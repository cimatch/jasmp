package jp.co.atomsware.swift.parsers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import jp.co.atomsware.swift.SwiftFormatException;

/**
 * RJEFormat用のパーサ
 *
 * @author T.Shimada
 *
 */
public class SwiftRJEFormatParserImpl extends SwiftParser {

	private boolean start = true; // 最初の判定
	private boolean lf = true; // 改行の発生
	private int line = 1; // 読み込み行数
	private int pos = 1;// 行内の位置
	private int level = 0; // BLOCKやTAGの階層
	private StringBuilder blockdata = new StringBuilder(); // BLOCKのデータ
	private StringBuilder tagdata = new StringBuilder(); // TAGのデータ
	private Map<Integer, String> currentBlock = new HashMap<Integer, String>(); // BLOCK名の格納
	private Map<Integer, String> currentTag = new HashMap<Integer, String>(); // TAG名の格納

	/**
	 * RJEフォーマットの解析
	 *
	 * @throws SwiftFormatException
	 */
	public void parse(InputStream inputStream) throws IOException,
			SwiftFormatException {

		/*
		 * ハンドラのインスタンスチェック
		 */
		if (super.handler == null) {
			throw new IllegalArgumentException("Handler cannot be null");
		}
		// テキストの読み込み
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				inputStream));
		while (reader.ready()) {
			int ch = reader.read();
			if (start) {
				// 1バイト目読み込み時のみ
				handler.startDocument(line, pos);
				start = false;
			}
			switch (ch) {
			case '{':
				level++;
				judge(reader, (char) ch);
				lf = false;
				break;
			case '}':
				// Resned
				if (currentTag.get(level) != null) {
					endTag((char) ch);
					blockdata.append((char) ch);
				} else if (currentBlock.get(level) != null) {
					endBlock((char) ch);
				} else {
					throw new SwiftFormatException(level, "Unknown Format:"
							+ (char) ch, line, pos);
				}
				level--;
				lf = false;
				break;
			case '$':
				// 次の電文
				handler.endDocument(line, pos);
				handler.startDocument(line, pos);
				lf = false;
				break;
			case '\r':
				// SWIFT電文は、CRLFであるべきだが、「CR」は無視
				break;
			case '\n':
				// TAG内の改行はデータ扱い
				if (currentTag.get(level) != null) {
					tagdata.append((char) ch);
					handler.character(line, pos, (char) ch);
				} else {
					// BLOCK 4 の改行
				}
				blockdata.append((char) ch);
				// 改行処理
				lf = true;
				line++;
				pos = 0;
				break;
			case ':':
				if (lf && currentTag.get(level) != null) {
					// BLOCK 4 TAG(２つ目以降)
					endTag((char) ch);
					judge(reader, (char) ch);
				} else if (lf) {
					// BLOCK 4 TAG(１つ目)
					judge(reader, (char) ch);
				} else {
					blockdata.append((char) ch);
					tagdata.append((char) ch);
					handler.character(line, pos, (char) ch);
				}
				lf = false;
				break;
			case '-':
				if (lf && currentTag.get(level) != null) {
					// BLOCK 4 LAST TAG
					endTag((char) ch);
				} else {
					blockdata.append((char) ch);
					tagdata.append((char) ch);
					handler.character(line, pos, (char) ch);
				}
				lf = false;
				break;
			default:
				blockdata.append((char) ch);
				tagdata.append((char) ch);
				handler.character(line, pos, (char) ch);
				lf = false;
			}
			pos++;
		}
		// EOF時
		handler.endDocument(line, pos);
		reader.close();
	}

	/**
	 * TAGかBLOCKかの判定
	 *
	 * @param reader
	 * @param c
	 * @throws IOException
	 * @throws SwiftFormatException
	 */
	private void judge(BufferedReader reader, char c) throws IOException,
			SwiftFormatException {
		StringBuilder name = new StringBuilder();
		if (reader.ready() == false) {
			throw new SwiftFormatException(level, "Unknown Format:", line, pos);
		}
		// 「:」が来るまで読み込む
		while (reader.ready()) {
			int ch = reader.read();
			switch (ch) {
			case ':':
				if (name.length() == 1) {
					// 名前が1バイトはブロック
					startBlock(name.toString());
				} else if (name.length() == 2 || name.length() == 3) {
					// 名前が2バイト以上はタグ
					startTag(name.toString());
					blockdata.append((char) c);
					blockdata.append(name.toString());
					blockdata.append(":");
				} else {
					// 不明
					throw new SwiftFormatException(level, "Unknown Format:"
							+ (char) ch, line, pos);
				}
				return;
			default:
				name.append((char) ch);
			}
		}
	}

	/**
	 * BLOCK開始処理
	 *
	 * @param name
	 * @throws SwiftFormatException
	 */
	private void startBlock(String name) throws SwiftFormatException {
		currentBlock.put(level, name);
		handler.startBlock(level, line, pos, currentBlock.get(level));
		blockdata = new StringBuilder();
	}

	/**
	 * TAG開始処理
	 *
	 * @param name
	 * @throws SwiftFormatException
	 */
	private void startTag(String name) throws SwiftFormatException {
		currentTag.put(level, name);
		handler.startTag(level, line, pos, currentTag.get(level));
		tagdata = new StringBuilder();
	}

	/**
	 * TAG終了処理
	 *
	 * @param ch
	 * @throws SwiftFormatException
	 */
	private void endTag(char ch) throws SwiftFormatException {
		if (currentTag.get(level) == null) {
			// 想定外
			throw new RuntimeException("TagEnd Error:" + ch + " (" + line + ","
					+ pos + ")");
		}
		handler.endTag(level, line, pos, currentTag.get(level), tagdata
				.toString().trim());
		currentTag.remove(level);
		tagdata = new StringBuilder();
	}

	/**
	 * BLOCK終了処理
	 *
	 * @param ch
	 * @throws SwiftFormatException
	 */
	private void endBlock(char ch) throws SwiftFormatException {
		if (currentBlock.get(level) == null) {
			// 想定外
			throw new RuntimeException("BlockEnd Error:" + ch + " (" + line
					+ "," + pos + ")");
		}
		handler.endBlock(level, line, pos, currentBlock.get(level),
				blockdata.toString());
		currentBlock.remove(level);
		blockdata = new StringBuilder();
	}

}

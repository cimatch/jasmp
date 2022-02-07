package jp.co.atomsware.swift.helpers;

import jp.co.atomsware.swift.SwiftFormatException;

/**
 * SWIFT電文のハンドリング用クラス実装用インタフェース
 *
 * @author T.Shimada
 *
 */
public interface HadlerBase {

	/**
	 * ISO15022電文の開始
	 *
	 * @param line
	 *            ファイル内の行数
	 * @param pos
	 *            行内の位置
	 */
	public void startDocument(int line, int pos) throws SwiftFormatException;

	/**
	 * BLOCKの開始
	 *
	 * @param level
	 *            階層
	 * @param line
	 *            ファイル内の行数
	 * @param pos
	 *            行内の位置
	 * @param blockNo
	 *            ブロック名
	 */
	public void startBlock(int level, int line, int pos, String blockNo)
			throws SwiftFormatException;

	/**
	 * TAGの開始
	 *
	 * @param level
	 *            階層
	 * @param line
	 *            ファイル内の行数
	 * @param pos
	 *            行内の位置
	 * @param tagName
	 *            TAG名
	 */
	public void startTag(int level, int line, int pos, String tagName)
			throws SwiftFormatException;

	/**
	 * TAGの終了
	 *
	 * @param level
	 *            階層
	 * @param line
	 *            ファイル内の行数
	 * @param pos
	 *            行内の位置
	 * @param tagName
	 *            TAG名
	 * @param tagData
	 *            TAG内の値
	 */
	public void endTag(int level, int line, int pos, String tagName,
			String tagData) throws SwiftFormatException;

	/**
	 * BLOCKの終了
	 *
	 * @param level
	 *            階層
	 * @param line
	 *            ファイル内の行数
	 * @param pos
	 *            行内の位置
	 * @param blockNo
	 *            BLOCK名
	 * @param blockData
	 *            BLOCK内の値
	 */
	public void endBlock(int level, int line, int pos, String blockNo,
			String blockData) throws SwiftFormatException;

	/**
	 * BLOCK内、TAG内での文字列読み込み時のイベント
	 *
	 * @param line
	 *            ファイル内の行数
	 * @param pos
	 *            行内の位置
	 * @param buffer
	 *            読み込んだ文字
	 */
	public void character(int line, int pos, char buffer)
			throws SwiftFormatException;

	/**
	 * ISO15022電文の終了
	 *
	 * @param line
	 *            ファイル内の行数
	 * @param pos
	 *            行内の位置
	 */
	public void endDocument(int line, int pos) throws SwiftFormatException;
}

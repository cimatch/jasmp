package jp.co.atomsware.swift.helpers;

/**
 * ハンドラのサンプル このサンプルを参考にSWIFT電文解析処理を実装します。
 *
 * @author T.Shimada
 *
 */
public class DefaultHandler implements HadlerBase {

	private static final String CRLF = "\n";
	private StringBuilder buffer = new StringBuilder();

	/**
	 * デフォルトコンストラクタ
	 */
	public DefaultHandler() {
	}

	/**
	 * ISO15022電文の開始
	 *
	 * @param line
	 *            ファイル内の行数
	 * @param pos
	 *            行内の位置
	 */
	public void startDocument(int line, int pos) {
		buffer.append(line + ":\t");
		buffer.append("startDocument()").append(CRLF);
	}

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
	public void startBlock(int level, int line, int pos, String blockNo) {
		// BLOCK名の表示
		buffer.append(line + ":\t");
		for (int i = 0; i < level; i++)
			buffer.append("\t");
		buffer.append("startBlock(" + blockNo + ")").append(CRLF);
	}

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
	public void startTag(int level, int line, int pos, String tagName) {
		// TAG名の表示
		buffer.append(line + ":\t");
		for (int i = 0; i < level; i++)
			buffer.append("\t");
		buffer.append("startTag(" + tagName + ")").append(CRLF);
	}

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
			String tagData) {
		// データの表示
		buffer.append(line + ":\t");
		for (int i = 0; i < level; i++)
			buffer.append("\t");
		buffer.append("\t");
		buffer.append(tagData).append(CRLF);

		// TAG名の表示
		buffer.append(line + ":\t");
		for (int i = 0; i < level; i++)
			buffer.append("\t");
		buffer.append("endTag(" + tagName + ")").append(CRLF);
	}

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
			String blockData) {
		// データの表示
		buffer.append(line + ":\t");
		for (int i = 0; i < level; i++)
			buffer.append("\t");
		buffer.append("\t");
		buffer.append(blockData).append(CRLF);

		// BLOCK名の表示
		buffer.append(line + ":\t");
		for (int i = 0; i < level; i++)
			buffer.append("\t");
		buffer.append("endBlock(" + blockNo + ")").append(CRLF);
	}

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
	public void character(int line, int pos, char buffer) {
		//
	}

	/**
	 * ISO15022電文の終了
	 *
	 * @param line
	 *            ファイル内の行数
	 * @param pos
	 *            行内の位置
	 */
	public void endDocument(int line, int pos) {
		buffer.append(line + ":\t");
		buffer.append("endDocument()").append(CRLF);
	}

	/**
	 * 解析結果の取得
	 *
	 * @return StringBuilder
	 */
	public StringBuilder getBuffer() {
		return buffer;
	}

}

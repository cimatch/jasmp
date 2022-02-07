/**
 *
 */
package jp.co.atomsware.swift;

/**
 * ISO15022の文法エラー
 *
 * @author T.shimada
 *
 */
public class SwiftFormatException extends Exception {

	private static final long serialVersionUID = 5354470669493343018L;

	int level;
	int line;
	int pos;

	/**
	 * @param arg0
	 *            エラーメッセージ
	 */
	public SwiftFormatException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 *            エラーメッセージ
	 * @param line
	 *            行
	 * @param pos
	 *            位置
	 */
	public SwiftFormatException(String arg0, int line, int pos) {
		super(arg0);
		this.line = line;
		this.pos = pos;
	}

	/**
	 * @param level
	 *            階層
	 * @param arg0
	 *            エラーメッセージ
	 * @param line
	 *            行
	 * @param pos
	 *            位置
	 */
	public SwiftFormatException(int level, String arg0, int line, int pos) {
		super(arg0);
		this.level = level;
		this.line = line;
		this.pos = pos;
	}

	/**
	 * @param level
	 *            階層
	 * @param arg0
	 *            エラーメッセージ
	 * @param line
	 *            行
	 * @param pos
	 *            位置
	 * @param e
	 *            エラー
	 */
	public SwiftFormatException(int level, String arg0, int line, int pos, Exception e) {
		super(arg0, e);
		this.level = level;
		this.line = line;
		this.pos = pos;
	}

	/**
	 * @param level
	 *            階層
	 * @param arg0
	 *            エラーメッセージ
	 * @param arg1
	 *            例外
	 * @param line
	 *            行
	 * @param pos
	 *            位置
	 */
	public SwiftFormatException(int level, String arg0, Throwable arg1,
			int line, int pos) {
		super(arg0, arg1);
		this.level = level;
		this.line = line;
		this.pos = pos;
	}

	/**
	 * @param level
	 *            階層
	 * @param arg1
	 *            例外
	 * @param line
	 *            行
	 * @param pos
	 *            位置
	 */
	public SwiftFormatException(int level, Throwable arg1, int line, int pos) {
		super(arg1);
		this.level = level;
		this.line = line;
		this.pos = pos;
	}

	public String getMessage() {
		if (level == 0)
			return super.getMessage() + " (" + this.line + "," + this.pos + ")";
		else
			return this.level + ":" + super.getMessage() + " (" + this.line
					+ "," + this.pos + ")";
	}

	public String getLocalizedMessage() {
		if (level == 0)
			return super.getMessage() + " (" + this.line + "," + this.pos + ")";
		else
			return this.level + ":" + super.getMessage() + " (" + this.line
					+ "," + this.pos + ")";
	}

}

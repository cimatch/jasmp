package jp.co.atomsware.swift.block;

/**
 * 出力用アプリケーションヘッダー
 *
 * @author T.Shimada
 *
 */
public class ApplicationHeaderBlockOutput extends ApplicationHeaderBlock {

	private static final long serialVersionUID = 1L;

	/** 内部変数 */
	private String inputTime = "0000";
	private String messageInputReference;
	private String outputDate = "000000";
	private String outputTime = "0000";
	private String priority = "N";

	/**
	 * 内容設定型コンストラクタ
	 *
	 * @param value
	 */
	protected ApplicationHeaderBlockOutput(String value) {
		this.blockId = BLOCK_2;
		setValue(value);
	}

	/**
	 * デフォルトコンストラクタ
	 */
	public ApplicationHeaderBlockOutput() {
		super();
	}

	/**
	 * 内容設定
	 */
	public void setValue(String value) {
		super.setValue(value);
		this.inputTime = value.substring(4, 8);
		this.messageInputReference = value.substring(8, 36);
		this.outputDate = value.substring(36, 42);
		this.outputTime = value.substring(42, 46);
		this.priority = value.substring(46, 47);
	}

	/**
	 * 入力時間
	 *
	 * @return hhmm
	 */
	public String getInputTime() {
		return inputTime;
	}

	/**
	 * 入力時間
	 *
	 * @param inputTime
	 */
	public void setInputTime(String inputTime) {
		this.inputTime = inputTime;
	}

	/**
	 * 入力日付や送信側のアドレスを含む Message Input Reference (MIR)<br/>
	 * (28桁)
	 *
	 * @return messageInputReference
	 */
	public String getMessageInputReference() {
		return messageInputReference;
	}

	/**
	 * Message Input Reference (MIR)<br/>
	 *
	 * @param messageInputReference
	 */
	public void setMessageInputReference(String messageInputReference) {
		this.messageInputReference = messageInputReference;
	}

	/**
	 * 出力日付
	 *
	 * @return yymmdd
	 */
	public String getOutputDate() {
		return outputDate;
	}

	/**
	 * 出力日付
	 *
	 * @param outputDate
	 */
	public void setOutputDate(String outputDate) {
		this.outputDate = outputDate;
	}

	/**
	 * 出力時間
	 *
	 * @return hhmm
	 */
	public String getOutputTime() {
		return outputTime;
	}

	/**
	 * 出力時間
	 *
	 * @param outputTime
	 */
	public void setOutputTime(String outputTime) {
		this.outputTime = outputTime;
	}

	/**
	 * 優先順位 <br/>
	 * ・S = システム <br/>
	 * ・N = 標準 <br/>
	 * ・U = 緊急 <br/>
	 *
	 * @return priority
	 */
	public String getPriority() {
		return priority;
	}

	/**
	 * 優先順位 <br/>
	 *
	 * @param priority
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}

	@Override
	public String toSwiftMessage() {
		StringBuilder text = new StringBuilder();
		text.append(BLOCK_OPEN).append(blockId).append(SEPARATOR);
		text.append(ioType).append(messageType);
		text.append(inputTime).append(messageInputReference).append(outputDate);
		text.append(outputTime).append(priority).append(BLOCK_CLOSE);
		return text.toString();
	}

	@Override
	public String toString() {
		return "ApplicationHeaderBlockOutput [blockID=1, inputTime=" + inputTime + ", messageInputReference="
				+ messageInputReference + ", outputDate=" + outputDate + ", outputTime=" + outputTime + ", priority="
				+ priority + "]";
	}

}

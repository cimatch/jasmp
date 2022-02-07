package jp.co.atomsware.swift.block;

/**
 * 入力用アプリケーションヘッダー
 *
 * @author T.Shimada
 *
 */
public class ApplicationHeaderBlockInput extends ApplicationHeaderBlock {

	private static final long serialVersionUID = 1L;

	/** 内部変数 */
	private String lTAddr;
	private String priority = "N";
	private String deliveryMonitorField = "2";
	private String obsolescencePeriod = "";

	/**
	 * 内容設定型コンストラクタ
	 *
	 * @param value
	 */
	protected ApplicationHeaderBlockInput(String value) {
		setValue(value);
	}

	/**
	 * デフォルトコンストラクタ
	 */
	public ApplicationHeaderBlockInput() {
		super();
	}

	/**
	 * 内容設定
	 */
	public void setValue(String value) {
		super.setValue(value);
		this.lTAddr = value.substring(4, 16);
		this.priority = value.substring(16, 17);
		if (value.length() > 17)
			this.deliveryMonitorField = value.substring(17, 18);
		if (value.length() > 18)
			this.obsolescencePeriod = value.substring(18, 21);
	}

	/**
	 * 宛先12桁BIC
	 *
	 * @return lTAddr
	 */
	public String getLtAddr() {
		return lTAddr;
	}

	/**
	 * 宛先11桁BIC
	 *
	 * @return lTAddr
	 */
	public String getLtAddr11() {
		if (lTAddr != null && lTAddr.length() == 12) {
			return lTAddr.substring(0, 8) + lTAddr.substring(9, 12);
		} else {
			return null;
		}
	}

	/**
	 * 宛先12桁BIC
	 *
	 * @return
	 *
	 */
	public void setLtAddr(String lTAddr) {
		if (lTAddr.length() == 8) {
			this.lTAddr = lTAddr + "XXXX";
		} else if (lTAddr.length() == 11) {
			this.lTAddr = lTAddr.substring(0, 8) + "X" + lTAddr.substring(8, 11);
		} else {
			this.lTAddr = lTAddr;
		}
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

	/**
	 * デリバリー・モニター・フィールドは次のとおりです。<br/>
	 * •1 = デリバリー警告なし (MT010)<br/>
	 * •2 = デリバリー通知 (MT011)<br/>
	 * •3 = どちらも有効 = U1 または U3、N2 または N
	 *
	 * @return deliveryMonitorField
	 */
	public String getDeliveryMonitorField() {
		return deliveryMonitorField;
	}

	/**
	 * デリバリー・モニター・フィールド
	 *
	 * @param deliveryMonitorField
	 */
	public void setDeliveryMonitorField(String deliveryMonitorField) {
		this.deliveryMonitorField = deliveryMonitorField;
	}

	/**
	 * 陳腐化期間。これは、次のように、ノンデリバリー通知をいつ生成するかを指定します。<br/>
	 * •U に有効 = 003 (15 分) <br/>
	 * •N に有効 = 020 (100 分)
	 *
	 * @return obsolescencePeriod
	 */
	public String getObsolescencePeriod() {
		return obsolescencePeriod;
	}

	/**
	 * 陳腐化期間
	 *
	 * @param obsolescencePeriod
	 */
	public void setObsolescencePeriod(String obsolescencePeriod) {
		this.obsolescencePeriod = obsolescencePeriod;
	}

	@Override
	public String toSwiftMessage() {
		StringBuilder text = new StringBuilder();
		text.append(BLOCK_OPEN).append(blockId).append(SEPARATOR);
		text.append(ioType).append(messageType);
		text.append(lTAddr).append(priority).append(deliveryMonitorField);
		text.append(obsolescencePeriod).append(BLOCK_CLOSE);
		return text.toString();
	}

	@Override
	public String toString() {
		return "ApplicationHeaderBlockInput [blockID=2, lTAddr=" + lTAddr + ", priority=" + priority
				+ ", deliveryMonitorField=" + deliveryMonitorField + ", obsolescencePeriod=" + obsolescencePeriod + "]";
	}

}

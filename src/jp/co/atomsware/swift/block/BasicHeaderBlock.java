package jp.co.atomsware.swift.block;

/**
 * 基本ヘッダー・ブロック
 *
 * @author T.Shimada
 *
 */
public class BasicHeaderBlock extends BaseBlock {

	private static final long serialVersionUID = 1L;

	/** 内部変数 */
	private String appID = "F";
	private String serviceID = "01";
	private String lTAddr;
	private String sessionID = "0000";
	private String sequenceID = "000000";

	/**
	 * 基本ヘッダー・ブロックのインスタンス生成
	 *
	 * @return BaseBlock
	 */
	public static BaseBlock newInstance() {
		return new BasicHeaderBlock();
	}

	/**
	 * 基本ヘッダー・ブロックのインスタンス生成
	 *
	 * @param value
	 * @return BasicHeaderBlock
	 */
	public static BasicHeaderBlock newInstance(String value) {
		return new BasicHeaderBlock(value);
	}

	/**
	 * デフォルトコンストラクタ
	 */
	protected BasicHeaderBlock() {
		super();
		this.blockId = BLOCK_1;
	}

	/**
	 * 内容設定型コンストラクタ
	 *
	 * @param value
	 */
	protected BasicHeaderBlock(String value) {
		setValue(value);
	}

	@Override
	public void setValue(String value) {
		blockId = "1";
		this.appID = value.substring(0, 1);
		this.serviceID = value.substring(1, 3);
		this.lTAddr = value.substring(3, 15);
		this.sessionID = value.substring(15, 19);
		this.sequenceID = value.substring(19, 25);
	}

	/**
	 * アプリケーション ID は以下のとおりです。 <br/>
	 * •F = FIN (金融アプリケーション) <br/>
	 * •A = GPA (汎用アプリケーション) <br/>
	 * •L = GPA (ログインなどに使用します)
	 *
	 * @return appID
	 */
	public String getAppID() {
		return appID;
	}

	/**
	 * アプリケーション ID
	 */
	public void setAppID(String appID) {
		this.appID = appID;
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
	 * 送信元12桁BIC
	 * @return
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
	 * サービス ID は以下のとおりです。 <br/>
	 * •01 = FIN/GPA <br/>
	 * •21 = ACK/NAK <br/>
	 *
	 * @return serviceID
	 */
	public String getServiceID() {
		return serviceID;
	}

	/**
	 * サービス ID
	 * @param serviceID
	 */
	public void setServiceID(String serviceID) {
		this.serviceID = serviceID;
	}

	/**
	 * 送信元12桁BICを取得
	 *
	 * @return lTAddr
	 */
	public String getLTAddr() {
		return lTAddr;
	}

	/**
	 * セッションIDを取得
	 *
	 * @return sessionID
	 */
	public String getSessionID() {
		return sessionID;
	}

	/**
	 * セッションID
	 * @param sessionID
	 */
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	/**
	 * シーケンスIDを取得
	 *
	 * @return sequenceID
	 */
	public String getSequenceID() {
		return sequenceID;
	}

	/**
	 * シーケンスID
	 *
	 * @param sequenceID
	 */
	public void setSequenceID(String sequenceID) {
		this.sequenceID = sequenceID;
	}

	@Override
	public String toSwiftMessage() {
		StringBuilder text = new StringBuilder();
		text.append(BLOCK_OPEN).append(blockId).append(SEPARATOR);
		text.append(appID).append(serviceID).append(lTAddr);
		text.append(sessionID).append(sequenceID).append(BLOCK_CLOSE);
		return text.toString();
	}

	@Override
	public String toString() {
		return "BasicHeaderBlock [blockID=1, appID=" + appID + ", serviceID="
				+ serviceID + ", lTAddr=" + lTAddr + ", sessionID=" + sessionID
				+ ", sequenceID=" + sequenceID + ", tags=" + tags + "]";
	}

}

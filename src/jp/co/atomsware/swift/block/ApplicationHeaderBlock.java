package jp.co.atomsware.swift.block;

import java.lang.reflect.InvocationTargetException;

/**
 * アプリケーションヘッダー
 *
 * @author T.Shimada
 *
 */
public class ApplicationHeaderBlock extends BaseBlock {

	private static final long serialVersionUID = 1L;

	/** 内部変数 */
	protected String ioType;
	protected String messageType;

	/** 入出力用 */
	protected ApplicationHeaderBlock element;

	/**
	 * インスタンス生成
	 *
	 * @return ApplicationHeaderBlock
	 */
	public static BaseBlock newInstance() {
		return new ApplicationHeaderBlock();
	}

	/**
	 * デフォルトコンストラクタ
	 */
	protected ApplicationHeaderBlock() {
		super();
		blockId = BLOCK_2;
	}

	/**
	 * 内容によるインスタンス生成
	 *
	 * @return ApplicationHeaderBlock
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 */
	public static ApplicationHeaderBlock newInstance(String value)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		String ioType = value.substring(0, 1);
		if (IO_INPUT.equals(ioType)) {
			BaseBlock block = SwiftBlockFactory.newInstance("ApplicationHeaderBlockInput");
			block.setValue(value);
			return (ApplicationHeaderBlock)block;
		}
		if (IO_OUTPUT.equals(ioType)) {
			BaseBlock block = SwiftBlockFactory.newInstance("ApplicationHeaderBlockOutput");
			block.setValue(value);
			return (ApplicationHeaderBlock)block;
		}
		return new ApplicationHeaderBlock();
	}

	/**
	 * 内容設定型コンストラクタ
	 *
	 * @param value
	 */
	protected ApplicationHeaderBlock(String value) {
		setValue(value);
	}

	/**
	 * 内容設定
	 */
	public void setValue(String value) {
		this.blockId = BLOCK_2;
		this.ioType = value.substring(0, 1);
		this.messageType = value.substring(1, 4);
	}

	/**
	 * 入出力タイプ
	 *
	 * @return I/O
	 */
	public String getIoType() {
		return ioType;
	}

	/**
	 * 入出力タイプ
	 *
	 * @param ioType
	 */
	public void setIoType(String ioType) {
		this.ioType = ioType;
	}

	/**
	 * メッセージタイプ
	 *
	 * @return messageType
	 */
	public String getMessageType() {
		return messageType;
	}

	/**
	 * メッセージタイプ
	 *
	 * @param messageType
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	/**
	 * 子クラス
	 *
	 * @return ApplicationHeaderBlock
	 */
	public ApplicationHeaderBlock getElement() {
		return element;
	}

	/**
	 * 子クラス
	 *
	 * @param element
	 */
	public void setElement(ApplicationHeaderBlock element) {
		this.element = element;
	}

	@Override
	public String toSwiftMessage() {
		ApplicationHeaderBlock element = getElement();
		if (element != null)
			return getElement().toSwiftMessage();
		StringBuilder text = new StringBuilder();
		text.append(BLOCK_OPEN).append(blockId).append(SEPARATOR);
		text.append(BLOCK_CLOSE);
		return text.toString();
	}

	@Override
	public String toString() {
		return "ApplicationHeaderBlock [ioType=" + ioType + ", messageType=" + messageType + ", element=" + element
				+ ", blockId=" + blockId + ", tags=" + tags + "]";
	}

}

package jp.co.atomsware.swift.block;

import jp.co.atomsware.swift.SwiftMessage;
import jp.co.atomsware.swift.tag.BaseTag;

/**
 * テキストブロック
 *
 * @author T.Shimada
 *
 */
public class TextBlock extends BaseBlock {

	private static final long serialVersionUID = 1L;

	/** 内包SWIFT メッセージ */
	protected SwiftMessage swiftMessage = null;

	/**
	 * TextBlockのインスタンス生成
	 *
	 * @return TextBlock
	 */
	public static TextBlock newInstance() {
		return new TextBlock();
	}

	/**
	 * デフォルトコンストラクタ
	 */
	protected TextBlock() {
		blockId = BLOCK_4;
	}

	@Override
	public void setValue(String value) {
		//
	}

	/**
	 * 内包するSWIFTメッセージの取得
	 *
	 * @return SwiftMessage
	 */
	public SwiftMessage getSwiftMessage() {
		return swiftMessage;
	}

	/**
	 * 内包するSWIFTメッセージの設定
	 *
	 * @param swiftMessage
	 */
	public void setSwiftMessage(SwiftMessage swiftMessage) {
		this.swiftMessage = swiftMessage;
	}

	@Override
	public String toSwiftMessage() {
		StringBuilder text = new StringBuilder();
		text.append(BLOCK_OPEN).append(blockId).append(SEPARATOR);
		if (getSwiftMessage() == null) {
			for (BaseTag tag : this.tags) {
				if(tag.getValue()==null || tag.getValue().isEmpty()) {
					continue;
				}
				text.append(CRLF).append(SEPARATOR).append(tag.getTag());
				text.append(SEPARATOR).append(tag.getValue());
			}
			text.append(CRLF).append(BLOCK_TAG_END);
		} else {
			for (BaseTag tag : this.tags) {
				if(tag.getValue()==null || tag.getValue().isEmpty()) {
					continue;
				}
				text.append(TAG_OPEN).append(tag.getTag()).append(SEPARATOR);
				text.append(tag.getValue()).append(TAG_CLOSE);
			}
			text.append(swiftMessage.toSwiftMessage());
		}
		text.append(BLOCK_CLOSE);
		return text.toString();
	}

	@Override
	public String toString() {
		return "TextBlock [blockID=4, , tags=" + tags + "], swiftMessage="
				+ swiftMessage + "]";
	}

}

package jp.co.atomsware.swift.block;

import jp.co.atomsware.swift.tag.BaseTag;

/**
 * Ack用テキストブロック
 *
 * @author T.Shimada
 *
 */
public class AckTextBlock extends TextBlock {

	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス生成
	 *
	 * @return AckTextBlock
	 */
	public static AckTextBlock newInstance() {
		return new AckTextBlock();
	}

	@Override
	public String toSwiftMessage() {
		StringBuilder text = new StringBuilder();
		text.append(BLOCK_OPEN).append(blockId).append(SEPARATOR);

		for (BaseTag tag : this.tags) {
			text.append(TAG_OPEN).append(tag.getTag()).append(SEPARATOR).append(tag.getValue()).append(TAG_CLOSE);
		}
		if (getSwiftMessage() != null)
			text.append(swiftMessage.toSwiftMessage());
		text.append(BLOCK_CLOSE);
		return text.toString();
	}

	@Override
	public String toString() {
		return "HistoryBlock [blockID=" + blockId + " , tags=" + tags + "], swiftMessage=" + swiftMessage + "]";
	}

}

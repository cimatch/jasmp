package jp.co.atomsware.swift.block;

import jp.co.atomsware.swift.tag.BaseTag;

/**
 * Retrieved Message用のテキストブロック
 *
 * @author T.Shimada
 *
 */
public class HistoryBlock extends TextBlock {

	private static final long serialVersionUID = 1L;

	/**
	 * HistoryBlockのインスタンス生成
	 *
	 * @return HistoryBlock
	 */
	public static HistoryBlock newInstance() {
		return new HistoryBlock();
	}

	@Override
	public String toSwiftMessage() {
		StringBuilder text = new StringBuilder();
		text.append(BLOCK_OPEN).append(blockId).append(SEPARATOR);
		for (BaseTag tag : this.tags) {
			text.append(TAG_OPEN).append(tag.getTag()).append(SEPARATOR)
					.append(tag.getValue()).append(TAG_CLOSE);
		}
		if (getSwiftMessage() != null)
			text.append(swiftMessage.toSwiftMessage());
		text.append(BLOCK_CLOSE);
		return text.toString();
	}

	@Override
	public String toString() {
		return "HistoryBlock [blockID=4, , tags=" + tags + "], swiftMessage="
				+ swiftMessage + "]";
	}

}

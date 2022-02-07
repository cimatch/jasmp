package jp.co.atomsware.swift.block;

import jp.co.atomsware.swift.tag.BaseTag;

/**
 * オプションブロック
 *
 * @author T.Shimada
 *
 */
public class UserHeaderBlock extends BaseBlock {

	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス生成
	 *
	 * @return UserHeaderBlock
	 */
	public static UserHeaderBlock newInstance() {
		return new UserHeaderBlock();
	}

	/**
	 * デフォルトコンストラクタ
	 */
	protected UserHeaderBlock() {
		super("");
		this.blockId = BLOCK_3;
	}

	@Override
	public void setValue(String value) {
		this.blockId = BLOCK_3;
	}

	/**
	 * オプションの銀行用優先順位コード
	 *
	 * @return 優先順位コード
	 */
	public String getPriority() {
		return super.getFirstTag(TAG_PRIORITY).getValue();
	}

	/**
	 * オプションの銀行用優先順位コード
	 *
	 * @param value
	 * @throws Exception
	 */
	public void setPriority(String value) throws Exception {
		BaseTag tag = super.getFirstTag(TAG_PRIORITY);
		if (tag == null) {
			tag = BaseTag.newInstance(TAG_PRIORITY, value);
			tags.add(tag);
		}
		tag.setValue(value);
	}

	/**
	 * アクセス・ポイントが ACK の調整に使用する Message User Reference (MUR)
	 *
	 * @return MUR
	 */
	public String getMessageUserReference() {
		return super.getFirstTag(TAG_MUR).getValue();
	}

	/**
	 * Message User Reference (MUR)
	 *
	 * @param value
	 * @throws Exception
	 */
	public void setMessageUserReference(String value) throws Exception {
		BaseTag tag = super.getFirstTag(TAG_MUR);
		if (tag == null) {
			tag = BaseTag.newInstance(TAG_MUR, value);
			tags.add(tag);
		}
		tag.setValue(value);
	}

	/**
	 * Unique end-to-end transaction reference (UETR)
	 *
	 * @return MUR
	 */
	public String getUETR() {
		return super.getFirstTag(TAG_UETR).getValue();
	}

	/**
	 * Unique end-to-end transaction reference (UETR)
	 *
	 * @param value
	 * @throws Exception
	 */
	public void setUETR(String value) throws Exception {
		BaseTag tag = super.getFirstTag(TAG_UETR);
		if (tag == null) {
			tag = BaseTag.newInstance(TAG_UETR, value);
			tags.add(tag);
		}
		tag.setValue(value);
	}

	@Override
	public String toString() {
		return "UserHeaderBlock [blockID=3, tags=" + tags + "]";
	}
}

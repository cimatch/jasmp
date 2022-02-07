package jp.co.atomsware.swift.block;

/**
 * トレーラーブロック
 *
 * @author T.Shimada
 *
 */
public class TrailerBlock extends BaseBlock {

	private static final long serialVersionUID = 1L;

	/**
	 * インスタンス生成
	 *
	 * @return TrailerBlock
	 */
	public static TrailerBlock newInstance() {
		return new TrailerBlock();
	}

	/**
	 * デフォルトコンストラクタ
	 */
	protected TrailerBlock() {
		super("");
		blockId = "5";
	}

	@Override
	public void setValue(String value) {
	}

	/**
	 * 宛先との間で交換されるキーと秘密のアルゴリズムを使用して、メッセージの内容全体に基づいて計算されるメッセージ確認コード。<br/>
	 * メッセージ・カテゴリー 1、2、4、5、7、8、ほとんどの 6s および 304 で検出されます
	 *
	 * @return メッセージ確認コード
	 */
	public String getMessageAuthenticationCode() {
		return super.getFirstTag(TAG_MAC).getValue();
	}

	/**
	 * すべてのメッセージ・タイプに対して計算されるチェックサム。
	 *
	 * @return チェックサム
	 */
	public String getChecksum() {
		return super.getFirstTag(TAG_CHK).getValue();
	}

	/**
	 * 同じメッセージが以前に送信されたとユーザーが考えた場合に追加される重複送信の可能性
	 *
	 * @return 重複送信の可能性
	 */
	public String getPossibilityOfDuplicateTransmissions() {
		return super.getFirstTag(TAG_PDE).getValue();
	}

	/**
	 * 緊急メッセージ (U) が 15 分以内に配信されなかった場合や、<br/>
	 * セージ (N) が 100 分以内にデリバリーされなかった場合に SWIFT によって追加される。
	 *
	 * @return 配信制限
	 */
	public String getDeliverryLimitMessage() {
		return super.getFirstTag(TAG_DLM).getValue();
	}

	@Override
	public String toString() {
		return "TrailerBlock [blockID=" + blockId + ", tags=" + tags + "]";
	}

}

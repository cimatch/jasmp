package jp.co.atomsware.swift.tag;

/**
 * 未定義タグ
 *
 * @author T.Shimada
 *
 */
public class UnKnownTag extends BaseTag {

	private static final long serialVersionUID = 1L;

	public UnKnownTag() {
	}

	public UnKnownTag(String tag, String value) {
		super(tag, value);
	}

	@Override
	public String toString() {
		return "UnKnownTag [tag=" + tag + ", value=" + value + "]";
	}

}

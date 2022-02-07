package jp.co.atomsware.swift.tag;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;

import jp.co.atomsware.swift.Element;

/**
 * TAGの基底クラス
 *
 * @author T.Shimada
 *
 */
public abstract class BaseTag extends Element implements Serializable {

	private static final long serialVersionUID = 1L;
	/** TAGの内部変数 */
	protected String tag;
	protected String value;

	/**
	 * TAGのオブジェクト生成
	 *
	 * @param tagName
	 * @param value
	 * @return BaseTag
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 */
	public static BaseTag newInstance(String tagName, String value)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException {
		Enumeration<Object> keys = SwiftTagFactory.getKeys();
		while (keys.hasMoreElements()) {
			String key = (String)keys.nextElement();
			if (key.equals(tagName)) {
				BaseTag tag = SwiftTagFactory.newInstance(SwiftTagFactory
						.getString(key));
				tag.setTag(tagName);
				tag.setValue(value);
				return tag;
			}
		}
		return new UnKnownTag(tagName, value);
	}

	/**
	 * デフォルトコンストラクタ
	 */
	public BaseTag() {
	}

	protected BaseTag(String tag, String value) {
		this.tag = tag;
		setValue(value);
	}

	public void setTag(String tagName) {
		this.tag = tagName;
	}

	public String getTag() {
		return tag;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "BaseTag [tag=" + tag + ", value=" + value + "]";
	}

	@Override
	public String toSwiftMessage() {
		return null;
	}

}

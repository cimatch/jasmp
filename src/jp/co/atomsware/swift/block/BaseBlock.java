package jp.co.atomsware.swift.block;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.co.atomsware.swift.Element;
import jp.co.atomsware.swift.SwiftMessage;
import jp.co.atomsware.swift.tag.BaseTag;
import jp.co.atomsware.swift.tag.UnKnownTag;

/**
 * ブロックの基底クラス
 *
 * @author T.Shimada
 *
 */
public abstract class BaseBlock extends Element implements Serializable, Map<String, Object> {

	private static final long serialVersionUID = 1L;

	/** 定数定義 */
	public static final String BLOCK_1 = "1";
	public static final String BLOCK_2 = "2";
	public static final String BLOCK_3 = "3";
	public static final String BLOCK_4 = "4";
	public static final String BLOCK_5 = "5";

	/** 定数定義 */
	protected static final String SVC_ACK_NACK = "21";
	protected static final String IO_INPUT = "I";
	protected static final String IO_OUTPUT = "O";
	protected static final String MT_RETRIEVED = "021";

	/** TAG定数定義 */
	protected static final String TAG_DLM = "DLM";
	protected static final String TAG_PDE = "PDE";
	protected static final String TAG_CHK = "CHK";
	protected static final String TAG_MAC = "MAC";
	protected static final String TAG_MUR = "108";
	protected static final String TAG_PRIORITY = "113";
	protected static final String TAG_UETR = "121";

	/** 定数定義 */
	protected static final String SEPARATOR = ":";
	protected static final String BLOCK_OPEN = "{";
	protected static final String BLOCK_CLOSE = "}";
	protected static final String TAG_OPEN = "{";
	protected static final String TAG_CLOSE = "}";
	protected static final String BLOCK_TAG_END = "-";
	protected static final String CRLF = "\r\n";

	/** 内部変数 */
	protected String blockId;

	/** 保有TAG */
	protected List<BaseTag> tags = new ArrayList<BaseTag>();

	/** 親メッセージ */
	protected SwiftMessage parent;

	/**
	 * 各BLOCK用のBLOCKオブジェクトのインスタンス生成
	 *
	 * @param swiftMessage
	 * @param blockId
	 * @return BaseBlock
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 */
	public static BaseBlock newInstance(SwiftMessage swiftMessage,
			String blockId) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		BaseBlock block;
		if (BLOCK_1.equals(blockId)) {
			return SwiftBlockFactory.newInstance("BasicHeaderBlock");
		}
		if (BLOCK_2.equals(blockId)) {
			return SwiftBlockFactory.newInstance("ApplicationHeaderBlock");
		}
		if (BLOCK_3.equals(blockId)) {
			return SwiftBlockFactory.newInstance("UserHeaderBlock");
		}
		if (BLOCK_4.equals(blockId)) {
			if (SVC_ACK_NACK.equals(swiftMessage.getServiceID()))
				return SwiftBlockFactory.newInstance("AckTextBlock");
			if (MT_RETRIEVED.equals(swiftMessage.getMessageType()))
				return SwiftBlockFactory.newInstance("HistoryBlock");
			else
				return SwiftBlockFactory.newInstance("TextBlock");

		}
		if (BLOCK_5.equals(blockId)) {
			return SwiftBlockFactory.newInstance("TrailerBlock");
		}
		block = SwiftBlockFactory.newInstance("TrailerBlock");
		block.setBlockID(blockId);
		return block;
	}

	/**
	 * デフォルトコンストラクタ
	 */
	protected BaseBlock() {
	}

	/**
	 * 内容設定型コンストラクタ
	 *
	 * @param value
	 */
	public BaseBlock(String value) {
		this();
	}

	/**
	 * 内容設定
	 *
	 * @param value
	 */
	public abstract void setValue(String value);

	/**
	 * BLOCK IDの取得
	 *
	 * @return blockId
	 */
	public String getBlockID() {
		return blockId;
	}

	/**
	 * BLOCK IDの設定
	 *
	 * @param blockId
	 */
	public void setBlockID(String blockId) {
		this.blockId = blockId;
	}

	/**
	 * TAG名が一致するTAGを取得
	 * なければ null を返却します。
	 *
	 * @param tagName
	 * @return BaseTag
	 */
	public BaseTag getFirstTag(String tagName) {
		for (BaseTag tag : this.tags) {
			if (tag.getTag().equals(tagName)) {
				return tag;
			}
		}
		return null;
	}

	/**
	 * TAGを追加
	 *
	 * @param tag
	 */
	public void addTag(BaseTag tag) {
		tag.setParent(this);
		tags.add(tag);
	}

	/**
	 * TAG名が一致するTAGを取得
	 * なければノードに追加します。
	 *
	 * @param tagName
	 * @return BaseTag
	 */
	public BaseTag getTag(String tagName) {
		for (BaseTag tag : this.tags) {
			if (tag.getTag().equals(tagName)) {
				return tag;
			}
		}
		try {
			BaseTag tag = BaseTag.newInstance(tagName, "");
			tag.setParent(this);
			tags.add(tag);
			return tag;
		} catch (Exception e) {
			e.printStackTrace();
			return new UnKnownTag();
		}
	}

	/**
	 * TAGを追加
	 *
	 * @param list
	 */
	public void addTags(List<BaseTag> list) {
		list.addAll(list);
	}

	/**
	 * TAGをすべて取得
	 *
	 * @return tags
	 */
	public List<BaseTag> getTags() {
		return tags;
	}

	/**
	 * TAG名が一致するTAGをすべて取得
	 *
	 * @param tagName
	 * @return List<BaseTag>
	 */
	public List<BaseTag> getTags(String tagName) {
		List<BaseTag> list = new ArrayList<BaseTag>();
		for (BaseTag tag : this.tags) {
			if (tag.getTag().equals(tagName)) {
				list.add(tag);
			}
		}
		return list;
	}

	/**
	 * TAGを設定
	 *
	 * @param tags
	 */
	public void setTags(List<BaseTag> tags) {
		for (BaseTag tag : tags) {
			tag.setParent(this);
		}
		this.tags = tags;
	}

	/**
	 * ISO15022形式出力
	 *
	 * @return ISO15022形式 SWIFT メッセージ
	 */
	public String toSwiftMessage() {
		StringBuilder text = new StringBuilder();
		text.append(BLOCK_OPEN).append(blockId).append(SEPARATOR);
		for (BaseTag tag : this.tags) {
			text.append(TAG_OPEN).append(tag.getTag()).append(SEPARATOR);
			text.append(tag.getValue()).append(TAG_CLOSE);
		}
		text.append(BLOCK_CLOSE);
		return text.toString();
	}

	/**
	 * 親メッセージ取得
	 * @return SwiftMessage
	 */
	public SwiftMessage getParent() {
		return parent;
	}

	/**
	 * 親メッセージ設定
	 * @param parent SwiftMessage
	 */
	public void setParent(SwiftMessage parent) {
		this.parent = parent;
	}

	/**
	 * 内容出力
	 */
	public String toString() {
		return "BaseBlock [blockId=" + blockId + ", tags=" + tags + ", tags="
				+ tags + "]";
	}

	@Override
	public int size() {
		return tags.size();
	}

	@Override
	public boolean isEmpty() {
		return tags.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return tags.contains(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return false;
	}

	/**
	 * TAG名が一致するTAGを取得
	 * なければノードに追加します。
	 */
	@Override
	public Object get(Object tagName) {
		try {
			return (BaseTag) getTag((String) tagName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 *
	 */
	public Object put(String tagName, BaseTag tag) {
		remove(tagName);
		tags.add(tag);
		return tag;
	}

	/**
	 * TAGに値を設定します
	 *
	 * @param tag
	 * @return
	 */
	@Override
	public Object put(String tagName, Object value) {
		BaseTag tag = (BaseTag) get(tagName);
		if (value == null || value instanceof String) {
			tag.setValue((String) value);
		} else {
			tag.setValue(value.toString());
		}
		return tag;
	}

	@Override
	public Object remove(Object tagName) {
		BaseTag target = null;
		if (tagName instanceof BaseTag) {
			target = (BaseTag) tagName;
			return tags.remove(target);
		} else if (tagName instanceof String) {
			for (BaseTag tag : this.tags) {
				if (tag.getTag().equals(tagName)) {
					target = tag;
					break;
				}
			}
			if (target != null) {
				tags.remove(target);
				return tagName;
			}
		}
		return null;
	}

	@Override
	public void putAll(Map<? extends String, ? extends Object> m) {
		throw new RuntimeException("Not Supported");
	}

	@Override
	public void clear() {
		tags.clear();
	}

	@Override
	public Set<String> keySet() {
		throw new RuntimeException("Not Supported");
	}

	@Override
	public Collection<Object> values() {
		throw new RuntimeException("Not Supported");
	}

	@Override
	public Set<Entry<String, Object>> entrySet() {
		throw new RuntimeException("Not Supported");
	}

}

package jp.co.atomsware.swift;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import jp.co.atomsware.swift.block.ApplicationHeaderBlock;
import jp.co.atomsware.swift.block.BaseBlock;
import jp.co.atomsware.swift.block.BasicHeaderBlock;
import jp.co.atomsware.swift.block.SwiftBlockFactory;
import jp.co.atomsware.swift.block.TextBlock;
import jp.co.atomsware.swift.block.UserHeaderBlock;

/**
 * 15022形式のSwiftMessageObject
 *
 * @author T.shimada
 *
 */
public class SwiftMessage implements List<BaseBlock> {

	/** 保有ブロック */
	protected List<BaseBlock> blocks = new ArrayList<BaseBlock>();

	/**
	 * デフォルトコンストラクタ
	 */
	public SwiftMessage() {
	}

	/**
	 * BlockNoが一致する最初のブロック
	 *
	 * @param blockNo
	 * @return BaseBlock
	 */
	public BaseBlock getFirstBlock(String blockNo) {
		for (BaseBlock block : this.blocks) {
			if (block.getBlockID() != null && block.getBlockID().equals(blockNo)) {
				block.setParent(this);
				return block;
			}
		}
		return null;
	}

	/**
	 * BlockNoが一致する最後のブロック
	 *
	 * @param blockNo
	 * @return BaseBlock
	 */
	public BaseBlock getLastBlock(String blockNo) {
		BaseBlock findblock = null;
		for (BaseBlock block : this.blocks) {
			if (block.getBlockID() != null && block.getBlockID().equals(blockNo)) {
				findblock = block;
			}
		}
		return findblock;
	}

	/**
	 * サービスIDの取得
	 *
	 * @return ServiceID
	 */
	public String getServiceID() {
		BasicHeaderBlock block = (BasicHeaderBlock) getLastBlock(BaseBlock.BLOCK_1);
		if (block == null)
			return null;
		return block.getServiceID();
	}

	/**
	 * IOTipeの取得
	 *
	 * @return IOTipe
	 */
	public String getIOTipe() {
		ApplicationHeaderBlock block = (ApplicationHeaderBlock) getLastBlock(BaseBlock.BLOCK_2);
		if (block == null)
			return null;
		return block.getIoType();
	}

	/**
	 * メッセージタイプの取得
	 *
	 * @return MessageType
	 */
	public String getMessageType() {
		ApplicationHeaderBlock block = (ApplicationHeaderBlock) getLastBlock(BaseBlock.BLOCK_2);
		if (block == null)
			return null;
		return block.getMessageType();
	}

	/**
	 * @see java.util.List
	 */
	public boolean add(BaseBlock o) {
		o.setParent(this);
		return blocks.add(o);
	}

	/**
	 * @see java.util.List
	 */
	public void add(int index, BaseBlock element) {
		element.setParent(this);
		blocks.add(index, element);
	}

	/**
	 * @see java.util.List
	 */
	public boolean addAll(Collection<? extends BaseBlock> c) {
		for (BaseBlock o : c) {
			o.setParent(this);
		}
		return blocks.addAll(c);
	}

	/**
	 * @see java.util.List
	 */
	public boolean addAll(int index, Collection<? extends BaseBlock> c) {
		for (BaseBlock o : c) {
			o.setParent(this);
		}
		return blocks.addAll(index, c);
	}

	/**
	 * @see java.util.List
	 */
	public void clear() {
		blocks.clear();
	}

	/**
	 * @see java.util.List
	 */
	public boolean contains(Object o) {
		return blocks.contains(o);
	}

	/**
	 * @see java.util.List
	 */
	public boolean containsAll(Collection<?> c) {
		return blocks.containsAll(c);
	}

	/**
	 * @see java.util.List
	 */
	public BaseBlock get(int index) {
		return blocks.get(index);
	}

	/**
	 * @see java.util.List
	 */
	public int indexOf(Object o) {
		return blocks.indexOf(o);
	}

	/**
	 * @see java.util.List
	 */
	public boolean isEmpty() {
		return blocks.isEmpty();
	}

	/**
	 * @see java.util.List
	 */
	public Iterator<BaseBlock> iterator() {
		return blocks.iterator();
	}

	/**
	 * @see java.util.List
	 */
	public int lastIndexOf(Object o) {
		return blocks.lastIndexOf(o);
	}

	public ListIterator<BaseBlock> listIterator() {
		return blocks.listIterator();
	}

	/**
	 * @see java.util.List
	 */
	public ListIterator<BaseBlock> listIterator(int index) {
		return blocks.listIterator(index);
	}

	/**
	 * @see java.util.List
	 */
	public boolean remove(Object o) {
		return blocks.remove(o);
	}

	public BaseBlock remove(int index) {
		return blocks.remove(index);
	}

	/**
	 * @see java.util.List
	 */
	public boolean removeAll(Collection<?> c) {
		return blocks.removeAll(c);
	}

	/**
	 * @see java.util.List
	 */
	public boolean retainAll(Collection<?> c) {
		return blocks.retainAll(c);
	}

	/**
	 * @see java.util.List
	 */
	public BaseBlock set(int index, BaseBlock element) {
		element.setParent(this);
		return blocks.set(index, element);
	}

	/**
	 * @see java.util.List
	 */
	public int size() {
		return blocks.size();
	}

	/**
	 * @see java.util.List
	 */
	public List<BaseBlock> subList(int fromIndex, int toIndex) {
		return blocks.subList(fromIndex, toIndex);
	}

	/**
	 * BasicHeaderBlockをセットする
	 *
	 * @param block1
	 */
	public void setBlock1(BasicHeaderBlock block1) {
		block1.setParent(this);
		blocks.add(block1);
	}

	/**
	 * BasicHeaderBlockを取得する
	 *
	 * @return
	 */
	public BasicHeaderBlock getBlock1() {
		BasicHeaderBlock block1 = (BasicHeaderBlock) this.getFirstBlock(BaseBlock.BLOCK_1);
		if (block1 == null) {
			try {
				block1 = (BasicHeaderBlock) BaseBlock.newInstance(this, BaseBlock.BLOCK_1);
				block1.setParent(this);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			this.add(block1);
		}
		return block1;
	}

	/**
	 * ApplicationHeaderBlockをセットする
	 *
	 * @param block2
	 */
	public void setBlock2(ApplicationHeaderBlock block2) {
		block2.setParent(this);
		blocks.add(block2);
	}

	/**
	 * ApplicationHeaderBlockを取得する
	 *
	 * @return
	 */
	public ApplicationHeaderBlock getBlock2() {
		ApplicationHeaderBlock block2 = (ApplicationHeaderBlock) this.getFirstBlock(BaseBlock.BLOCK_2);
		if (block2 == null) {
			try {
				block2 = (ApplicationHeaderBlock)SwiftBlockFactory.newInstance("ApplicationHeaderBlockInput");
				block2.setParent(this);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			this.add(block2);
		}
		return block2;
	}

	/**
	 * UserHeaderBlockをセットする
	 *
	 * @param block3
	 */
	public void setBlock3(UserHeaderBlock block3) {
		block3.setParent(this);
		blocks.add(block3);
	}

	/**
	 * UserHeaderBlockを取得する
	 *
	 * @return
	 */
	public UserHeaderBlock getBlock3() {
		UserHeaderBlock block3 = (UserHeaderBlock) this.getFirstBlock(BaseBlock.BLOCK_3);
		if (block3 == null) {
			try {
				block3 = (UserHeaderBlock) BaseBlock.newInstance(this, BaseBlock.BLOCK_3);
				block3.setParent(this);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			this.add(block3);
		}
		return block3;
	}

	/**
	 * TextBlockをセットする
	 *
	 * @param block4
	 */
	public void setBlock4(TextBlock block4) {
		block4.setParent(this);
		blocks.add(block4);
	}

	/**
	 * TextBlockを取得する
	 *
	 * @return
	 */
	public TextBlock getBlock4() {
		TextBlock block4 = null;
		for (BaseBlock block : this.blocks) {
			if (block instanceof TextBlock) {
				block4 = (TextBlock) block;
				break;
			}
		}
		if (block4 == null) {
			try {
				block4 = (TextBlock) BaseBlock.newInstance(this, BaseBlock.BLOCK_4);
				block4.setParent(this);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			this.add(block4);
		}
		return block4;
	}

	/**
	 * @see java.util.List
	 */
	public Object[] toArray() {
		return blocks.toArray();
	}

	/**
	 * @see java.util.List
	 */
	public <T> T[] toArray(T[] a) {
		return blocks.toArray(a);
	}

	/**
	 * ISO15022形式出力
	 *
	 * @return ISO15022形式 SWIFT メッセージ
	 */
	public String toSwiftMessage() {
		StringBuilder text = new StringBuilder();
		for (BaseBlock block : blocks) {
			text.append(block.toSwiftMessage());
		}
		return text.toString();
	}

	@Override
	public String toString() {
		return "SwiftMessage [blocks=" + blocks + "]";
	}
}

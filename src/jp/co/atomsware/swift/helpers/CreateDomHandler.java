package jp.co.atomsware.swift.helpers;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import jp.co.atomsware.swift.SwiftFormatException;
import jp.co.atomsware.swift.SwiftMessage;
import jp.co.atomsware.swift.block.ApplicationHeaderBlock;
import jp.co.atomsware.swift.block.BaseBlock;
import jp.co.atomsware.swift.block.TextBlock;
import jp.co.atomsware.swift.tag.BaseTag;

/**
 * Document Object Model 形式でSWIFTメッセージ群を取得
 *
 * @author T.Shimada
 *
 */
public class CreateDomHandler implements HadlerBase {

	/** SWIFTメッセージ群 */
	protected List<SwiftMessage> swiftMessages = new ArrayList<SwiftMessage>();

	/** 読み込み中のSWIFTメッセージ */
	private SwiftMessage topSwiftMessage;
	private SwiftMessage currentSwiftMessage;
	/** 読み込み中のBLOCK */
	private LinkedList<BaseBlock> blocks = new LinkedList<BaseBlock>();

	/**
	 * Document Object Model 形式で ISO15022を読み込む
	 */
	public CreateDomHandler() {

	}

	/**
	 * SWIFTメッセージの読み込み開始
	 */
	public void startDocument(int line, int pos) {
		topSwiftMessage=  new SwiftMessage();
		currentSwiftMessage = topSwiftMessage;
	}

	/**
	 * BLOCKの開始
	 *
	 * @param level
	 *            階層
	 * @param line
	 *            ファイル内の行数
	 * @param pos
	 *            行内の位置
	 * @param blockNo
	 *            ブロック名
	 */
	public void startBlock(int level, int line, int pos, String blockNo) {

		currentSwiftMessage = topSwiftMessage;
		//System.out.println(level + ":" + blockNo + "(" + line + "," + pos + ")");
		if (level > 1) {
			TextBlock text = (TextBlock) topSwiftMessage.getLastBlock(BaseBlock.BLOCK_4);
			currentSwiftMessage = text.getSwiftMessage();
			if (currentSwiftMessage == null) {
				currentSwiftMessage = new SwiftMessage();
				text.setSwiftMessage(currentSwiftMessage);
			}
		}
		BaseBlock block;
		try {
			block = BaseBlock.newInstance(currentSwiftMessage, blockNo);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		if (!BaseBlock.BLOCK_2.equals(blockNo)) {
			currentSwiftMessage.add(block);
		}
		blocks.addLast(block);

	}

	/**
	 * TAGの開始
	 *
	 * @param level
	 *            階層
	 * @param line
	 *            ファイル内の行数
	 * @param pos
	 *            行内の位置
	 * @param tagName
	 *            TAG名
	 */
	public void startTag(int level, int line, int pos, String tagName) {
		//
	}

	/**
	 * TAGの終了
	 *
	 * @param level
	 *            階層
	 * @param line
	 *            ファイル内の行数
	 * @param pos
	 *            行内の位置
	 * @param tagName
	 *            TAG名
	 * @param tagData
	 *            TAG内の値
	 */
	public void endTag(int level, int line, int pos, String tagName,
			String tagData) throws SwiftFormatException {
		// TAGインスタンスの設定
		BaseTag tag;
		try {
			tag = BaseTag.newInstance(tagName, tagData);
		} catch (Exception e) {
			throw new SwiftFormatException(level, "Tag is unknown. tagName="
					+ tagName, line, pos, e);
		}
		BaseBlock block = blocks.getLast();
		if (block == null) {
			throw new SwiftFormatException(level,
					"Cuurent block is null. blocks=" + blocks.size(), line, pos);
		}
		block.addTag(tag);
	}

	/**
	 * BLOCKの終了
	 *
	 * @param level
	 *            階層
	 * @param line
	 *            ファイル内の行数
	 * @param pos
	 *            行内の位置
	 * @param blockNo
	 *            BLOCK名
	 * @param blockData
	 *            BLOCK内の値
	 */
	public void endBlock(int level, int line, int pos, String blockNo,
			String blockData) throws SwiftFormatException {
		// BLOCKインスタンスの設定
		BaseBlock block;
		if (BaseBlock.BLOCK_2.equals(blockNo)) {
			BaseBlock before = blocks.getLast();
			try {
				block = ApplicationHeaderBlock.newInstance(blockData);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			block.addTags(before.getTags());
			currentSwiftMessage.add(block);
		} else {
			block = blocks.getLast();
		}
		if (block == null) {
			throw new SwiftFormatException(level,
					"Cuurent block is null. blocks=" + blocks.size(), line, pos);
		}
		block.setBlockID(blockNo);
		block.setValue(blockData);
		blocks.removeLast();
	}

	/**
	 * BLOCK内、TAG内での文字列読み込み時のイベント
	 *
	 * @param line
	 *            ファイル内の行数
	 * @param pos
	 *            行内の位置
	 * @param buffer
	 *            読み込んだ文字
	 */
	public void character(int line, int pos, char buffer) {
		//
	}

	/**
	 * ISO15022電文の終了
	 *
	 * @param line
	 *            ファイル内の行数
	 * @param pos
	 *            行内の位置
	 */
	public void endDocument(int line, int pos) {
		swiftMessages.add(currentSwiftMessage);
	}

	/**
	 * Document Object Model 形式でSWIFTメッセージ群を取得
	 *
	 * @return List<SwiftMessage>
	 */
	public List<SwiftMessage> getSwiftMessages() {
		return swiftMessages;
	}

	/**
	 * Document Object Model 形式でSWIFTメッセージを取得
	 *
	 * @return SwiftMessage
	 */
	public SwiftMessage getSwiftMessage() {
		if (swiftMessages == null)
			return null;
		return swiftMessages.get(0);
	}

}

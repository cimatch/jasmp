package jp.co.atomsware.swift;

import java.io.Serializable;

/**
 * BLOCKやTAGの基底クラス
 *
 * @author T.shimada
 *
 */
public abstract class Element implements Serializable  {

	private static final long serialVersionUID = 1L;

	/**
	 * ISO15022形式出力
	 *
	 * @return ISO15022形式 SWIFT メッセージ
	 */
	public abstract String toSwiftMessage();

}

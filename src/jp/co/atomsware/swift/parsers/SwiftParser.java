package jp.co.atomsware.swift.parsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import jp.co.atomsware.swift.SwiftFormatException;
import jp.co.atomsware.swift.helpers.HadlerBase;

/**
 * パーサ
 *
 * @author T.Shimada
 *
 */
public abstract class SwiftParser {

	/** 内部変数 */
	protected HadlerBase handler;

	/**
	 * ハンドラの取得
	 *
	 * @return HadlerBase
	 */
	public HadlerBase getHandler() {
		return handler;
	}

	/**
	 * ハンドラ設定
	 *
	 * @param handler
	 *            HadlerBase
	 */
	public void setHandler(HadlerBase handler) {
		this.handler = handler;
	}

	/**
	 * SWIFTフォーマットの解析
	 *
	 * @param paramInputStream
	 * @param paramHandlerBase
	 * @throws IOException
	 * @throws SwiftFormatException
	 */
	public void parse(InputStream paramInputStream, HadlerBase paramHandlerBase)
			throws IOException, SwiftFormatException {
		if (paramInputStream == null) {
			throw new IllegalArgumentException("InputStream cannot be null");
		}
		if (paramHandlerBase == null) {
			throw new IllegalArgumentException("Handler cannot be null");
		}
		handler = paramHandlerBase;
		parse(paramInputStream);
	}

	/**
	 * SWIFTフォーマットの解析
	 *
	 * @param file
	 * @param hadler
	 * @throws IOException
	 * @throws SwiftFormatException
	 */
	public void parse(File file, HadlerBase hadler) throws IOException,
			SwiftFormatException {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			parse(fis, hadler);
		} finally {
			if (fis != null)
				fis.close();
		}

	}

	/**
	 * SWIFTフォーマットの解析
	 *
	 * @param paramInputStream
	 * @throws IOException
	 * @throws SwiftFormatException
	 */
	public abstract void parse(InputStream paramInputStream)
			throws IOException, SwiftFormatException;

}

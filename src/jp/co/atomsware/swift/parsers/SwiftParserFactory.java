package jp.co.atomsware.swift.parsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import jp.co.atomsware.swift.tag.SwiftTagFactory;

/**
 * パーサファクトリ
 *
 * @author T.Shimada
 *
 */
public class SwiftParserFactory {

	/** パーサ定義 **/
	private static final String BASE_DIR = "/parserdefine/";

	/** リソースキー */
	private static final String DEFAULT_SWIFT_PARSER_FACTORY = "DefaultSwiftParserFactory";

	/** リソースキー */
	private static final String DEFAULT_SWIFT_PARSER = "DefaultSwiftParser";

	/** プロパティ名 */
	private static final String BUNDLE_NAME = "jp.co.atomsware.swift.parsers.SwiftParserFactory";//$NON-NLS-1$

	private static final Properties properties;

	/** ファイルの読み込み */
	static {
        properties = new Properties();
		try {
			ResourceBundle def = ResourceBundle.getBundle(BUNDLE_NAME);
			for (Enumeration<String> e = def.getKeys(); e.hasMoreElements();) {
				String key = e.nextElement();
				properties.put(key, def.getString(key));
			}
			Object val = SwiftTagFactory.class.getResource(BASE_DIR);
			if (val != null) {
				File dir = new File(SwiftTagFactory.class.getResource(BASE_DIR).getPath());
				File[] files = dir.listFiles();
				for (File propertiesFile : files) {
					properties.load(new FileInputStream(propertiesFile));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	/**
	 * キーの取得
	 *
	 * @return キー
	 */
	public static Enumeration<Object> getKeys() {
		return properties.keys();
	}

	/**
	 * プロパティファイルの読み込み
	 *
	 * @param key
	 * @return 値
	 */
	public static String getString(String key) {
		try {
			return properties.getProperty(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}

	/**
	 * 未使用
	 */
	protected SwiftParserFactory() {
	}

	/**
	 * デフォルトのSwiftParserFactoryの取得
	 *
	 * @return SwiftParserFactory
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static SwiftParserFactory newInstance()
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		return newInstance(getString(DEFAULT_SWIFT_PARSER_FACTORY));
	}

	/**
	 * SwiftParserFactoryの取得
	 *
	 * @param className
	 *            ファクトリクラス名
	 * @return SwiftParserFactory
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	public static SwiftParserFactory newInstance(String className)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		Class<SwiftParserFactory> cls;
		SwiftParserFactory obj = null;
		try {
			cls = (Class<SwiftParserFactory>) Class.forName(className);
			obj = cls.newInstance();
		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundException(className, e);
		}
		return obj;
	}

	/**
	 * SwiftParserの取得
	 *
	 * @return SwiftParser
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public SwiftParser newSwiftParser() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		return newSwiftParser(getString(DEFAULT_SWIFT_PARSER));
	}

	/**
	 * SwiftParserの取得
	 *
	 * @param className
	 *            パーサクラス名
	 * @return SwiftParser
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	public SwiftParser newSwiftParser(String className)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		Class<SwiftParser> cls;
		SwiftParser obj = null;
		try {
			cls = (Class<SwiftParser>) Class.forName(className);
			obj = cls.newInstance();
		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundException(className, e);
		}
		return obj;
	}

}

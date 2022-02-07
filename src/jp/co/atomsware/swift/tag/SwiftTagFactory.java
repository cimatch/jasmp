package jp.co.atomsware.swift.tag;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * パーサファクトリ
 *
 * @author T.Shimada
 *
 */
public class SwiftTagFactory {

	/** タグ定義 **/
	private static final String BASE_DIR = "/tagdefine/";

	/** プロパティ名 */
	private static final String BUNDLE_NAME = "jp.co.atomsware.swift.tag.SwiftTagFactory";//$NON-NLS-1$

	/** ファイルの読み込み */
	private static final Properties properties;

	static {
        properties = new Properties();
		try {
			ResourceBundle def = ResourceBundle.getBundle(BUNDLE_NAME);
			for (Enumeration<String> e = def.getKeys(); e.hasMoreElements();) {
				String key = e.nextElement();
				properties.put(key, def.getString(key));
			}
			URL url = SwiftTagFactory.class.getResource(BASE_DIR);
			if (url != null) {
				if (url.getProtocol().equals("jar")) {
					String[] s = url.getPath().split(":");
					String path = s[s.length - 1].split("!")[0];
					File f = new File(path);
					JarFile jarFile = new JarFile(f);
					Enumeration<JarEntry> entries = jarFile.entries();
					while (entries.hasMoreElements()) {
						JarEntry entry = entries.nextElement();
						String name = entry.getName();
						//System.out.println(name);
						if (name != null && name.startsWith(BASE_DIR.substring(1)) && name.endsWith("properties")) {
							//System.out.println("path:" + name);
							SwiftTagFactory.load(SwiftTagFactory.class.getClassLoader().getResourceAsStream(name));
						}
					}
					jarFile.close();
				} else {
					//System.out.println("file");
					File dir = new File(url.getPath());
					File[] files = dir.listFiles();
					for (File propertiesFile : files) {
						properties.load(new FileInputStream(propertiesFile));
					}
				}
			}
		} catch (Exception e) {
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

	public static void load(InputStream inStream) throws IOException {
		properties.load(inStream);
	}

	/**
	 * 未使用
	 */
	protected SwiftTagFactory() {
	}

	/**
	 * Tagの取得
	 *
	 * @param className
	 *            ファクトリクラス名
	 * @return BaseTag
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 */
	@SuppressWarnings("unchecked")
	public static BaseTag newInstance(String className)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException {
		Class<BaseTag> cls;
		BaseTag obj = null;
		try {
			cls = (Class<BaseTag>) Class.forName(className, true,
					Thread.currentThread().getContextClassLoader());
			obj = cls.getDeclaredConstructor().newInstance();
		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundException(className, e);
		}
		return obj;
	}

}

package org.mobylet.core.xml;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.util.ResourceUtils;
import org.mobylet.core.util.StringUtils;

import sun.reflect.misc.FieldUtil;

/**
 *
 * 汎用的なXMLリーダクラス.<br />
 * <p>
 * XMLをパース及びノードに分解し、
 * 専用のXPath記述でデータへアクセスすることが可能。<br />
 * また、指定したDtoに対して値をマッピングすることも可能。
 * </p>
 *
 * @author Shin Takeuchi
 *
 */
public class Xml {

	/**
	 * 汎用XMLハンドラ
	 */
	protected XmlHandler handler;

	/**
	 * ルートノード
	 */
	protected XmlNode root;

	/**
	 * クラスローダ
	 */
	protected ClassLoader classLoader;

	/**
	 * リトライ可能数
	 */
	protected int retryCount;

	/**
	 * ファイル最終更新日時
	 */
	protected long lastModified;

	/**
	 * XMLファイルインスタンス
	 */
	private File xmlFile;

	/**
	 * XMLストリーム
	 */
	private InputStream xmlStream;

	/**
	 * 動的リロードフラグ
	 */
	private boolean isDynamicLoad;


	/**
	 * 汎用XMLリーダインスタンスを生成する.<br />
	 *
	 * @param filepath
	 * 		XMLファイルパス
	 */
	public Xml(String filepath) {
		this(filepath, 3);
	}

	/**
	 * 汎用XMLリーダインスタンスを生成する.<br />
	 *
	 * @param filepath
	 * 		XMLファイルパス
	 * @param retryCount
	 * 		XMLリードリトライカウント（動的リロード有効時）
	 */
	public Xml(String filepath, int retryCount) {
		this(ResourceUtils.getResourceFileOrInputStream(filepath), retryCount);
	}

	/**
	 * 汎用XMLリーダインスタンスを生成する.<br />
	 *
	 * @param inputStream
	 * 		XMLストリーム
	 * @param retryCount
	 * 		XMLリードリトライカウント（動的リロード有効時）
	 */
	public Xml(InputStream inputStream, int retryCount) {
		this.xmlStream = inputStream;
		this.retryCount = retryCount;
		this.handler = new XmlHandler();
		this.isDynamicLoad = false;
		this.load();
	}

	/**
	 * ルートノードを取得する.<br />
	 *
	 * @return
	 * 		ルートノード
	 */
	public XmlNode getRootNode() {
		this.update(0);
		return this.root;
	}

	/**
	 * 動的リロード設定メソッド.<br />
	 *
	 * @param isDynamicLoad
	 * 		動的リロード設定フラグ
	 */
	public void setDynamicLoad(boolean isDynamicLoad) {
		this.isDynamicLoad = isDynamicLoad;
	}

	/**
	 * XPathで指定した構造データを指定したクラスへマッピングする.<br />
	 *
	 * @param <T>
	 * 		データクラス（extends Object）
	 * @param xpath
	 * 		構造データまでのXPath
	 * @param clazz
	 * 		マッピングするデータクラス
	 * @return
	 * 		マッピング結果（データクラスのリスト）
	 */
	public <T extends Object> List<T> copyToBean(String xpath, Class<T> clazz) {
		return this.copyToBean(xpath, clazz, null);
	}

	/**
	 * XPathで指定した構造データを指定したクラスへマッピングする.<br />
	 *
	 * @param <T>
	 * 		データクラス（extends Object）
	 * @param xpath
	 * 		構造データまでのXPath
	 * @param clazz
	 * 		マッピングするデータクラス
	 * @param attName
	 * 		マッピング時に有効化する属性名
	 * @return
	 * 		マッピング結果（データクラスのリスト）
	 */
	public <T extends Object> List<T> copyToBean(String xpath, Class<T> clazz, String attName) {
		List<T> beanList = new ArrayList<T>();
		try {
			List<XmlNode> nodeList =
				this.getRootNode().getNodeListByXPath(xpath);
			Field[] fields = clazz.getDeclaredFields();
			for (XmlNode node : nodeList) {
				List<XmlNode> children = node.getChildren();
				T bean = clazz.newInstance();
				for (Field field : fields) {
					field.setAccessible(true);
					List<XmlNode> selectedChildren =
						node.getChildren(field.getName());
					if (selectedChildren != null && selectedChildren.size() > 0) {
						FieldUtil.set(field, bean, selectedChildren.get(0).getValue());
					} else if (node.getAttributes().containsKey(field.getName())) {
						FieldUtil.set(field, bean, node.getAttributes().get(field.getName()));
					} else if (StringUtils.isNotEmpty(attName)) {
						for (XmlNode child : children) {
							if (StringUtils.equals(
									child.getAttributes().get(attName),
									field.getName())) {
								FieldUtil.set(field, bean, child.getValue());
							}
						}
					}
				}
				beanList.add(bean);
			}
		} catch (Exception e) {
			throw new MobyletRuntimeException("XMLノード情報構築中に例外発生", e);
		}
		return beanList;
	}

	/**
	 * XPathで指定した構造データを指定したクラスへマッピングする.<br />
	 *
	 * @param <T>
	 * 		データクラス（extends Object）
	 * @param xpath
	 * 		構造データまでのXPath
	 * @param clazz
	 * 		マッピングするデータクラス
	 * @param keyField
	 * 		マップのキーに指定するフィールド
	 * @return
	 * 		マッピング結果（データクラスのリスト）
	 */
	public <T extends Object> Map<String, T> copyToBeanMap(String xpath, Class<T> clazz, String keyField) {
		return this.copyToBeanMap(xpath, clazz, null, keyField);
	}

	/**
	 * XPathで指定した構造データを指定したクラスへマッピングする.<br />
	 *
	 * @param <T>
	 * 		データクラス（extends Object）
	 * @param xpath
	 * 		構造データまでのXPath
	 * @param clazz
	 * 		マッピングするデータクラス
	 * @param attName
	 * 		マッピング時に有効化する属性名
	 * @param keyField
	 * 		マップのキーに指定するフィールド
	 * @return
	 * 		マッピング結果（データクラスのリスト）
	 */
	public <T extends Object> Map<String, T> copyToBeanMap(String xpath, Class<T> clazz, String attName, String keyField) {
		List<T> beanList = this.copyToBean(xpath, clazz, attName);
		return beanListToMap(beanList, clazz, keyField);
	}

	static <T extends Object> Map<String, T>  beanListToMap(List<T> beanList,Class<T> clazz,String keyField){
		try {
			Map<String, T> beanMap = new LinkedHashMap<String, T>();

			Field field = clazz.getDeclaredField(keyField);
			field.setAccessible(true);
			for (T bean : beanList) {
				beanMap.put(FieldUtil.getString(field, bean), bean);
			}
			return beanMap;
		} catch (Exception e) {
			throw new BizReachException(e);
		}
	}

	/**
	 * 動的リロード実施チェック.<br />
	 *
	 * @return
	 * 		true: XMLファイルが更新されている, false: XMLファイルが更新されていない
	 */
	public boolean isUpdate() {
		if (this.xmlFile == null) {
			return false;
		}
		return this.isDynamicLoad &&
			this.lastModified < this.xmlFile.lastModified();
	}

	/**
	 * リロードメソッド.<br />
	 */
	protected void load() {
		synchronized (this) {
			try {
				SAXParserFactory spfactory = SAXParserFactory.newInstance();
				SAXParser parser = spfactory.newSAXParser();
				this.handler.initialize();
				if (this.xmlFile != null &&
						this.xmlFile.exists() && this.xmlFile.isFile()) {
					parser.parse(this.xmlFile, this.handler);
					this.root = this.handler.getRootNode();
				} else if (this.xmlStream != null) {
					parser.parse(this.xmlStream, this.handler);
					this.root = this.handler.getRootNode();
				}
			} catch (Exception e) {
				throw new BizReachException(e);
			}
		}
	}

	/**
	 * 動的リロード発動メソッド.<br />
	 *
	 * @param retry
	 * 		リトライカウント
	 */
	private void update(int retry) {
		if (this.isUpdate()) {
			try {
				this.load();
			} catch (Exception e) {
				if (retry < this.retryCount) {
					this.update(retry+1);
					return;
				}
			}
			this.lastModified = (this.xmlFile == null) ?
					0L : this.xmlFile.lastModified();
		}
	}
}

/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.mobylet.core.xml;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.util.FieldUtils;
import org.mobylet.core.util.ResourceUtils;
import org.mobylet.core.util.StringUtils;

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
	 * XMLストリーム
	 */
	private InputStream xmlStream;


	/**
	 * 汎用XMLリーダインスタンスを生成する.<br />
	 *
	 * @param filepath
	 * 		XMLファイルパス
	 */
	public Xml(String filepath) {
		this(ResourceUtils.getResourceFileOrInputStream(filepath));
	}

	/**
	 * 汎用XMLリーダインスタンスを生成する.<br />
	 *
	 * @param inputStream
	 * 		XMLストリーム
	 */
	public Xml(InputStream inputStream) {
		this.xmlStream = inputStream;
		this.handler = new XmlHandler();
		this.load();
	}

	/**
	 * ルートノードを取得する.<br />
	 *
	 * @return
	 * 		ルートノード
	 */
	public XmlNode getRootNode() {
		return this.root;
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
						FieldUtils.set(field, bean, selectedChildren.get(0).getValue());
					} else if (node.getAttributes().containsKey(field.getName())) {
						FieldUtils.set(field, bean, node.getAttributes().get(field.getName()));
					} else if (StringUtils.isNotEmpty(attName)) {
						for (XmlNode child : children) {
							if (StringUtils.equals(
									child.getAttributes().get(attName),
									field.getName())) {
								FieldUtils.set(field, bean, child.getValue());
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
				beanMap.put(FieldUtils.getString(field, bean), bean);
			}
			return beanMap;
		} catch (Exception e) {
			throw new MobyletRuntimeException("XML情報をMapへセット出来ません", e);
		}
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
				if (this.xmlStream != null) {
					parser.parse(this.xmlStream, this.handler);
					this.root = this.handler.getRootNode();
				}
			} catch (Exception e) {
				throw new MobyletRuntimeException("XML情報をロード出来ませんでした", e);
			}
		}
	}
}

/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mobylet.core.util.ObjectUtils;
import org.mobylet.core.util.StringUtils;

/**
 *
 * 解析したXMLノード情報.<br />
 * <p>
 * XMLノードひとつに対するノード情報を保持する.<br />
 * このノードには単一の親ノードと複数の子ノードが紐付き、
 * 中間ノードからどのノードでも辿ることが可能。
 * </p>
 *
 * @author Shin Takeuchi
 *
 */
public class XmlNode {

	/**
	 * シリアルバージョンUID
	 */
	private static final long serialVersionUID = -2224946250718469933L;

	/**
	 * バリュー探索パターン
	 */
	private static final Pattern VAL_PATTERN = Pattern.compile("[^@]+\\(.+\\)");

	/**
	 * 属性値探索パターン
	 */
	private static final Pattern ATT_PATTERN = Pattern.compile(".+@.+\\(.+\\)");


	/**
	 * ノード名（QName）
	 */
	protected String name;

	/**
	 * ノードバリュー
	 */
	protected String value;

	/**
	 * 属性値マップ
	 */
	protected Map<String, String> attributes;

	/**
	 * 子ノードリスト
	 */
	protected List<XmlNode> children;

	/**
	 * 親ノード
	 */
	protected XmlNode parent;


	/**
	 * ノードインスタンス化.<br />
	 *
	 * @param name
	 * 		ノード名
	 */
	public XmlNode(String name) {
		this.name = name;
		this.attributes = new LinkedHashMap<String, String>();
		this.children = new ArrayList<XmlNode>();
	}

	/**
	 * ノード名取得メソッド.<br />
	 *
	 * @return
	 * 		ノード名
	 */
	public String getName() {
		return name;
	}

	/**
	 * ノード名設定メソッド.<br />
	 *
	 * @param name
	 * 		ノード名
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ノードバリュー取得メソッド.<br />
	 *
	 * @return
	 * 		ノードバリュー
	 */
	public String getValue() {
		return value;
	}

	/**
	 * ノードバリュー取得メソッド.<br />
	 *
	 * @param <T>
	 * 		内部キャストを行う型
	 * @param clazz
	 * 		内部キャストを行う型
	 * return
	 * 		ノードバリュー
	 */
	@SuppressWarnings("unchecked")
	public <T extends Object> T getValue(Class<T> clazz) {
		return (T)value;
	}

	/**
	 * ノードバリュー設定メソッド.<br />
	 *
	 * @param value
	 * 		ノードバリュー
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 属性値マップ取得メソッド.<br />
	 *
	 * @return
	 * 		属性値マップ
	 */
	public Map<String, String> getAttributes() {
		return attributes;
	}

	/**
	 * 属性値マップ設定メソッド.<br />
	 *
	 * @param attributes
	 * 		属性値マップ
	 */
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	/**
	 * 属性値追加メソッド.<br /
	 *
	 * @param name
	 * 		属性名
	 * @param value
	 * 		属性値
	 */
	public void addAttribute(String name, String value) {
		this.attributes.put(name, value);
	}

	/**
	 * 子ノード情報取得メソッド.<br />
	 *
	 * @return
	 * 		子ノードリスト
	 */
	public List<XmlNode> getChildren() {
		return children;
	}

	/**
	 * 子ノード情報取得メソッド.<br />
	 * <p>
	 * 指定したQNameの子ノードのみを取得する.
	 * </p>
	 *
	 * @param qname
	 * 		QName
	 * @return
	 * 		子ノードリスト
	 */
	public List<XmlNode> getChildren(String qname) {
		List<XmlNode> childList = new ArrayList<XmlNode>();
		for (XmlNode node : this.children) {
			if (StringUtils.equals(node.getName(), qname)) {
				childList.add(node);
			}
		}
		return childList;
	}

	/**
	 * 子ノード情報取得メソッド.<br />
	 * <p>
	 * 指定したQNameに対して、指定した値を持つ子ノード情報を取得する
	 * </p>
	 *
	 * @param qname
	 * 		QName
	 * @param value
	 * 		QNameに対するノードバリュー
	 * @return
	 * 		子ノードリスト
	 */
	public List<XmlNode> getChildrenByValue(String qname, Object value) {
		List<XmlNode> childList = new ArrayList<XmlNode>();
		for (XmlNode node : this.children) {
			if (StringUtils.equals(node.getName(), qname) &&
					ObjectUtils.equals(node.getValue(), value)) {
				childList.add(node);
			}
		}
		return childList;
	}

	/**
	 * 子ノード情報取得メソッド.<br />
	 * <p>
	 * 指定したQNameの指定した属性名に、指定した値を持つ子ノード情報を取得する
	 * </p>
	 *
	 * @param qname
	 * 		QName
	 * @param name
	 * 		属性名
	 * @param value
	 * 		属性値
	 * @return
	 * 		子ノードリスト
	 */
	public List<XmlNode> getChildrenByAttribute(String qname, String name, String value) {
		List<XmlNode> childList = new ArrayList<XmlNode>();
		for (XmlNode node : this.children) {
			if (StringUtils.equals(node.getName(), qname) &&
					ObjectUtils.equals(node.getAttributes().get(name), value)) {
				childList.add(node);
			}
		}
		return childList;
	}

	/**
	 * 子ノード追加メソッド.<br />
	 *
	 * @param child
	 * 		子ノード
	 */
	public void addChild(XmlNode child) {
		this.children.add(child);
	}

	/**
	 * 親ノード取得メソッド.<br />
	 *
	 * @return
	 * 		親ノード
	 */
	public XmlNode getParent() {
		return parent;
	}

	/**
	 * 親ノード設定メソッド.<br />
	 *
	 * @param parent
	 * 		親ノード
	 */
	public void setParent(XmlNode parent) {
		this.parent = parent;
	}

	/**
	 * 独自のXPathからノードリストを取得するメソッド.<br />
	 *
	 * @param xpath
	 * 		XPath（相対パス：自身のパスは含まない）
	 * @return
	 * 		ノードリスト
	 */
	public List<XmlNode> getNodeListByXPath(String xpath) {
		List<XmlNode> nodeList = new ArrayList<XmlNode>();
		if (xpath == null) {
			return nodeList;
		}
		List<XmlNode> tempList = new ArrayList<XmlNode>();
		nodeList.add(this);
		String[] pathes = xpath.split("/");
		for (String path : pathes) {
			// エスケープされた文字を置換
			path = path.replaceAll("_／", "/");
			if (StringUtils.isEmpty(path)) {
				continue;
			}
			tempList.clear();
			tempList.addAll(nodeList);
			nodeList.clear();
			Matcher valMatcher = VAL_PATTERN.matcher(path);
			Matcher attMatcher = ATT_PATTERN.matcher(path);
			if (valMatcher.matches()) {
				String qname = path.substring(0, path.indexOf("("));
				String value = path.substring(path.indexOf("(") + 1, path.indexOf(")"));
				for (XmlNode node : tempList) {
					nodeList.addAll(node.getChildrenByValue(qname, value));
				}
			} else if (attMatcher.matches()) {
				String qname = path.substring(0, path.indexOf("@"));
				String name = path.substring(path.indexOf("@") + 1, path.indexOf("("));
				String value = path.substring(path.indexOf("(") + 1, path.indexOf(")"));
				for (XmlNode node : tempList) {
					nodeList.addAll(node.getChildrenByAttribute(qname, name, value));
				}
			} else {
				for (XmlNode node : tempList) {
					nodeList.addAll(node.getChildren(path));
				}
			}
		}
		return nodeList;
	}

}

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

import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * 汎用XMLハンドラ.<br />
 * <p>
 * スキーマに依存せずXMLを解釈し、ノード化する.
 * </p>
 *
 * @author Shin Takeuchi
 *
 */
public class XmlHandler extends DefaultHandler {

	/**
	 * XMl解釈用Stack
	 */
	protected Stack<XmlNode> nodeStack;

	/**
	 * ルートノード
	 */
	protected XmlNode root;


	/**
	 * ハンドラインスタンス生成.<br />
	 *
	 * @param isOgnl
	 * 		OGNL有効フラグ
	 */
	public XmlHandler() {
		this.initialize();
	}

	/**
	 * ハンドラ初期化メソッド.<br />
	 */
	public void initialize() {
		this.nodeStack = null;
		this.root = null;
	}

	/**
	 * 解析済みのXMLルートノードインスタンスを取得する.<br />
	 *
	 * @return
	 * 		解析済みXMLルートノード
	 */
	public XmlNode getRootNode() {
		return this.root;
	}

	/*
	 * (非 Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startDocument()
	 */
	@Override
	public void startDocument() throws SAXException {
		this.nodeStack = new Stack<XmlNode>();
	}

	/*
	 * (非 Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#endDocument()
	 */
	@Override
	public void endDocument() throws SAXException {
		//NOP
	}

	/*
	 * (非 Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	@Override
	public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
		XmlNode node = new XmlNode(name);
		int attributeLength = attributes.getLength();
		for (int i = 0; i < attributeLength; i++) {
			node.addAttribute(attributes.getQName(i), attributes.getValue(i));
		}
		if (this.root == null) {
			this.root = node;
		} else {
			XmlNode parent = this.nodeStack.peek();
			parent.addChild(node);
			node.setParent(parent);
		}
		this.nodeStack.push(node);
	}

	/*
	 * (非 Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {
		XmlNode node = this.nodeStack.pop();
		Object nodeValue = node.getValue();
		if (nodeValue != null) {
			String nodeValueString = nodeValue.toString().trim();
			node.setValue(nodeValueString);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
	 */
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		XmlNode node = this.nodeStack.peek();
		if (node.getValue() == null) {
			node.setValue(new String(ch, start, length));
		} else {
			node.setValue(node.getValue() + new String(ch, start, length));
		}
	}

}

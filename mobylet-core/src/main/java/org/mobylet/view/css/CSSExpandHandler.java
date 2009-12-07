package org.mobylet.view.css;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.mobylet.core.util.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CSSExpandHandler extends DefaultHandler {

	public static final String LT = "<";

	public static final String GT = ">";

	public static final String SL = "/";


	protected CSSCondContainer container;

	protected XhtmlNode node;

	protected Stack<String> tagStack;

	protected Map<String, Integer> indexMap;

	protected StringBuilder buf;


	public CSSExpandHandler(CSSCondContainer container) {
		this.container = container;
	}

	@Override
	public void startDocument() throws SAXException {
		buf = new StringBuilder();
		tagStack = new Stack<String>();
		indexMap = new HashMap<String, Integer>();
	}

	@Override
	public void endDocument() throws SAXException {
		tagStack = null;
		indexMap = null;
	}

	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		Integer index = getIndex();
		tagStack.push(name);
		//Set-Node
		XhtmlNode newNode = new XhtmlNode(
				name,
				attributes.getValue("id"),
				attributes.getValue("class"),
				index);
		if (node != null) {
			newNode.setParent(node);
		}
		node = newNode;
		//Get-Style
		StringBuilder tagBuf = new StringBuilder();
		tagBuf.append(name);
		if (container != null) {
			String styleString = container.getStyle(node);
			if (attributes.getValue("style") != null) {
				styleString = attributes.getValue("style") + styleString;
			}
			boolean existsStyle = false;
			for (int i=0; i<attributes.getLength(); i++) {
				String attName = attributes.getQName(i);
				if ("style".equals(attName)) {
					tagBuf.append(" " + attName + "=\""+ styleString.replace("\"", "\\\"") + "\"");
					existsStyle = true;
				} else {
					tagBuf.append(" " + attName + "=\""+ attributes.getValue(attName).replace("\"", "\\\"") + "\"");
				}
			}
			if (!existsStyle && StringUtils.isNotEmpty(styleString)) {
				tagBuf.append(" style=\""+ styleString.replace("\"", "\\\"") + "\"");
			}
		} else {
			for (int i=0; i<attributes.getLength(); i++) {
				String attName = attributes.getQName(i);
				tagBuf.append(" " + attName + "=\""+ attributes.getValue(attName).replace("\"", "\\\"") + "\"");
			}
		}
		buf.append(LT + tagBuf.toString() + GT);
	}

	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		tagStack.pop();
		if (node != null) {
			node = node.getParent();
		}
		buf.append(LT + SL + name + GT);
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		buf.append(ch, start, length);
	}

	@Override
	public String toString() {
		return buf.toString();
	}

	protected Integer getIndex() {
		String parentNodeKey = tagStack.toString();
		Integer index = indexMap.get(parentNodeKey);
		if (index == null) {
			indexMap.put(parentNodeKey, 1);
			index = 1;
		} else {
			index++;
			indexMap.put(parentNodeKey, index);
		}
		return index;
	}
}

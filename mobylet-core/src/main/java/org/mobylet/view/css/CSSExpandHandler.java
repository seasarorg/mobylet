package org.mobylet.view.css;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.mobylet.core.util.StringUtils;
import org.mobylet.view.xhtml.TagAttributes;
import org.mobylet.view.xhtml.XhtmlHandler;
import org.mobylet.view.xhtml.XhtmlNode;

public class CSSExpandHandler implements XhtmlHandler {

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
	public void startDocument() {
		buf = new StringBuilder();
		tagStack = new Stack<String>();
		indexMap = new HashMap<String, Integer>();
	}

	@Override
	public void endDocument() {
		tagStack = null;
		indexMap = null;
	}

	@Override
	public void startElement(String name, TagAttributes attributes, boolean existsBody) {
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
			for (int i=0; i<attributes.size(); i++) {
				String attName = attributes.getName(i);
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
			for (int i=0; i<attributes.size(); i++) {
				String attName = attributes.getName(i);
				tagBuf.append(" " + attName + "=\""+ attributes.getValue(attName).replace("\"", "\\\"") + "\"");
			}
		}
		if (existsBody) {
			buf.append(LT + tagBuf.toString() + GT);
		} else {
			buf.append(LT + tagBuf.toString() + " " + SL + GT);
		}
	}

	@Override
	public void endElement(String name) {
		tagStack.pop();
		if (node != null) {
			node = node.getParent();
		}
		buf.append(LT + SL + name + GT);
	}

	@Override
	public void characters(char[] ch, int start, int length) {
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

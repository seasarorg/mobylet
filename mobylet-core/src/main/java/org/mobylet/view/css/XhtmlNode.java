package org.mobylet.view.css;

import java.util.LinkedHashSet;
import java.util.Set;


public class XhtmlNode {

	protected String tag;

	protected String styleId;

	protected Set<String> styleClasses;

	protected XhtmlNode parent;

	protected int index;


	protected XhtmlNode(String tag, String styleId, String styleClass,
			int index) {
		this.tag = tag;
		this.styleId = styleId;
		if (styleClass != null) {
			this.styleClasses = new LinkedHashSet<String>();
			for (String classStr : styleClass.split("\\s")) {
				this.styleClasses.add(classStr);
			}
		}
		this.index = index;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getStyleId() {
		return styleId;
	}

	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

	public Set<String> getStyleClasses() {
		return styleClasses;
	}

	public void setStyleClasses(Set<String> styleClasses) {
		this.styleClasses = styleClasses;
	}

	public XhtmlNode getParent() {
		return parent;
	}

	public void setParent(XhtmlNode parent) {
		this.parent = parent;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}

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
package org.mobylet.view.xhtml;

import java.util.LinkedHashSet;
import java.util.Set;


public class XhtmlNode {

	protected String tag;

	protected String styleId;

	protected Set<String> styleClasses;

	protected XhtmlNode parent;

	protected int index;


	public XhtmlNode(String tag, String styleId, String styleClass,
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

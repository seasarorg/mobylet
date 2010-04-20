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
package org.mobylet.view.css;

import java.util.HashMap;
import java.util.Map;

import org.mobylet.core.util.StringUtils;
import org.mobylet.view.xhtml.XhtmlNode;

public class CSSCond {

	protected String tag;

	protected String styleId;

	protected String styleClass;

	protected CSSCond parent;

	protected SelectorType selectorType;

	protected Map<String, String> valueMap;

	public CSSCond(String key) {
		if (key == null) {
			return;
		}
		if (key.endsWith(":first-child")) {
			key = key.substring(0, key.lastIndexOf(':'));
			selectorType = SelectorType.FIRST_CHILD;
		}
		int dot = key.indexOf('.');
		int shp = key.indexOf('#');
		if (dot < 0 && shp < 0) {
			this.tag = key;
		} else if (dot >= 0 && shp < 0) {
			this.styleClass = key.substring(dot + 1);
			if (dot > 0) {
				this.tag = key.substring(0, dot);
			}
		} else if (dot < 0 && shp >= 0) {
			this.styleId = key.substring(shp + 1);
			if (shp > 0) {
				this.tag = key.substring(0, shp);
			}
		} else {
			// その他のパターンは対応外とする
		}
		valueMap = new HashMap<String, String>();
	}

	public void putStyle(String key, String value) {
		if (StringUtils.isNotEmpty(key) &&
				StringUtils.isNotEmpty(value)) {
			valueMap.put(key.trim(), value.trim());
		}
	}

	public boolean matchAllParent(XhtmlNode node) {
		if (node == null) {
			return false;
		}
		boolean isMatch = false;
		while (!(isMatch = match(node.getParent()))) {
			node = node.getParent();
			if (node == null) {
				break;
			}
		}
		return isMatch;
	}

	public boolean match(XhtmlNode node) {
		if (node == null) {
			return false;
		}
		boolean match = false;
		boolean unmatch = false;
		if (StringUtils.isNotEmpty(tag)) {
			if (tag.equals("*") || tag.equals(node.getTag())) {
				match = true;
			} else {
				unmatch = true;
			}
		}
		if (StringUtils.isNotEmpty(styleId)) {
			if (styleId.equals(node.getStyleId())) {
				match = true;
			} else {
				unmatch = true;
			}
		}
		if (StringUtils.isNotEmpty(styleClass)) {
			if (node.getStyleClasses() != null
					&& node.getStyleClasses().contains(styleClass)) {
				match = true;
			} else {
				unmatch = true;
			}
		}
		if (selectorType != null) {
			if (selectorType == SelectorType.FIRST_CHILD) {
				if (node.getIndex() == 1) {
					match = true;
				} else {
					unmatch = true;
				}
			}
		}
		if (match && !unmatch &&
				(selectorType == SelectorType.CHILD ||
						selectorType == SelectorType.FIRST_CHILD)) {
			return parent == null || parent.match(node.getParent());
		} else if (match && !unmatch) {
			return parent == null || parent.matchAllParent(node);
		} else {
			return false;
		}
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

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public CSSCond getParent() {
		return parent;
	}

	public void setParent(CSSCond parent) {
		this.parent = parent;
	}

	public SelectorType getSelectorType() {
		return selectorType;
	}

	public void setSelectorType(SelectorType selectorType) {
		this.selectorType = selectorType;
	}

	public Map<String, String> getValueMap() {
		return valueMap;
	}

	public void setValueMap(Map<String, String> valueMap) {
		this.valueMap = valueMap;
	}

}

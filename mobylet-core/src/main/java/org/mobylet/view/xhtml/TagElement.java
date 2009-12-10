package org.mobylet.view.xhtml;

import java.util.LinkedHashMap;
import java.util.Map;

public class TagElement {

	protected String name;

	protected LinkedHashMap<String, String> attMap;

	public TagElement(String name) {
		this.name = name;
		this.attMap = new LinkedHashMap<String, String>();
	}

	public void putAttribute(String key, String val) {
		attMap.put(key, val);
	}

	public Map<String, String> getAttMap() {
		return attMap;
	}

	public String getName() {
		return name;
	}

}

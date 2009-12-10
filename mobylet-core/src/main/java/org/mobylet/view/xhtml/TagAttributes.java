package org.mobylet.view.xhtml;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;


public class TagAttributes {

	protected Map<String, TagAttribute> attMap;

	protected List<TagAttribute> attEntryList;

	public TagAttributes(Map<String, String> attMap) {
		this.attMap = new LinkedHashMap<String, TagAttribute>();
		attEntryList = new ArrayList<TagAttribute>();
		Set<Entry<String, String>> entrySet = attMap.entrySet();
		if (entrySet != null && entrySet.size() != 0) {
			for (Entry<String, String> entry : entrySet) {
				TagAttribute tagAtt =
					new TagAttribute(entry.getKey(), entry.getValue());
				this.attMap.put(entry.getKey(), tagAtt);
				attEntryList.add(tagAtt);
			}
		}
	}

	public int size() {
		return attEntryList.size();
	}

	public TagAttribute getAttribute(int index) {
		return attEntryList.get(index);
	}

	public TagAttribute getAttribute(String key) {
		return attMap.get(key);
	}

	public String getName(int index) {
		return getAttribute(index).getName();
	}

	public String getValue(String key) {
		TagAttribute att = getAttribute(key);
		if (att != null) {
			return att.getValue();
		} else {
			return null;
		}
	}
}

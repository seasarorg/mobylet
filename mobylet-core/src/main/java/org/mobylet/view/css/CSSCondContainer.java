package org.mobylet.view.css;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.mobylet.core.util.StringUtils;

public class CSSCondContainer {

	protected Set<CSSCond> allCond;
	
	protected LinkedHashMap<String, Set<CSSCond>> tagMap;

	protected LinkedHashMap<String, Set<CSSCond>> styleIdMap;

	protected LinkedHashMap<String, Set<CSSCond>> styleClassMap;

	public CSSCondContainer() {
		init();
	}

	public String getStyle(DOMNode node) {
		Set<CSSCond> condSet = new LinkedHashSet<CSSCond>();
		addCondSet(condSet, node);
		if (condSet.size() == 0) {
			return null;
		}
		Map<String, String> styleMap = new HashMap<String, String>();
		for (CSSCond cond : condSet) {
			if (cond.match(node)) {
				styleMap.putAll(cond.getValueMap());
			}
		}
		if (styleMap.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for(Map.Entry<String, String> entry : styleMap.entrySet()) {
				sb.append(entry.getKey() + ":");
				sb.append(entry.getValue() + ";");
			}
			return sb.toString();
		}
		return null;
	}
	
	public void putAll(CSSCondContainer container) {
		if (container == null ||
				container.allCond.size() == 0) {
			return;
		}
		for (CSSCond cond : container.allCond) {
			put(cond);
		}
	}
	
	public void put(CSSCond cond) {
		if (cond == null) {
			return;
		}
		if (StringUtils.isNotEmpty(cond.getTag())) {
			Set<CSSCond> set = tagMap.get(cond.getTag());
			if (set == null) {
				set = new LinkedHashSet<CSSCond>();
			}
			set.add(cond);
		}
		if (StringUtils.isNotEmpty(cond.getStyleId())) {
			Set<CSSCond> set = styleIdMap.get(cond.getStyleId());
			if (set == null) {
				set = new LinkedHashSet<CSSCond>();
			}
			set.add(cond);
		}
		if (StringUtils.isNotEmpty(cond.getStyleClass())) {
			Set<CSSCond> set = styleClassMap.get(cond.getStyleClass());
			if (set == null) {
				set = new LinkedHashSet<CSSCond>();
			}
			set.add(cond);
		}
		allCond.add(cond);
	}

	protected void init() {
		allCond = new LinkedHashSet<CSSCond>();
		tagMap = new LinkedHashMap<String, Set<CSSCond>>();
		styleIdMap = new LinkedHashMap<String, Set<CSSCond>>();
		styleClassMap = new LinkedHashMap<String, Set<CSSCond>>();
	}
	
	protected void addCondSet(Set<CSSCond> condSet, DOMNode node) {
		Set<CSSCond> set = tagMap.get(node.getTag());
		if (set != null) {
			condSet.addAll(set);
		}
		set = styleIdMap.get(node.getStyleId());
		if (set != null) {
			condSet.addAll(set);
		}
		if (node.getStyleClasses() != null) {
			for (String styleClass : node.getStyleClasses()) {
				set = styleClassMap.get(styleClass);
				if (set != null) {
					condSet.addAll(set);
				}
			}
		}
	}
}

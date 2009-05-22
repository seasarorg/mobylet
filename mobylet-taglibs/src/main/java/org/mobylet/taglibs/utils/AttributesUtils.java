package org.mobylet.taglibs.utils;

import java.io.CharArrayWriter;
import java.util.Set;

import org.mobylet.core.util.StringUtils;
import org.mobylet.taglibs.Attribute;

public class AttributesUtils {

	public static void addAttribute(
			Set<Attribute> dynAttributes, String name, Object value) {
		if (StringUtils.isNotEmpty(name)) {
			String strValue = null;
			if (value == null) {
				strValue = "";
			} else {
				strValue = value.toString();
			}
			dynAttributes.add(new Attribute(name, strValue));
		}
	}

	public static StringBuilder getAttributesStringBuilder(
			Set<Attribute> dynAttributes) {
		StringBuilder sb = new StringBuilder();
		for (Attribute attribute : dynAttributes) {
			sb.append(" " + attribute.getKey() + "=\"");
			sb.append(getInnerDoubleQuoteString(attribute.getValue()) + "\"");
		}
		return sb;
	}

	public static String getInnerDoubleQuoteString(String str) {
		if (StringUtils.isNotEmpty(str)) {
			if (str.contains("\\") || str.contains("\"")) {
				CharArrayWriter caw = new CharArrayWriter(128);
				char[] chars = str.toCharArray();
				for (int i=0; i<chars.length; i++) {
					if (chars[i] == '\\') {
						caw.append(chars[i]);
					} else if (chars[i] == '"') {
						caw.append('\\');
					}
					caw.append(chars[i]);
				}
			} else {
				return str;
			}
		}
		return str;

	}
}

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
			if (value != null) {
				strValue = value.toString();
			}
			dynAttributes.add(new Attribute(name, strValue));
		}
	}

	public static StringBuilder getAttributesStringBuilder(
			Set<Attribute> dynAttributes) {
		StringBuilder sb = new StringBuilder();
		for (Attribute attribute : dynAttributes) {
			Object val = attribute.getValue();
			if (val == null) {
				sb.append(" " + attribute.getKey());
			} else {
				sb.append(" " + attribute.getKey() + "=\"");
				sb.append(getInnerDoubleQuoteString(
						attribute.getValue().toString()) + "\"");
			}
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

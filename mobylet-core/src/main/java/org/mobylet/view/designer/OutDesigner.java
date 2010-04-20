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
package org.mobylet.view.designer;

import org.mobylet.core.util.StringUtils;

public class OutDesigner extends SingletonDesigner {

	public static final String AMP = "&amp;";

	public static final String LT = "&lt;";

	public static final String GT = "&gt;";

	public static final String SQUOT = "&#39;";

	public static final String DQUOT = "&quot;";

	public static final String BR = "<br />";


	public String get(String value, boolean escapeXml, boolean breakToBr) {
		if (StringUtils.isEmpty(value)) {
			return value;
		}
		String retStr = StringUtils.toHan(value);
		if (escapeXml) {
			retStr = doEscapeXml(retStr);
		}
		if (breakToBr) {
			retStr = doBreakToBr(retStr);
		}
		return retStr;
	}


	protected String doEscapeXml(String value) {
		if (StringUtils.isEmpty(value)) {
			return value;
		}
		char[] charArray = value.toCharArray();
		StringBuilder buf = new StringBuilder();
		for (char c : charArray) {
			switch (c) {
			case '&':
				buf.append(AMP);
				break;
			case '<':
				buf.append(LT);
				break;
			case '>':
				buf.append(GT);
				break;
			case '\'':
				buf.append(SQUOT);
				break;
			case '"':
				buf.append(DQUOT);
				break;
			default:
				buf.append(c);
				break;
			}
		}
		return buf.toString();
	}

	protected String doBreakToBr(String value) {
		if (StringUtils.isEmpty(value)) {
			return value;
		}
		char[] charArray = value.toCharArray();
		StringBuilder buf = new StringBuilder();
		for (char c : charArray) {
			switch (c) {
			case '\r':
				break;
			case '\n':
				buf.append(BR);
				break;
			default:
				buf.append(c);
				break;
			}
		}
		return buf.toString();
	}

}

package org.mobylet.view.designer;

import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;

public class OutDesigner {

	public static final String AMP = "&amp;";

	public static final String LT = "&lt;";

	public static final String GT = "&gt;";

	public static final String SQUOT = "&#39;";

	public static final String DQUOT = "&quot;";



	public static OutDesigner getDesigner() {
		OutDesigner designer = null;
		try {
			designer = SingletonUtils.get(OutDesigner.class);
		} catch (Exception e) {
			//NOP
		}
		if (designer == null) {
			SingletonUtils.put(new OutDesigner());
			return getDesigner();
		}
		return designer;
	}

	public String get(String value, boolean escapeXml, boolean breakToBr) {
		return null;
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
				buf.append("&amp;");
			case '<':
				buf.append("&lt;");
			case '>':
				buf.append("&gt;");
			case '\'':
				buf.append("&#39;");
			case '"':
				buf.append("&quot;");
			default:
				buf.append(c);
				break;
			}
		}
		return buf.toString();
	}


}

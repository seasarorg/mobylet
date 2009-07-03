package org.mobylet.taglibs.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.util.StringUtils;
import org.mobylet.taglibs.utils.JspWriterUtils;

public class HanTag extends SimpleTagSupport {

	public static final String BR = "<br />";


	protected String value;

	protected boolean escapeXml = true;

	protected boolean breakToBr = true;


	@Override
	public void doTag() throws JspException, IOException {
		if (StringUtils.isEmpty(value)) {
			return;
		}
		String outString = StringUtils.toHan(value);
		if (escapeXml) {
			outString = doEscapeXml(outString);
		}
		if (breakToBr) {
			outString = doBreakToBr(outString);
		}
		JspWriterUtils.write(
				getJspContext().getOut(), outString);
	}

	public String doEscapeXml(String src) {
		if (StringUtils.isEmpty(src)) {
			return null;
		}
		String outString = src.replaceAll("&", "&amp;");
		outString = outString.replaceAll("<", "&lt;");
		outString = outString.replaceAll(">", "&gt;");
		outString = outString.replaceAll("'", "&#39;");
		outString = outString.replaceAll("\"", "&quot;");
		return outString;
	}

	public String doBreakToBr(String src) {
		if (StringUtils.isEmpty(src)) {
			return null;
		}
		StringBuilder builder = new StringBuilder(src.length());
		BufferedReader reader =
			new BufferedReader(new StringReader(src));
		boolean isAppendBr = false;
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				builder.append(line).append(BR);
				if (!isAppendBr) {
					isAppendBr = true;
				}
			}
		} catch (IOException e) {
			throw new MobyletRuntimeException(
					"文字列 [" + src + "] 解析出来ません", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					//NOP
				}
			}
		}
		if (isAppendBr) {
			builder.delete(builder.length()-BR.length(), builder.length());
		}
		return builder.toString();
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isEscapeXml() {
		return escapeXml;
	}

	public void setEscapeXml(boolean escapeXml) {
		this.escapeXml = escapeXml;
	}

	public boolean isBreakToBr() {
		return breakToBr;
	}

	public void setBreakToBr(boolean breakToBr) {
		this.breakToBr = breakToBr;
	}

}

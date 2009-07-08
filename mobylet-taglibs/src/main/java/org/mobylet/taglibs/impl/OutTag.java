package org.mobylet.taglibs.impl;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.mobylet.core.util.StringUtils;
import org.mobylet.taglibs.utils.JspWriterUtils;
import org.mobylet.view.designer.OutDesigner;

public class OutTag extends SimpleTagSupport {

	protected String value;

	protected boolean escapeXml = true;

	protected boolean breakToBr = true;


	@Override
	public void doTag() throws JspException, IOException {
		if (StringUtils.isEmpty(value)) {
			return;
		}
		OutDesigner designer = OutDesigner.getDesigner();
		String outString = designer.get(value, escapeXml, breakToBr);
		JspWriterUtils.write(
				getJspContext().getOut(), outString);
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

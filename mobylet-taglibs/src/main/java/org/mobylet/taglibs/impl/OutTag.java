package org.mobylet.taglibs.impl;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.mobylet.core.util.StringUtils;
import org.mobylet.taglibs.utils.JspWriterUtils;
import org.mobylet.view.designer.OutDesigner;
import org.mobylet.view.designer.SingletonDesigner;

public class OutTag extends SimpleTagSupport {

	protected String value;

	protected boolean escapeXml = true;

	protected boolean breakToBr = true;


	@Override
	public void doTag() throws JspException, IOException {
		try {
			if (StringUtils.isEmpty(value)) {
				return;
			}
			OutDesigner designer =
				SingletonDesigner.getDesigner(OutDesigner.class);
			String outString = designer.get(value, escapeXml, breakToBr);
			JspWriterUtils.write(
					getJspContext().getOut(), outString);
		} catch (Exception e) {
			throw new JspException(e);
		}
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

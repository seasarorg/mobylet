package org.mobylet.taglibs.impl;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.mobylet.taglibs.utils.JspWriterUtils;
import org.mobylet.view.designer.SingletonDesigner;
import org.mobylet.view.designer.XmlHeaderDesigner;

public class XmlHeaderTag extends SimpleTagSupport {

	protected String iversion;

	@Override
	public void doTag() throws JspException, IOException {
		try {
			XmlHeaderDesigner designer =
				SingletonDesigner.getDesigner(XmlHeaderDesigner.class);
			JspWriterUtils.write(
					getJspContext().getOut(),
					designer.get(iversion)
					);
		} catch (Exception e) {
			throw new JspException(e);
		}
	}

	public String getIversion() {
		return iversion;
	}

	public void setIversion(String iversion) {
		this.iversion = iversion;
	}

}

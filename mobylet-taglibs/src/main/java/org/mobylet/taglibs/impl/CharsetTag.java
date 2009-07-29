package org.mobylet.taglibs.impl;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.taglibs.utils.JspWriterUtils;

public class CharsetTag extends SimpleTagSupport {


	protected String type = "content";

	@Override
	public void doTag() throws JspException, IOException {
		try {
			Mobylet m = MobyletFactory.getInstance();
			String charsetName = m.getDialect().getContentCharsetName();
			if ("native".equalsIgnoreCase(type)) {
				charsetName = m.getDialect().getNativeCharsetName();
			} else if ("real".equalsIgnoreCase(type)) {
				charsetName = m.getDialect().getCharsetName();
			}
			JspWriterUtils.write(
					getJspContext().getOut(),
					charsetName
					);
		} catch (Exception e) {
			throw new JspException(e);
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}

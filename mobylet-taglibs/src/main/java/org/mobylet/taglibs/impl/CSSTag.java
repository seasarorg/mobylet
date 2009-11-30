package org.mobylet.taglibs.impl;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.mobylet.core.util.StringUtils;
import org.mobylet.taglibs.MobyletDynamicSimpleTagSupport;
import org.mobylet.taglibs.utils.JspWriterUtils;
import org.mobylet.view.designer.CSSDesigner;
import org.mobylet.view.designer.SingletonDesigner;

public class CSSTag extends MobyletDynamicSimpleTagSupport {

	protected String src;

	protected String charset;

	@Override
	public void doTag() throws JspException, IOException {
		if (StringUtils.isEmpty(charset)) {
			charset = "UTF-8";
		}
		try {
			CSSDesigner designer =
				SingletonDesigner.getDesigner(CSSDesigner.class);
			JspWriterUtils.write(
					getJspContext().getOut(),
					designer.includeCSS(src, charset)
					);
		} catch (Exception e) {
			throw new JspException(e);
		}
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

}

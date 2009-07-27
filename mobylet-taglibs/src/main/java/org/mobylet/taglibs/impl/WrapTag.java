package org.mobylet.taglibs.impl;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.mobylet.taglibs.utils.JspWriterUtils;
import org.mobylet.view.designer.IStyleDesigner;
import org.mobylet.view.designer.SingletonDesigner;

public class WrapTag extends BodyTagSupport {

	private static final long serialVersionUID = -4315975001784286337L;

	protected Integer istyle;


	@Override
	public int doEndTag() throws JspException {
		try {
			IStyleDesigner designer =
				SingletonDesigner.getDesigner(IStyleDesigner.class);
			if (istyle != null) {
				JspWriterUtils.write(
						pageContext.getOut(),
						designer.addIStyle(
								getBodyContent().getString(),
								istyle));
			} else {
				JspWriterUtils.write(
						pageContext.getOut(),
						getBodyContent().getString());
			}
			return EVAL_PAGE;
		} catch (Exception e) {
			throw new JspException(e);
		}
	}

	public Integer getIstyle() {
		return istyle;
	}

	public void setIstyle(Integer istyle) {
		this.istyle = istyle;
	}


}

package org.mobylet.taglibs.impl;

import javax.servlet.jsp.JspException;

import org.mobylet.taglibs.MobyletDynamicBodyTagSupport;

public class ATag extends MobyletDynamicBodyTagSupport {

	private static final long serialVersionUID = 6951351713553308618L;

	public static final String TAG = "a";

	protected String href;


	@Override
	public int doStartTag() throws JspException {
		return EVAL_BODY_BUFFERED;
	}

}

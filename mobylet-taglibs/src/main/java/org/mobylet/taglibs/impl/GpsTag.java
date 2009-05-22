package org.mobylet.taglibs.impl;

import javax.servlet.jsp.JspException;

import org.mobylet.taglibs.MobyletDynamicBodyTagSupport;

public class GpsTag extends MobyletDynamicBodyTagSupport {

	private static final long serialVersionUID = -4285604125713972051L;

	public static final String TAG = "a";

	protected String kickBackUrl;

	protected String iAreaKickBackUrl;


	@Override
	public int doStartTag() throws JspException {
		return EVAL_BODY_BUFFERED;
	}

}

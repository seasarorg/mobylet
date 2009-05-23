package org.mobylet.taglibs.impl;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.mobylet.taglibs.MobyletDynamicSimpleTagSupport;

public class HiddenUidTag extends MobyletDynamicSimpleTagSupport {

	public static final String TAG = "input type=\"hidden\"";

	@Override
	public void doTag() throws JspException, IOException {
	}

}

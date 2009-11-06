package org.mobylet.taglibs.impl;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.mobylet.taglibs.MobyletDynamicSimpleTagSupport;

public class StyleTag extends MobyletDynamicSimpleTagSupport {

	protected String src;
	
	@Override
	public void doTag() throws JspException, IOException {
		// TODO Auto-generated method stub
		super.doTag();
	}
	
	public void setSrc(String src) {
		this.src = src;
	}
	
}

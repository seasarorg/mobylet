package org.mobylet.taglibs;

import org.mobylet.core.MobyletRuntimeException;

public class MobyletRenderingException extends MobyletRuntimeException {

	private static final long serialVersionUID = -5140909353831120913L;

	public MobyletRenderingException(String msg, Exception e) {
		super(msg, e);
	}

}

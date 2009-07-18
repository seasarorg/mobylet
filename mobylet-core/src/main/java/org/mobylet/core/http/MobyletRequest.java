package org.mobylet.core.http;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class MobyletRequest extends HttpServletRequestWrapper {

	protected HttpServletRequest request;


	public MobyletRequest(HttpServletRequest request) {
		super(request);
	}

}

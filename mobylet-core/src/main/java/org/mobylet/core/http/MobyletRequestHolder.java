package org.mobylet.core.http;

import javax.servlet.http.HttpServletRequest;

public class MobyletRequestHolder {

	protected ThreadLocal<HttpServletRequest> requestHolder;


	public MobyletRequestHolder() {
		requestHolder = new ThreadLocal<HttpServletRequest>();
	}

	public HttpServletRequest get() {
		return requestHolder.get();
	}

	public void set(HttpServletRequest request) {
		requestHolder.set(request);
	}

	public void remove() {
		requestHolder.remove();
	}

}

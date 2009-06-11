package org.seasar.mobylet.holder;

import javax.servlet.http.HttpServletRequest;

import org.mobylet.core.holder.RequestHolder;
import org.seasar.framework.container.SingletonS2Container;

public class S2MobyletRequestHolder implements RequestHolder {

	protected ThreadLocal<HttpServletRequest> nativeRequest;

	public S2MobyletRequestHolder() {
		nativeRequest = new ThreadLocal<HttpServletRequest>();
	}

	@Override
	public HttpServletRequest get() {
		try {
			HttpServletRequest request =
				SingletonS2Container.getComponent(HttpServletRequest.class);
			if (request == null) {
				request = nativeRequest.get();
			}
			return request;
		} catch (Throwable t) {
			return nativeRequest.get();
		}
	}

	@Override
	public void remove() {
		nativeRequest.remove();
	}

	@Override
	public void set(HttpServletRequest request) {
		if (nativeRequest.get() == null) {
			nativeRequest.set(request);
		}
	}

}

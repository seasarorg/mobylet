package org.mobylet.core.util;

import javax.servlet.http.HttpServletRequest;

import org.mobylet.core.http.MobyletContext;
import org.mobylet.core.http.MobyletRequestHolder;

public class RequestUtils {

	public static HttpServletRequest get() {
		return SingletonUtils.get(MobyletRequestHolder.class).get();
	}

	public static void set(HttpServletRequest request) {
		SingletonUtils.get(MobyletRequestHolder.class).set(request);
	}

	public static void remove() {
		SingletonUtils.get(MobyletRequestHolder.class).remove();
	}

	public static MobyletContext getMobyletContext() {
		HttpServletRequest request = get();
		if (request.getAttribute(MobyletContext.CONTEXT_KEY) == null) {
			request.setAttribute(
					MobyletContext.CONTEXT_KEY,
					new MobyletContext());
			return getMobyletContext();
		} else {
			return (MobyletContext)request.getAttribute(MobyletContext.CONTEXT_KEY);
		}
	}

	public static String getUserAgent() {
		return get().getHeader("User-Agent");
	}
}

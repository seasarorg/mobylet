package org.mobylet.core;

import javax.servlet.http.HttpServletRequest;

import org.mobylet.core.util.RequestUtils;

public class MobyletFactory {

	private static final String KEY = Mobylet.class.getName();


	public static Mobylet getInstance() {
		HttpServletRequest request = RequestUtils.get();
		if (request != null) {
			Object obj = null;
			if ((obj = request.getAttribute(KEY)) != null &&
					obj instanceof Mobylet) {
				return (Mobylet)obj;
			} else {
				request.setAttribute(KEY, new Mobylet());
				return getInstance();
			}
		}
		return null;
	}
}

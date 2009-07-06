package org.mobylet.core.util;

import javax.servlet.http.HttpServletRequest;

public class UrlUtils {

	public static String getCurrentUrl() {
		HttpServletRequest request = RequestUtils.get();
		if (request == null) {
			return null;
		}
		String url = request.getRequestURL().toString();
		if (request.isSecure() &&
				url.startsWith("http:")) {
			url = url.replace("http:", "https;");
		}
		return url;
	}

}

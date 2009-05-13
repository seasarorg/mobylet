package org.mobylet.core.util;

import javax.servlet.http.HttpServletRequest;

public class MobyletRequestUtils {

	public static String getUserAgent(HttpServletRequest req) {
		if (req == null) {
			return null;
		}
		return req.getHeader("User-Agent");
	}
}

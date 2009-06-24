package org.mobylet.taglibs.config;

import org.mobylet.core.util.StringUtils;

public class ImageTagConfig extends AbstractTagConfig {

	public static final String KEY_SERVLET_PATH = "servlet.path";

	public boolean useServlet() {
		return StringUtils.isNotEmpty(getServletPath());
	}

	public String getServletPath() {
		return getConfig().getProperty(KEY_SERVLET_PATH);
	}
}

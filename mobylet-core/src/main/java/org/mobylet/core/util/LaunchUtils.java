package org.mobylet.core.util;

import java.util.Enumeration;

import javax.servlet.FilterConfig;

import org.mobylet.core.launcher.LaunchConfig;

public class LaunchUtils {

	@SuppressWarnings("unchecked")
	public static LaunchConfig getLaunchConfig(FilterConfig filterConfig) {
		LaunchConfig config = new LaunchConfig();
		if (filterConfig == null) {
			return config;
		}
		Enumeration keys = filterConfig.getInitParameterNames();
		if (keys != null) {
			while (keys.hasMoreElements()) {
				Object key = keys.nextElement();
				config.addParameter((String)key, filterConfig.getInitParameter((String)key));
			}
		}
		return config;
	}

}

package org.mobylet.core.launcher;

import java.util.Enumeration;
import java.util.Properties;

import org.mobylet.core.config.MobyletInjectionConfig;

public class LaunchConfig extends MobyletInjectionConfig {

	public void addParameter(String key, String value) {
		if (injectionConfig == null) {
			injectionConfig = new Properties();
		}
		injectionConfig.setProperty(key, value);
	}

	public String getInitParameter(String s) {
		return getConfig().getProperty(s);
	}

	@SuppressWarnings("unchecked")
	public Enumeration getInitParameterNames() {
		return getConfig().keys();
	}

	@Override
	public String getConfigName() {
		return "mobylet.launch.properties";
	}
}

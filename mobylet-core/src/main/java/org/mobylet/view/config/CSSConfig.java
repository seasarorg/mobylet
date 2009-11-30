package org.mobylet.view.config;

import java.util.Properties;

import org.mobylet.core.config.MobyletInjectionConfig;
import org.mobylet.core.util.StringUtils;

public class CSSConfig extends MobyletInjectionConfig {

	public static final String KEY_LOCAL_BASE_PATH = "css.local.base.path";

	protected String localBasePath;


	public CSSConfig() {
		Properties config = getConfig();
		if (StringUtils.isNotEmpty(
				config.getProperty(KEY_LOCAL_BASE_PATH))) {
			localBasePath = config.getProperty(KEY_LOCAL_BASE_PATH);
			if (localBasePath.endsWith("/") ||
					localBasePath.endsWith("\\")) {
				localBasePath =
					localBasePath.substring(0, localBasePath.length()-1);
			}
		}
	}


	public String getLocalBasePath() {
		return localBasePath;
	}

	public void setLocalBasePath(String localBasePath) {
		this.localBasePath = localBasePath;
	}

}

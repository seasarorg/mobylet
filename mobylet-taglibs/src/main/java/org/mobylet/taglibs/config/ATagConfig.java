package org.mobylet.taglibs.config;

import java.util.Properties;

import org.mobylet.core.util.StringUtils;

public class ATagConfig extends AbstractTagConfig {


	public static final String KEY_ISUIDQUERYREQUIRED =
		"atag.isUidQueryRequired";

	public static final String KEY_ISGUIDQUERYREQUIRED =
		"atag.isGuidQueryRequired";

	public static final String KEY_ISSESSIONCOOKIEPRIORITY =
		"atag.isSessionCookiePriority";

	public static final String KEY_ISUIDORGUIDQUERYREQUIREDINSECURE =
		"atag.isUidOrGuidQueryRequiredInSecure";


	protected boolean isUidQueryRequired = false;

	protected boolean isGuidQueryRequired = true;

	protected boolean isSessionCookiePriority = true;

	protected boolean isUidOrGuidQueryRequiredInSecure = true;


	public ATagConfig() {
		Properties config = getConfig();
		if (StringUtils.isNotEmpty(
				config.getProperty(KEY_ISUIDQUERYREQUIRED))) {
			isUidQueryRequired =
				"TRUE".equalsIgnoreCase(config.getProperty(
						KEY_ISUIDQUERYREQUIRED));
		}
		if (StringUtils.isNotEmpty(
				config.getProperty(KEY_ISGUIDQUERYREQUIRED))) {
			isGuidQueryRequired =
				"TRUE".equalsIgnoreCase(config.getProperty(
						KEY_ISGUIDQUERYREQUIRED));
		}
		if (StringUtils.isNotEmpty(
				config.getProperty(KEY_ISSESSIONCOOKIEPRIORITY))) {
			isSessionCookiePriority =
				"TRUE".equalsIgnoreCase(config.getProperty(
						KEY_ISSESSIONCOOKIEPRIORITY));
		}
		if (StringUtils.isNotEmpty(
				config.getProperty(KEY_ISUIDORGUIDQUERYREQUIREDINSECURE))) {
			isUidOrGuidQueryRequiredInSecure =
				"TRUE".equalsIgnoreCase(config.getProperty(
						KEY_ISUIDORGUIDQUERYREQUIREDINSECURE));
		}
	}


	public boolean isUidQueryRequired() {
		return isUidQueryRequired;
	}

	public boolean isGuidQueryRequired() {
		return isGuidQueryRequired;
	}

	public boolean isSessionCookiePriority() {
		return isSessionCookiePriority;
	}

	public boolean isUidOrGuidQueryRequiredInSecure() {
		return isUidOrGuidQueryRequiredInSecure;
	}

}

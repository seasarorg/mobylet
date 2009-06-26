package org.mobylet.taglibs.config;

import java.util.Properties;

import org.mobylet.core.util.StringUtils;

public class TransitionTagConfig extends AbstractTagConfig {


	public static final String KEY_ISUIDQUERYREQUIRED =
		"transition.isUidQueryRequired";

	public static final String KEY_ISGUIDQUERYREQUIRED =
		"transition.isGuidQueryRequired";

	public static final String KEY_ISSESSIONCOOKIEPRIORITY =
		"transition.isSessionCookiePriority";

	public static final String KEY_ISUIDORGUIDQUERYREQUIREDINSECURE =
		"transition.isUidOrGuidQueryRequiredInSecure";


	protected boolean isUidQueryRequired = false;

	protected boolean isGuidQueryRequired = true;

	protected boolean isSessionCookiePriority = true;

	protected boolean isUidOrGuidQueryRequiredInSecure = true;


	public TransitionTagConfig() {
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

package org.mobylet.view.config;

import java.util.Properties;

import org.mobylet.core.config.MobyletInjectionConfig;
import org.mobylet.core.util.StringUtils;

public class TransitionConfig extends MobyletInjectionConfig {

	public static final String KEY_ISUIDQUERYREQUIRED =
		"transition.isUidQueryRequired";

	public static final String KEY_ISGUIDQUERYREQUIRED =
		"transition.isGuidQueryRequired";

	public static final String KEY_ISSESSIONCOOKIEPRIORITY =
		"transition.isSessionCookiePriority";

	public static final String KEY_ISUIDORGUIDQUERYREQUIREDINSECURE =
		"transition.isUidOrGuidQueryRequiredInSecure";

	public static final String KEY_ISADDITIONALCONTEXT =
		"transition.isAdditionalContext";


	protected boolean isUidQueryRequired = false;

	protected boolean isGuidQueryRequired = true;

	protected boolean isSessionCookiePriority = true;

	protected boolean isUidOrGuidQueryRequiredInSecure = true;

	protected boolean isAdditionalContext = false;


	public TransitionConfig() {
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
		if (StringUtils.isNotEmpty(
				config.getProperty(KEY_ISADDITIONALCONTEXT))) {
			isAdditionalContext =
				"TRUE".equalsIgnoreCase(config.getProperty(
						KEY_ISADDITIONALCONTEXT));
		}
	}

	public TransitionConfig(TransitionConfig config) {
		setAdditionalContext(config.isAdditionalContext());
		setGuidQueryRequired(config.isGuidQueryRequired());
		setSessionCookiePriority(config.isSessionCookiePriority());
		setUidOrGuidQueryRequiredInSecure(config.isUidOrGuidQueryRequiredInSecure());
		setUidQueryRequired(config.isUidQueryRequired());
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

	public boolean isAdditionalContext() {
		return isAdditionalContext;
	}


	public void setUidQueryRequired(boolean isUidQueryRequired) {
		this.isUidQueryRequired = isUidQueryRequired;
	}

	public void setGuidQueryRequired(boolean isGuidQueryRequired) {
		this.isGuidQueryRequired = isGuidQueryRequired;
	}

	public void setSessionCookiePriority(boolean isSessionCookiePriority) {
		this.isSessionCookiePriority = isSessionCookiePriority;
	}

	public void setUidOrGuidQueryRequiredInSecure(
			boolean isUidOrGuidQueryRequiredInSecure) {
		this.isUidOrGuidQueryRequiredInSecure = isUidOrGuidQueryRequiredInSecure;
	}

	public void setAdditionalContext(boolean isAdditionalContext) {
		this.isAdditionalContext = isAdditionalContext;
	}

}

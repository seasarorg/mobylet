package org.mobylet.taglibs.impl;

import org.mobylet.taglibs.MobyletDynamicBodyTagSupport;
import org.mobylet.view.config.TransitionConfig;
import org.mobylet.view.designer.TransitionDesigner;

public abstract class TransitionTag extends MobyletDynamicBodyTagSupport {

	private static final long serialVersionUID = 7210436838271995347L;

	protected Boolean isUidQueryRequired;

	protected Boolean isGuidQueryRequired;

	protected Boolean isSessionCookiePriority;

	protected Boolean isUidOrGuidQueryRequiredInSecure;

	protected Boolean isAdditionalContext;


	public final TransitionConfig getConfig() {
		if (isUidQueryRequired != null ||
				isGuidQueryRequired != null ||
				isSessionCookiePriority != null ||
				isUidOrGuidQueryRequiredInSecure != null ||
				isAdditionalContext != null) {
			TransitionConfig config = new TransitionConfig();
			if (isUidQueryRequired != null) {
				config.setUidQueryRequired(
						isUidQueryRequired);
			}
			if (isGuidQueryRequired != null) {
				config.setGuidQueryRequired(
						isGuidQueryRequired);
			}
			if (isSessionCookiePriority != null) {
				config.setSessionCookiePriority(
						isSessionCookiePriority);
			}
			if (isUidOrGuidQueryRequiredInSecure != null) {
				config.setUidOrGuidQueryRequiredInSecure(
						isUidOrGuidQueryRequiredInSecure);
			}
			if (isAdditionalContext != null) {
				config.setAdditionalContext(isAdditionalContext);
			}
			return config;
		}
		return TransitionDesigner.config;
	}

	public Boolean getIsUidQueryRequired() {
		return isUidQueryRequired;
	}

	public void setIsUidQueryRequired(Boolean isUidQueryRequired) {
		this.isUidQueryRequired = isUidQueryRequired;
	}

	public Boolean getIsGuidQueryRequired() {
		return isGuidQueryRequired;
	}

	public void setIsGuidQueryRequired(Boolean isGuidQueryRequired) {
		this.isGuidQueryRequired = isGuidQueryRequired;
	}

	public Boolean getIsSessionCookiePriority() {
		return isSessionCookiePriority;
	}

	public void setIsSessionCookiePriority(Boolean isSessionCookiePriority) {
		this.isSessionCookiePriority = isSessionCookiePriority;
	}

	public Boolean getIsUidOrGuidQueryRequiredInSecure() {
		return isUidOrGuidQueryRequiredInSecure;
	}

	public void setIsUidOrGuidQueryRequiredInSecure(
			Boolean isUidOrGuidQueryRequiredInSecure) {
		this.isUidOrGuidQueryRequiredInSecure = isUidOrGuidQueryRequiredInSecure;
	}

	public Boolean getIsAdditionalContext() {
		return isAdditionalContext;
	}

	public void setIsAdditionalContext(Boolean isAdditionalContext) {
		this.isAdditionalContext = isAdditionalContext;
	}



}

package org.mobylet.taglibs.config;

public class ATagConfig {

	protected boolean isUidQueryRequired = false;

	protected boolean isGuidQueryRequired = true;

	protected boolean isSessionCookiePriority = true;

	protected boolean isUidOrGuidQueryRequiredInSecure = true;


	public boolean isUidQueryRequired() {
		return isUidQueryRequired;
	}

	public void setUidQueryRequired(boolean isUidQueryRequired) {
		this.isUidQueryRequired = isUidQueryRequired;
	}

	public boolean isGuidQueryRequired() {
		return isGuidQueryRequired;
	}

	public void setGuidQueryRequired(boolean isGuidQueryRequired) {
		this.isGuidQueryRequired = isGuidQueryRequired;
	}

	public boolean isSessionCookiePriority() {
		return isSessionCookiePriority;
	}

	public void setSessionCookiePriority(boolean isSessionCookiePriority) {
		this.isSessionCookiePriority = isSessionCookiePriority;
	}

	public boolean isUidOrGuidQueryRequiredInSecure() {
		return isUidOrGuidQueryRequiredInSecure;
	}

	public void setUidOrGuidQueryRequiredInSecure(
			boolean isUidOrGuidQueryRequiredInSecure) {
		this.isUidOrGuidQueryRequiredInSecure = isUidOrGuidQueryRequiredInSecure;
	}

}

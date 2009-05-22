package org.mobylet.taglibs.config;

public class ATagConfig {

	protected boolean isUidQueryRequired = false;

	protected boolean isGuidQueryRequired = true;

	protected boolean isSessionQueryRequired = true;

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

	public boolean isSessionQueryRequired() {
		return isSessionQueryRequired;
	}

	public void setSessionQueryRequired(boolean isSessionQueryRequired) {
		this.isSessionQueryRequired = isSessionQueryRequired;
	}

	public boolean isUidOrGuidQueryRequiredInSecure() {
		return isUidOrGuidQueryRequiredInSecure;
	}

	public void setUidOrGuidQueryRequiredInSecure(
			boolean isUidOrGuidQueryRequiredInSecure) {
		this.isUidOrGuidQueryRequiredInSecure = isUidOrGuidQueryRequiredInSecure;
	}

}

package org.mobylet.core.device.impl;

import org.mobylet.core.device.Device;
import org.mobylet.core.device.DeviceDisplay;
import org.mobylet.core.device.DeviceProfile;

public class MobyletDevice implements Device {

	protected DeviceDisplay display;

	protected DeviceProfile profile;

	@Override
	public DeviceDisplay getDeviceDisplay() {
		return display;
	}

	@Override
	public DeviceProfile getDeviceProfile() {
		return profile;
	}

	public void setDeviceDisplay(DeviceDisplay dd) {
		display = dd;
	}

	public void setDeviceProfile(DeviceProfile dp) {
		profile = dp;
	}

}

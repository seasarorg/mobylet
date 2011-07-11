/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
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

	@Override
	public boolean hasGps() {
		return "Y".equals(profile.get("GPS対応"));
	}

	public void setDeviceDisplay(DeviceDisplay dd) {
		display = dd;
	}

	public void setDeviceProfile(DeviceProfile dp) {
		profile = dp;
	}

}

/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.mobylet.core;

import org.mobylet.core.detector.CarrierDetector;
import org.mobylet.core.device.Device;
import org.mobylet.core.device.DevicePool;
import org.mobylet.core.dialect.MobyletDialect;
import org.mobylet.core.gps.Gps;
import org.mobylet.core.http.MobyletContext;
import org.mobylet.core.selector.DialectSelector;
import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;

public class Mobylet {

	protected Carrier carrier;

	protected MobyletDialect dialect;

	protected Device device;

	protected boolean isPopedDevice = false;


	public Mobylet() {
		initialize();
	}

	public Carrier getCarrier() {
		return carrier;
	}

	public MobyletDialect getDialect() {
		return dialect;
	}

	public Device getDevice() {
		if (!isPopedDevice) {
			device = SingletonUtils.get(DevicePool.class).get();
			if (device != null) {
				RequestUtils.getMobyletContext().set(device);
			}
			isPopedDevice = true;
		}
		return device;
	}

	public boolean hasCookies() {
		return RequestUtils.get().getCookies() != null;
	}

	public String getUid() {
		return dialect.getUid();
	}

	public String getGuid() {
		return dialect.getGuid();
	}

	public String getUniqueId() {
		String id = getGuid();
		if (StringUtils.isEmpty(id)) {
			id = getUid();
		}
		return id;
	}

	public Gps getGps() {
		MobyletContext context = RequestUtils.getMobyletContext();
		Gps g = null;
		if ((g = context.get(Gps.class)) != null) {
			return g;
		} else {
			g = dialect.getGps();
			context.set(g);
			return getGps();
		}
	}

	protected void initialize() {
		carrier = SingletonUtils.get(CarrierDetector.class).getCarrier();
		dialect = SingletonUtils.get(DialectSelector.class).getDialect(carrier);
	}
}

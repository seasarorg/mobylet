package org.mobylet.core;

import org.mobylet.core.detector.CarrierDetector;
import org.mobylet.core.device.Device;
import org.mobylet.core.device.DevicePool;
import org.mobylet.core.dialect.MobyletDialect;
import org.mobylet.core.selector.DialectSelector;
import org.mobylet.core.util.SingletonUtils;

public class Mobylet {

	public Carrier getCarrier() {
		return SingletonUtils.get(CarrierDetector.class).getCarrier();
	}

	public Device getDevice() {
		return SingletonUtils.get(DevicePool.class).get();
	}

	public MobyletDialect getDialect() {
		return SingletonUtils.get(DialectSelector.class).getDialect(getCarrier());
	}
}

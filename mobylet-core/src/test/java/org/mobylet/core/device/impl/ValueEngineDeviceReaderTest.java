package org.mobylet.core.device.impl;

import java.util.Map;

import junit.framework.TestCase;

import org.mobylet.core.device.Device;
import org.mobylet.core.device.DeviceReader;

public class ValueEngineDeviceReaderTest extends TestCase {

	public void test_read() {
		//## ARRANGE ##
		DeviceReader reader = new ValueEngineDeviceReader();

		//## ACT ##
		Map<String, Device> map = reader.read();

		//## ASSERT ##
		assertEquals(map.size(), 780);
	}
}

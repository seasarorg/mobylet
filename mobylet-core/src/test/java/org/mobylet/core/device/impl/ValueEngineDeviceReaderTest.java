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
		assertEquals(map.size(), 1068);
	}
}

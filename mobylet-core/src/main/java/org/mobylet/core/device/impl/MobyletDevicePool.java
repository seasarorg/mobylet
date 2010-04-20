/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mobylet.core.MobyletFactory;
import org.mobylet.core.device.Device;
import org.mobylet.core.device.DevicePool;
import org.mobylet.core.device.DeviceReader;
import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;

public class MobyletDevicePool implements DevicePool {

	protected Map<String, Device> deviceMap;


	public MobyletDevicePool() {
		deviceMap = new HashMap<String, Device>(1024);
		DeviceReader reader = SingletonUtils.get(DeviceReader.class);
		deviceMap.putAll(reader.read());
	}

	@Override
	public Device get() {
		return deviceMap.get(getKeyString());
	}

	public String getKeyString() {
		Pattern regex =
			MobyletFactory.getInstance().getDialect().getDeviceMatchRegex();
		String userAgent = RequestUtils.getUserAgent();
		if (StringUtils.isNotEmpty(userAgent)) {
			Matcher matcher = regex.matcher(userAgent);
			if (matcher.find()) {
				return matcher.group();
			}
		}
		return null;
	}
}

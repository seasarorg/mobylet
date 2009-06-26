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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.config.MobyletConfig;
import org.mobylet.core.device.Device;
import org.mobylet.core.device.DeviceDisplay;
import org.mobylet.core.device.DeviceProfile;
import org.mobylet.core.device.DeviceReader;
import org.mobylet.core.util.CSVSplitUtils;
import org.mobylet.core.util.ResourceUtils;
import org.mobylet.core.util.SingletonUtils;

public class ValueEngineDeviceReader implements DeviceReader {

	public String pathUserAgent = "UserAgent.csv";

	public String pathProfile = "ProfileData.csv";

	public String pathDisplay = "DisplayInfo.csv";


	@Override
	public synchronized Map<String, Device> read() {
		Map<String, Device> dvMap = new HashMap<String, Device>(1024);
		Map<String, List<String>> uaMap = readUserAgent();
		Map<String, DeviceProfile> pfMap = readProfileData(uaMap);
		Map<String, DeviceDisplay> diMap = readDisplayInfo(uaMap);
		Set<Entry<String, DeviceProfile>> entrySet = pfMap.entrySet();
		for (Entry<String, DeviceProfile> entry : entrySet) {
			MobyletDevice d = new MobyletDevice();
			d.setDeviceProfile(entry.getValue());
			d.setDeviceDisplay(diMap.get(entry.getKey()));
			dvMap.put(entry.getKey(), d);
		}
		return dvMap;
	}

	protected Map<String, List<String>> readUserAgent() {
		MobyletConfig config = SingletonUtils.get(MobyletConfig.class);
		BufferedReader reader = null;
		Map<String, List<String>> uaMap = new HashMap<String, List<String>>();
		String[] header = null;
		try {
			reader = new BufferedReader(
					new InputStreamReader(
							ResourceUtils.getResourceFileOrInputStream(
									config.getDeviceDir() + pathUserAgent),
							"windows-31j"
							)
					);
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (header == null) {
					header = CSVSplitUtils.splitLineToArray(line);
					continue;
				}
				String[] val = CSVSplitUtils.splitLineToArray(line);
				String key = val[0]+"/"+val[1];
				List<String> uaSet = uaMap.get(key);
				if (uaSet == null) {
					uaSet = new ArrayList<String>();
					uaSet.add(val[3]);
					uaMap.put(key, uaSet);
				} else {
					uaSet.add(val[3]);
				}
			}
		} catch (IOException e) {
			throw new MobyletRuntimeException(
					"IO例外が発生しました", e);
		}	finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					//NOP
				}
			}
		}
		return uaMap;
	}

	protected Map<String, DeviceProfile> readProfileData(Map<String, List<String>> uaMap) {
		MobyletConfig config = SingletonUtils.get(MobyletConfig.class);
		BufferedReader reader = null;
		Map<String, DeviceProfile> dpMap = new HashMap<String, DeviceProfile>();
		String[] header = null;
		try {
			reader = new BufferedReader(
					new InputStreamReader(
							ResourceUtils.getResourceFileOrInputStream(
									config.getDeviceDir() + pathProfile),
							"windows-31j"
							)
					);
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (header == null) {
					header = CSVSplitUtils.splitLineToArray(line);
					continue;
				}
				String[] val = CSVSplitUtils.splitLineToArray(line);
				DeviceProfile dp = new DeviceProfile();
				for (int i=0; i<val.length; i++) {
					dp.put(header[i], val[i]);
				}
				List<String> uaSet = uaMap.get(val[0]+"/"+val[1]);
				if (uaSet != null) {
					for (String ua : uaSet) {
						dpMap.put(ua, dp);
					}
				}
			}
		} catch (IOException e) {
			throw new MobyletRuntimeException(
					"IO例外が発生しました", e);
		}	finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					//NOP
				}
			}
		}
		return dpMap;
	}


	protected Map<String, DeviceDisplay> readDisplayInfo(Map<String, List<String>> uaMap) {
		MobyletConfig config = SingletonUtils.get(MobyletConfig.class);
		BufferedReader reader = null;
		Map<String, DeviceDisplay> diMap = new HashMap<String, DeviceDisplay>();
		String[] header = null;
		try {
			reader = new BufferedReader(
					new InputStreamReader(
							ResourceUtils.getResourceFileOrInputStream(
									config.getDeviceDir() + pathDisplay),
							"windows-31j"
							)
					);
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (header == null) {
					header = CSVSplitUtils.splitLineToArray(line);
					continue;
				}
				String[] val = CSVSplitUtils.splitLineToArray(line);
				List<String> keys = uaMap.get(val[0]+"/"+val[1]);
				if (keys == null) {
					continue;
				}
				if ("ブラウザ画像サイズ".equals(val[2])) {
					if (diMap.get(keys.get(0)) == null) {
						DeviceDisplay dd = new DeviceDisplay();
						dd.setWidth(new Integer(val[3]));
						dd.setHeight(new Integer(val[4]));
						for (String key : keys) {
							diMap.put(key, dd);
						}
					} else {
						DeviceDisplay dd = diMap.get(keys.get(0));
						dd.setWidth(new Integer(val[3]));
						dd.setHeight(new Integer(val[4]));
					}
				} else {
					DeviceDisplay dd = new DeviceDisplay();
					dd.setWidth(new Integer(val[3]));
					dd.setHeight(new Integer(val[4]));
					if (diMap.get(keys.get(0)) == null) {
						DeviceDisplay pdd = new DeviceDisplay();
						pdd.putAnotherDisplay(val[2], dd);
						for (String key : keys) {
							diMap.put(key, pdd);
						}
					} else {
						DeviceDisplay pdd = diMap.get(keys.get(0));
						pdd.putAnotherDisplay(val[2], dd);
					}
				}
			}
		} catch (IOException e) {
			throw new MobyletRuntimeException(
					"IO例外が発生しました", e);
		}	finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					//NOP
				}
			}
		}
		return diMap;
	}
}

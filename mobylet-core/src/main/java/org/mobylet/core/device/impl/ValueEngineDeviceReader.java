package org.mobylet.core.device.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.device.Device;
import org.mobylet.core.device.DeviceDisplay;
import org.mobylet.core.device.DeviceProfile;
import org.mobylet.core.device.DeviceReader;
import org.mobylet.core.util.CSVSplitUtils;
import org.mobylet.core.util.ResourceUtils;

public class ValueEngineDeviceReader implements DeviceReader {

	private static final String PATH_UA = "device/UserAgent.csv";

	private static final String PATH_PF = "device/ProfileData.csv";

	private static final String PATH_DP = "device/DisplayInfo.csv";


	@Override
	public Map<String, Device> read() {
		Map<String, Device> dvMap = new HashMap<String, Device>(1024);
		Map<String, String> uaMap = readUserAgent();
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

	protected Map<String, String> readUserAgent() {
		BufferedReader reader = null;
		Map<String, String> uaMap = new HashMap<String, String>();
		String[] header = null;
		try {
			reader = new BufferedReader(
					new InputStreamReader(
							ResourceUtils.getResourceFileOrInputStream(PATH_UA)
							)
					);
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (header == null) {
					header = CSVSplitUtils.splitLineToArray(line);
					continue;
				}
				String[] val = CSVSplitUtils.splitLineToArray(line);
				uaMap.put(val[0]+"/"+val[1], val[3]);
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

	protected Map<String, DeviceProfile> readProfileData(Map<String, String> uaMap) {
		BufferedReader reader = null;
		Map<String, DeviceProfile> dpMap = new HashMap<String, DeviceProfile>();
		String[] header = null;
		try {
			reader = new BufferedReader(
					new InputStreamReader(
							ResourceUtils.getResourceFileOrInputStream(PATH_PF)
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
				dpMap.put(uaMap.get(val[0]+"/"+val[1]), dp);
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


	protected Map<String, DeviceDisplay> readDisplayInfo(Map<String, String> uaMap) {
		BufferedReader reader = null;
		Map<String, DeviceDisplay> diMap = new HashMap<String, DeviceDisplay>();
		String[] header = null;
		try {
			reader = new BufferedReader(
					new InputStreamReader(
							ResourceUtils.getResourceFileOrInputStream(PATH_DP)
							)
					);
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (header == null) {
					header = CSVSplitUtils.splitLineToArray(line);
					continue;
				}
				String[] val = CSVSplitUtils.splitLineToArray(line);
				String key = uaMap.get(val[0]+"/"+val[1]);
				if ("ブラウザ画像サイズ".equals(val[2])) {
					if (diMap.get(key) == null) {
						DeviceDisplay dd = new DeviceDisplay();
						dd.setWidth(new Integer(val[3]));
						dd.setHeight(new Integer(val[4]));
						diMap.put(key, dd);
					} else {
						DeviceDisplay dd = diMap.get(key);
						dd.setWidth(new Integer(val[3]));
						dd.setHeight(new Integer(val[4]));
					}
				} else {
					DeviceDisplay dd = new DeviceDisplay();
					dd.setWidth(new Integer(val[3]));
					dd.setHeight(new Integer(val[4]));
					if (diMap.get(key) == null) {
						DeviceDisplay pdd = new DeviceDisplay();
						pdd.putAnotherDisplay(val[2], dd);
						diMap.put(key, pdd);
					} else {
						DeviceDisplay pdd = diMap.get(key);
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

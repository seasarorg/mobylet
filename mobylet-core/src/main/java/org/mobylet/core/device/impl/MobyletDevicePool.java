package org.mobylet.core.device.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mobylet.core.MobyletFactory;
import org.mobylet.core.device.Device;
import org.mobylet.core.device.DevicePool;
import org.mobylet.core.device.DeviceReader;
import org.mobylet.core.http.MobyletContext;
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
		MobyletContext context = RequestUtils.getMobyletContext();
		Device d = null;
		if ((d = context.get(Device.class)) != null) {
			return d;
		} else {
			context.set(deviceMap.get(getKeyString()));
			return get();
		}
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

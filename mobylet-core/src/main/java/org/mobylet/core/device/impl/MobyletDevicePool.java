package org.mobylet.core.device.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.mobylet.core.MobyletFactory;
import org.mobylet.core.device.Device;
import org.mobylet.core.device.DevicePool;
import org.mobylet.core.device.DeviceReader;
import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;

public class MobyletDevicePool implements DevicePool {

	private static final String KEY = Device.class.getName();

	protected Map<String, Device> deviceMap;


	public MobyletDevicePool() {
		deviceMap = new HashMap<String, Device>(1024);
		DeviceReader reader = SingletonUtils.get(DeviceReader.class);
		deviceMap.putAll(reader.read());
	}

	@Override
	public Device get() {
		HttpServletRequest request = RequestUtils.get();
		if (request != null) {
			Object obj = null;
			if ((obj = request.getAttribute(KEY)) != null &&
					obj instanceof Device) {
				return (Device)obj;
			} else {
				request.setAttribute(KEY, deviceMap.get(getKeyString()));
				return get();
			}
		}
		return null;
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

package org.mobylet.core.ip;

import org.mobylet.core.MobyletRuntimeException;

public class IpAddress {

	protected String ipString;

	protected int ipStart;

	protected int ipEnd;

	protected int mask;

	public static int getIntegerIp(String ip) {
		String[] ipElements = ip.split("[.]");
		if (ipElements.length != 4) {
			throw new MobyletRuntimeException("IPアドレスの形式が間違っています = " + ip, null);
		}
		int ipInteger = 0x0000;
		ipInteger = Integer.parseInt(ipElements[0]) << 24;
		ipInteger += Integer.parseInt(ipElements[1]) << 16;
		ipInteger += Integer.parseInt(ipElements[2]) << 8;
		ipInteger += Integer.parseInt(ipElements[3]);
		return ipInteger;
	}

	public IpAddress(String ip) {
		ipString = ip;
		int maskIndex = 0;
		if ((maskIndex = ip.indexOf('/')) > 0) {
			mask = Integer.parseInt(ip.substring(maskIndex + 1).trim());
			ipString = ip.substring(0, maskIndex).trim();
		}
		int ipInteger = getIntegerIp(ipString);
		if (mask != 0) {
			ipStart = ipInteger & (-1 << (32-mask));
			ipEnd = ipInteger | (-1 >>> mask);
		} else {
			ipStart = ipInteger;
			ipEnd = ipInteger;
		}
	}

	public boolean containsIp(String ip) {
		return containsIp(getIntegerIp(ip));
	}

	public boolean containsIp(int ipInteger) {
		return (ipStart <= ipInteger) && (ipInteger <= ipEnd);
	}

	public String getIpString() {
		return ipString;
	}

}

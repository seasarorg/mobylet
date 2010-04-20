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

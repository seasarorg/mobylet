package org.mobylet.core.ip;

import java.util.ArrayList;

public class IpAddressList extends ArrayList<IpAddress> {

	private static final long serialVersionUID = 5786895712087530202L;

	public boolean containsIp(String ip) {
		int ipInteger = IpAddress.getIntegerIp(ip);
		for (IpAddress allowIp : this) {
			if (allowIp.containsIp(ipInteger)) {
				return true;
			}
		}
		return false;
	}

}

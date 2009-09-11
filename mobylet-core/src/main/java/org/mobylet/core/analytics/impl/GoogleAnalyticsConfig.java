package org.mobylet.core.analytics.impl;

import org.mobylet.core.MobyletFactory;
import org.mobylet.core.analytics.AnalyticsConfig;
import org.mobylet.core.analytics.AnalyticsUUKey;
import org.mobylet.core.util.RequestUtils;

public class GoogleAnalyticsConfig implements AnalyticsConfig {


	public static final String HTTP_URL = "http://www.google-analytics.com/__utm.gif";


	protected AnalyticsUUKey key = AnalyticsUUKey.GUID;

	protected String id;

	protected String domain;


	@Override
	public String getURL() {
		String cookie = null;
		if (key == null) {
			key = AnalyticsUUKey.GUID;
		}
		switch (key) {
		case UID:
			cookie = MobyletFactory.getInstance().getUid();
			break;
		case GUID:
			cookie = MobyletFactory.getInstance().getGuid();
			break;
		case JSESSIONID:
			cookie = RequestUtils.get().getSession(true).getId();
			break;
		case NONE:
			cookie = "" + System.currentTimeMillis() + System.nanoTime();
			break;
		}
		String utmac = id;
		String utmhn = domain;
		String utmn = "" + (long)(1000000000L + (Math.random() * 8999999999L));
		String random = "" + (long)(1000000000L + (Math.random() * 1147483647L));
		String today = "" + (System.currentTimeMillis() / 1000);
		String referer = RequestUtils.get().getHeader("Referer");
		String uri = RequestUtils.get().getRequestURI();
		String uservar = id;

		StringBuilder buf = new StringBuilder();
		buf.append(HTTP_URL)
			.append("?utmwv=1")
			.append("&utmn=" + utmn)
			.append("&utmsr=-")
			.append("&utmsc=-")
			.append("&utmul=-")
			.append("&utmje=0")
			.append("&utmfl=-")
			.append("&utmdt=-")
			.append("&utmhn=" + utmhn)
			.append("&utmr=" + referer)
			.append("&utmp=" + uri)
			.append("&utmac=" + utmac)
			.append("&utmcc=__utma%3D" + cookie + ".")
			.append(random + "." + today + "." + today + "." + today + ".2%3B%2B__utmb%3D")
			.append(cookie + "%3B%2B__utmc%3D" + cookie + "%3B%2B__utmz%3D" + cookie + ".")
			.append(today + ".2.2.utmccn%3D(direct)%7Cutmcsr%3D(direct)%7Cutmcmd%3D(none)%3B%2B__utmv%3D")
			.append(cookie + "." + uservar + "%3B");

		return buf.toString();
	}

}

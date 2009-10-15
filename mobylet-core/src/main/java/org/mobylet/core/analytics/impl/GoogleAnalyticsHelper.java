package org.mobylet.core.analytics.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mobylet.core.MobyletFactory;
import org.mobylet.core.analytics.AnalyticsHelper;
import org.mobylet.core.analytics.UniqueUserKey;
import org.mobylet.core.http.MobyletFilter.NativeUrl;
import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.core.util.UrlEncoder;

public class GoogleAnalyticsHelper implements AnalyticsHelper {


	public static final String HTTP_URL = "http://www.google-analytics.com/__utm.gif";

	public static final Pattern RGX_URL = Pattern.compile(".+//([^/]+)(/.*)");

	private String cookie = "";
	private String domain = "";
	private String referer = "";
	private String uri = "";

	@Override
	public void prepare(){
		GoogleAnalyticsConfig config =
			SingletonUtils.get(GoogleAnalyticsConfig.class);
		UniqueUserKey key = config.getUniqueUserKey();
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
		String url = null;
		if(StringUtils.isNotEmpty(config.getRequestUrlHeader())) {
			url = RequestUtils.get().getHeader(config.getRequestUrlHeader());
		}
		if(url == null) {
			url = RequestUtils.getMobyletContext().get(NativeUrl.class).toString();
			String queryString = RequestUtils.get().getQueryString();
			if(queryString != null) {
				url = url + "?" + queryString;
			}
		}
		Matcher urlMatcher = RGX_URL.matcher(url);
		if (urlMatcher.find()) {
			domain = urlMatcher.group(1);
			uri = UrlEncoder.encode(urlMatcher.group(2), MobyletFactory.getInstance().getDialect().getCharset());
		}
		referer = RequestUtils.get().getHeader("Referer");
		if(referer == null) {
			referer = "-";
		} else {
			referer = UrlEncoder.encode(referer, MobyletFactory.getInstance().getDialect().getCharset());
		}
	}

	@Override
	public String getURL(String id) {
		String utmac = id;
		String utmhn = domain;
		String utmn = "" + (long)(1000000000L + (Math.random() * 8999999999L));
		String random = "" + (long)(1000000000L + (Math.random() * 1147483647L));
		String today = "" + (System.currentTimeMillis() / 1000);
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

		System.out.println("[ANALYTICS-URL] = " + buf.toString());
		return buf.toString();
	}
}

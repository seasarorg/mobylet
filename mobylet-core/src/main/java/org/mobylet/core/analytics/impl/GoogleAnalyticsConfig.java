package org.mobylet.core.analytics.impl;

import java.util.Properties;

import org.mobylet.core.analytics.UniqueUserKey;
import org.mobylet.core.config.MobyletInjectionConfig;

public class GoogleAnalyticsConfig extends MobyletInjectionConfig {

	public static final String KEY_MAXTHREAD = "analytics.max.thread";

	public static final String KEY_TIMEOUT = "analytics.connection.timeout";

	public static final String KEY_UUKEY = "analytics.uniqueuser.key";

	public static final String KEY_HEADER_REQUESTURL = "analytics.header.requesturl";


	protected Integer maxThread;

	protected Integer connectionTimeout;

	protected UniqueUserKey uniqueUserKey;

	protected String requestUrlHeader;


	public GoogleAnalyticsConfig() {
		super();
		init();
	}

	public Integer getMaxThread() {
		return maxThread;
	}

	public Integer getConnectionTimeout() {
		return connectionTimeout;
	}

	public UniqueUserKey getUniqueUserKey() {
		return uniqueUserKey;
	}

	public String getRequestUrlHeader() {
		return requestUrlHeader;
	}

	protected void init() {
		Properties config = getConfig();
		//MaxThread
		try {
			String val = config.getProperty(KEY_MAXTHREAD);
			maxThread = Integer.parseInt(val);
		} catch (Exception e) {
			//NOP
		}
		if (maxThread == null) {
			maxThread = 0;
		}
		//ConnectionTimeout
		try {
			String val = config.getProperty(KEY_TIMEOUT);
			connectionTimeout = Integer.parseInt(val);
		} catch (Exception e) {
			//NOP
		}
		if (connectionTimeout == null) {
			connectionTimeout = 15000;
		}
		//UniqueUserKey
		try {
			String val = config.getProperty(KEY_UUKEY);
			uniqueUserKey = UniqueUserKey.valueOf(val);
		} catch (Exception e) {
			//NOP
		}
		if (uniqueUserKey == null) {
			uniqueUserKey = UniqueUserKey.GUID;
		}
		//UrlNotifyHeadername
		try {
			requestUrlHeader = config.getProperty(KEY_HEADER_REQUESTURL);
		} catch (Exception e) {
			//NOP
		}
	}

}

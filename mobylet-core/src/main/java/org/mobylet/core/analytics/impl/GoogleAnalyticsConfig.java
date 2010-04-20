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
package org.mobylet.core.analytics.impl;

import java.util.Properties;
import java.util.regex.Pattern;

import org.mobylet.core.analytics.UniqueUserKey;
import org.mobylet.core.config.MobyletInjectionConfig;
import org.mobylet.core.util.StringUtils;

public class GoogleAnalyticsConfig extends MobyletInjectionConfig {

	public static final String KEY_USE_JS = "analytics.use.js";

	public static final String KEY_MAXTHREAD = "analytics.max.thread";

	public static final String KEY_MAXSESSION = "analytics.max.session";

	public static final String KEY_TIMEOUT = "analytics.connection.timeout";

	public static final String KEY_UUKEY = "analytics.uniqueuser.key";

	public static final String KEY_HEADER_REQUESTURL = "analytics.header.requesturl";

	public static final String KEY_CRAWLER_IGNORE = "analytics.crawler.is.ignore";

	public static final String KEY_CRAWLER_REGEX = "analytics.crawler.regex";

	public static final String KEY_MOBILE_CRAWLER_IGNORE = "analytics.mobile.crawler.is.ignore";

	public static final String KEY_MOBILE_CRAWLER_REGEX = "analytics.mobile.crawler.regex";


	public static final String REGEX_CRAWLER =
		".*(" +
		"Accoona|" +
		"Alexa|" +
		"Ask[.]com|" +
		"Baiduspider[+]|" +
		"BBSWriter|" +
		"DotBot|" +
		"Exabot|" +
		"e-SocietyRobot|" +
		"Feedfetcher-Google|" +
		"findlinks|" +
		"GameSpy|" +
		"Gigabot|" +
		"Googlebot|" +
		"Grub|" +
		"ichiro|" +
		"Infoseek|" +
		"Inktomi Slurp|" +
		"livedoor|" +
		"Mediapartners-Google|" +
		"MJ12bot|" +
		"msnbot|" +
		"NaverBot|" +
		"OutfoxBot|" +
		"Scooter|" +
		"Shim-Crawler|" +
		"sogou spider|" +
		"Speedy Spider|" +
		"Steeler|" +
		"Twiceler|" +
		"W3C|" +
		"Yahoo[!]|" +
		"YahooFeedSeeker|" +
		"YahooSeeker" +
		").*";

	public static final String REGEX_MOBILE_CRAWLER =
		".*(" +
		"Googlebot-Mobile|" +
		"Mediapartners-Google|" +
		"Y[!]J-SRD|" +
		"mobile goo|" +
		"livedoor-Yill|" +
		"Hatena-Mobile-Gateway|" +
		"moba-crawler" +
		").*";

	protected Boolean useJs;

	protected Integer maxThread;

	protected Integer connectionTimeout;

	protected Integer maxSession;

	protected UniqueUserKey uniqueUserKey;

	protected String requestUrlHeader;

	protected Boolean isIgnoreCrawler;

	protected Pattern regexCrawler;

	protected Boolean isIgnoreMobileCrawler;

	protected Pattern regexMobileCrawler;


	public GoogleAnalyticsConfig() {
		super();
		initialize();
	}

	public Boolean useJs() {
		return useJs;
	}

	public Integer getMaxThread() {
		return maxThread;
	}

	public Integer getMaxSession() {
		return maxSession;
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

	public Boolean isIgnoreCrawler() {
		return isIgnoreCrawler;
	}

	public Pattern getRegexCrawler() {
		return regexCrawler;
	}

	public Boolean isIgnoreMobileCrawler() {
		return isIgnoreMobileCrawler;
	}

	public Pattern getRegexMobileCrawler() {
		return regexMobileCrawler;
	}

	protected void initialize() {
		Properties config = getConfig();
		//UseJS
		try {
			if (StringUtils.isEmpty(config.getProperty(KEY_USE_JS))) {
				useJs = false;
			} else {
				useJs = Boolean.valueOf(config.getProperty(KEY_USE_JS));
			}
		} catch (Exception e) {
			useJs = false;
		}
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
		//MaxSession
		try {
			String val = config.getProperty(KEY_MAXSESSION);
			maxSession = Integer.parseInt(val);
		} catch (Exception e) {
			//NOP
		}
		if (maxSession == null) {
			maxSession = 8192;
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
		//Crawler
		try {
			if (StringUtils.isEmpty(config.getProperty(KEY_CRAWLER_IGNORE))) {
				isIgnoreCrawler = true;
			} else {
				isIgnoreCrawler = Boolean.valueOf(config.getProperty(KEY_CRAWLER_IGNORE));
			}
		} catch (Exception e) {
			isIgnoreCrawler = true;
		}
		try {
			regexCrawler = Pattern.compile(config.getProperty(KEY_CRAWLER_REGEX));
		} catch (Exception e) {
			regexCrawler = Pattern.compile(REGEX_CRAWLER);
		}
		//MobileCrawler
		try {
			if (StringUtils.isEmpty(config.getProperty(KEY_MOBILE_CRAWLER_IGNORE))) {
				isIgnoreMobileCrawler = true;
			} else {
				isIgnoreMobileCrawler = Boolean.valueOf(config.getProperty(KEY_MOBILE_CRAWLER_IGNORE));
			}
		} catch (Exception e) {
			isIgnoreMobileCrawler = true;
		}
		try {
			regexMobileCrawler = Pattern.compile(config.getProperty(KEY_MOBILE_CRAWLER_REGEX));
		} catch (Exception e) {
			regexMobileCrawler = Pattern.compile(REGEX_MOBILE_CRAWLER);
		}
	}

	@Override
	public String getConfigName() {
		return "mobylet.analytics.properties";
	}
}

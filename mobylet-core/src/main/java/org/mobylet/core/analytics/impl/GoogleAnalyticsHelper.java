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

import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mobylet.core.Carrier;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.analytics.AnalyticsHelper;
import org.mobylet.core.analytics.AnalyticsParameters;
import org.mobylet.core.analytics.AnalyticsSearchEngine;
import org.mobylet.core.analytics.AnalyticsSession;
import org.mobylet.core.analytics.AnalyticsSessionManager;
import org.mobylet.core.analytics.UniqueUserKey;
import org.mobylet.core.analytics.AnalyticsSearchEngine.SearchEngineType;
import org.mobylet.core.define.DefCharset;
import org.mobylet.core.http.MobyletFilter.NativeUrl;
import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.core.util.UrlDecoder;
import org.mobylet.core.util.UrlEncoder;

public class GoogleAnalyticsHelper implements AnalyticsHelper {


	public static final String HTTP_URL = "http://www.google-analytics.com/__utm.gif";

	public static final Pattern RGX_URL = Pattern.compile(".+//([^/]+)(/.*)");

	public static final Charset UTF8 = Charset.forName(DefCharset.UTF8);

	public static final Charset WIN31J = Charset.forName(DefCharset.WIN31J);


	public static final Pattern RGX_SEARCH_EZWEB = Pattern.compile("query=[^&]+");

	public static final Pattern RGX_SEARCH_YAHOO = Pattern.compile("p=[^&]+");

	public static final Pattern RGX_SEARCH_GOOGLE = Pattern.compile("q=[^&]+");

	public static final Pattern RGX_SEARCH_CHARSET_GOOGLE = Pattern.compile("ie=[^&]+");


	@Override
	public String getURL(AnalyticsParameters p) {
		//OTHERの場合はAnalyticsへ打ち込まない
		if (p.getCarrier() == Carrier.OTHER) {
			return null;
		}
		AnalyticsSessionManager manager = SingletonUtils.get(AnalyticsSessionManager.class);
		AnalyticsSession session = manager.get(p.getVisitorNo());
		AnalyticsSearchEngine ase = getAnalyticsSearchEngine(p.getReferer());

		StringBuilder buf = new StringBuilder();
		buf.append(HTTP_URL)
			.append("?utmwv=" + "1")
			.append("&utmn="  + p.getUtmn())			//Random Number
			.append("&utmsr=" + p.getDisplaySize())		//Display Size
			.append("&utmsc=" + p.getProcessor())		//Processor
			.append("&utmcs=" + DefCharset.UTF8)		//Character Encoding
			.append("&utmul=" + p.getUseLanguage())		//Use Language(Locale)
			.append("&utmje=" + "0")					//Java Applet Enable
			.append("&utmfl=" + "-")					//Flash Version
			.append("&utmdt=" + "-")					//Page Title
			.append("&utmhn=" + p.getDomain())			//Domain Name
			.append("&utmr="  + UrlEncoder.encode(p.getReferer(), UTF8))	//Referer
			.append("&utmp="  + UrlEncoder.encode(p.getUri(), UTF8))		//URI
			.append("&utmac=" + p.getUrchinId())		//Urchin ID
			.append("&utmcc=" + UrlEncoder.encode(
					"__utma="   + p.getDomainHash()             + "."
								+ p.getVisitorNo()              + "."
								+ session.getFirstTmString()    + "."
								+ session.getPreviousTmString() + "."
								+ session.getCurrentTmString()  + "."
								+ session.getVisitCount()       + ";+" +
					"__utmb="   + p.getDomainHash()             + ";+" +
					"__utmc="   + p.getDomainHash()             + ";+" +
					"__utmz="   + p.getDomainHash()             + "."
								+ session.getFirstTmString()    + "."
								+ session.getVisitCount()       + "."
								+ "1"                           + "."
								+ "utmcsr=" + ase.getUTMCSR()              + "|"
								+ "utmccn=" + "(" + ase.getUTMCMD() + ")"  + "|"
								+ "utmcmd=" + ase.getUTMCMD()              + "|"
								+ "utmctr=" + UrlEncoder.encode(
										ase.getEncodedSearchWords(), UTF8) + ";" +
					"__utmv="   + p.getDomainHash() + "."
								+ p.getVisitorNo()  + ";"
					, UTF8));

		return buf.toString();
	}

	@Override
	public AnalyticsParameters getParameters(String id) {
		AnalyticsParameters parameters = new AnalyticsParameters(id);
		GoogleAnalyticsConfig config =
			SingletonUtils.get(GoogleAnalyticsConfig.class);
		//VisitorId
		String visitorId = null;
		UniqueUserKey key = config.getUniqueUserKey();
		switch (key) {
		case UID:
			visitorId = MobyletFactory.getInstance().getUid();
			break;
		case GUID:
			visitorId = MobyletFactory.getInstance().getGuid();
			break;
		case JSESSIONID:
			visitorId = RequestUtils.get().getSession(true).getId();
			break;
		}
		if (visitorId == null) {
			visitorId = "" + System.currentTimeMillis() + System.nanoTime();
		}
		parameters.setVisitorId(visitorId);
		//UserAgent
		parameters.setUserAgent(RequestUtils.getUserAgent());
		//URL
		String url = null;
		if(StringUtils.isNotEmpty(config.getRequestUrlHeader())) {
			url = RequestUtils.get().getHeader(config.getRequestUrlHeader());
		}
		if(url == null) {
			url = RequestUtils.getMobyletContext().get(NativeUrl.class).toString();
		}
		Matcher urlMatcher = RGX_URL.matcher(url);
		if (urlMatcher.find()) {
			parameters.setDomain(urlMatcher.group(1));
			parameters.setUri(getCleanUrl(urlMatcher.group(2)));
		}
		//Referer
		String referer = RequestUtils.get().getHeader("Referer");
		if(referer == null) {
			referer = "-";
		}
		parameters.setReferer(getCleanUrl(referer));
		return parameters;
	}

	protected AnalyticsSearchEngine getAnalyticsSearchEngine(String referer) {
		AnalyticsSearchEngine ase = new AnalyticsSearchEngine();
		if (StringUtils.isEmpty(referer)) {
			return ase;
		}
		if (referer.contains("ezsch.ezweb.ne.jp")) {
			Matcher matcher = RGX_SEARCH_EZWEB.matcher(referer);
			if (matcher.find()) {
				String words = matcher.group();
				ase.setType(SearchEngineType.EZWEB);
				ase.setEncodedSearchWords(
						UrlDecoder.decode(
								words.substring(6), WIN31J));
			}
		}
		else if (referer.contains("search.mobile.yahoo.co.jp")) {
			Matcher matcher = RGX_SEARCH_YAHOO.matcher(referer);
			if (matcher.find()) {
				String words = matcher.group();
				ase.setType(SearchEngineType.YAHOO_MOBILE);
				ase.setEncodedSearchWords(
						UrlDecoder.decode(
								words.substring(2), WIN31J));
			}
		}
		else if (referer.contains("www.google.co.jp/m")) {
			Matcher matcher = RGX_SEARCH_GOOGLE.matcher(referer);
			if (matcher.find()) {
				Matcher charsetMatcher = RGX_SEARCH_CHARSET_GOOGLE.matcher(referer);
				Charset charset = null;
				if(charsetMatcher.find()) {
					String enc = charsetMatcher.group();
					if(DefCharset.SJIS.equals(enc.substring(3).toLowerCase())){
						charset = WIN31J;
					}
				}
				if(charset == null) {
					charset = UTF8;
				}

				String words = matcher.group();
				ase.setType(SearchEngineType.GOOGLE_MOBILE);
				ase.setEncodedSearchWords(
						UrlDecoder.decode(
								words.substring(2), charset));
			}
		}
		return ase;
	}

	protected String getCleanUrl(String url) {
		if (StringUtils.isEmpty(url)) {
			return "";
		}
		return url.replaceAll(";jsessionid=[a-zA-Z0-9.]+", "")
					.replaceAll("(guid|GUID)=(on|ON)", "")
					.replaceAll("(uid|UID)=[a-zA-Z0-9]{12}", "");
	}
}

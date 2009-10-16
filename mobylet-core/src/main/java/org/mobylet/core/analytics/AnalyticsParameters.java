package org.mobylet.core.analytics;

import java.nio.charset.Charset;

import org.mobylet.core.Carrier;
import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.device.DeviceDisplay;

public class AnalyticsParameters {

	protected Charset requestCharset;

	protected String urchinId;

	protected String visitorId;

	protected String userAgent;

	protected Carrier carrier;

	protected String domain;

	protected String referer;

	protected String uri;

	protected String utmn;

	protected String random;

	protected String today;

	protected String displaySize;

	protected String processor;

	protected String useLanguage;


	public AnalyticsParameters(String urchinId) {
		this.urchinId = urchinId;
		processor = "32-bit";
		useLanguage = "ja";
		utmn = "" + (long)(1000000000L + (Math.random() * 8999999999L));
		random = "" + (long)(1000000000L + (Math.random() * 1147483647L));
		today = "" + (System.currentTimeMillis() / 1000);
		Mobylet m = MobyletFactory.getInstance();
		DeviceDisplay display = m.getDisplay();
		if (display != null) {
			displaySize = display.getWidth() + "x" + display.getHeight();
		} else {
			displaySize = "1280x960";
		}
		requestCharset = m.getDialect().getCharset();
		carrier = m.getCarrier();
	}

	public String getUtmn() {
		return utmn;
	}

	public String getRandom() {
		return random;
	}

	public String getToday() {
		return today;
	}

	public String getDisplaySize() {
		return displaySize;
	}

	public String getProcessor() {
		return processor;
	}

	public String getUseLanguage() {
		return useLanguage;
	}

	public String getDomainHash() {
		Integer h = domain.hashCode();
		return "" + (h < 0 ? -h : h);
	}

	public String getUrchinId() {
		return urchinId;
	}

	public void setUrchinId(String urchinId) {
		this.urchinId = urchinId;
	}

	public String getVisitorId() {
		return visitorId;
	}

	public String getVisitorNo() {
		Integer h = visitorId.hashCode();
		return "" + (h < 0 ? -h : h);
	}

	public void setVisitorId(String visitorId) {
		this.visitorId = visitorId;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public Carrier getCarrier() {
		return carrier;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Charset getRequestCharset() {
		return requestCharset;
	}
}

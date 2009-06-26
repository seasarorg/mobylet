package org.mobylet.taglibs.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mobylet.core.Carrier;
import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.taglibs.MobyletDynamicBodyTagSupport;
import org.mobylet.taglibs.config.TransitionTagConfig;

public abstract class TransitionTag extends MobyletDynamicBodyTagSupport {

	private static final long serialVersionUID = 1L;

	public static final TransitionTagConfig config = new TransitionTagConfig();


	protected boolean isUidQueryRequired = config.isUidQueryRequired();

	protected boolean isGuidQueryRequired = config.isGuidQueryRequired();

	protected boolean isSessionCookiePriority = config.isSessionCookiePriority();

	protected boolean isUidOrGuidQueryRequiredInSecure =
		config.isUidOrGuidQueryRequiredInSecure();


	protected String getSessionId() {
		Mobylet m = MobyletFactory.getInstance();
		HttpServletRequest request = RequestUtils.get();
		//Session
		HttpSession session = request.getSession(false);
		if (session != null) {
			if (!isSessionCookiePriority() ||
					(isSessionCookiePriority() && !m.hasCookies())) {
				return session.getId();
			}
		}
		return null;
	}

	public Entry getAppendParameter(String url) {
		Mobylet m = MobyletFactory.getInstance();
		HttpServletRequest request = RequestUtils.get();
		//SecureQuery
		if (isUidOrGuidQueryRequiredInSecure() &&
				m.getCarrier() == Carrier.DOCOMO &&
				(url.startsWith("https:") ||
						(!url.startsWith("http:") && request.isSecure()))) {
			String id = m.getGuid();
			if (StringUtils.isEmpty(id)) {
				id = m.getUid();
				if (StringUtils.isNotEmpty(id)) {
					return new Entry("uid", id);
				}
			}
		//UidQuery
		} else if (isUidQueryRequired() &&
				m.getCarrier() == Carrier.DOCOMO &&
				(url.startsWith("http:") ||
						(!url.startsWith("https:") && !request.isSecure()))) {
			return new Entry("uid", "NULLGWDOCOMO");
		//GuidQuery
		} else if (isGuidQueryRequired() &&
				m.getCarrier() == Carrier.DOCOMO &&
				(url.startsWith("http:") ||
						(!url.startsWith("https:") && !request.isSecure()))) {
			return new Entry("guid", "ON");
		}
		return null;
	}


	public boolean isUidQueryRequired() {
		return isUidQueryRequired;
	}

	public void setUidQueryRequired(boolean isUidQueryRequired) {
		this.isUidQueryRequired = isUidQueryRequired;
	}

	public boolean isGuidQueryRequired() {
		return isGuidQueryRequired;
	}

	public void setGuidQueryRequired(boolean isGuidQueryRequired) {
		this.isGuidQueryRequired = isGuidQueryRequired;
	}

	public boolean isSessionCookiePriority() {
		return isSessionCookiePriority;
	}

	public void setSessionCookiePriority(boolean isSessionCookiePriority) {
		this.isSessionCookiePriority = isSessionCookiePriority;
	}

	public boolean isUidOrGuidQueryRequiredInSecure() {
		return isUidOrGuidQueryRequiredInSecure;
	}

	public void setUidOrGuidQueryRequiredInSecure(
			boolean isUidOrGuidQueryRequiredInSecure) {
		this.isUidOrGuidQueryRequiredInSecure = isUidOrGuidQueryRequiredInSecure;
	}


	public static class Entry {

		protected String key;

		protected String value;

		public Entry() {
		}

		public Entry(String k, String v) {
			key = k;
			value = v;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}


}

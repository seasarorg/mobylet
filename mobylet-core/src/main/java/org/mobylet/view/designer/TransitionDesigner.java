package org.mobylet.view.designer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mobylet.core.Carrier;
import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.view.config.TransitionConfig;

public abstract class TransitionDesigner {

	protected static TransitionConfig config = new TransitionConfig();

	protected static String getSessionId() {
		return getSessionId(config);
	}

	protected static String getSessionId(TransitionConfig config) {
		Mobylet m = MobyletFactory.getInstance();
		HttpServletRequest request = RequestUtils.get();
		//Session
		HttpSession session = request.getSession(false);
		if (session != null) {
			if (!config.isSessionCookiePriority() ||
					(config.isSessionCookiePriority() && !m.hasCookies())) {
				return session.getId();
			}
		}
		return null;
	}

	protected static Entry getOptionalEntry(String url) {
		return getOptionalEntry(url, config);
	}

	protected static Entry getOptionalEntry(String url, TransitionConfig config) {
		Mobylet m = MobyletFactory.getInstance();
		HttpServletRequest request = RequestUtils.get();
		//SecureQuery
		if (config.isUidOrGuidQueryRequiredInSecure() &&
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
		} else if (config.isUidQueryRequired() &&
				m.getCarrier() == Carrier.DOCOMO &&
				(url.startsWith("http:") ||
						(!url.startsWith("https:") && !request.isSecure()))) {
			return new Entry("uid", "NULLGWDOCOMO");
		//GuidQuery
		} else if (config.isGuidQueryRequired() &&
				m.getCarrier() == Carrier.DOCOMO &&
				(url.startsWith("http:") ||
						(!url.startsWith("https:") && !request.isSecure()))) {
			return new Entry("guid", "ON");
		}
		return null;
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

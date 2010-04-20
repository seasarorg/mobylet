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
package org.mobylet.view.designer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mobylet.core.Carrier;
import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.config.MobyletConfig;
import org.mobylet.core.config.enums.JSession;
import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.core.util.UrlUtils;
import org.mobylet.view.config.TransitionConfig;

public abstract class TransitionDesigner extends SingletonDesigner {

	public static TransitionConfig config = new TransitionConfig();


	protected String constructUrl(String url) {
		return constructUrl(url, config);
	}

	protected String constructUrl(String url, TransitionConfig config) {
		if (url == null) {
			url = "";
		}
		//ContextPath
		if (config.isAdditionalContext() && url.startsWith("/")) {
			url = RequestUtils.get().getContextPath() + url;
		}
		//Session
		String sessionId = getSessionId(config);
		if (StringUtils.isNotEmpty(sessionId)) {
			url = UrlUtils.addSession(url, sessionId);
		}
		//Query
		Entry optionalEntry = getOptionalEntry(url, config);
		if (optionalEntry != null) {
			url = UrlUtils.addParameter(
					url, optionalEntry.getKey(), optionalEntry.getValue());
		}
		return url;
	}

	protected String getSessionId() {
		return getSessionId(config);
	}

	protected String getSessionId(TransitionConfig config) {
		MobyletConfig mobyletConfig = SingletonUtils.get(MobyletConfig.class);
		if (mobyletConfig.getJSession() == JSession.NONE ||
				mobyletConfig.getJSession() == JSession.USE_COOKIE) {
			return null;
		}
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

	protected Entry getOptionalEntry(String url) {
		return getOptionalEntry(url, config);
	}

	protected Entry getOptionalEntry(String url, TransitionConfig config) {
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

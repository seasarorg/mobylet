package org.mobylet.view.designer;

import org.mobylet.core.util.StringUtils;
import org.mobylet.core.util.UrlUtils;
import org.mobylet.view.config.TransitionConfig;

public class AnchorDesigner extends TransitionDesigner {

	public static String getHref(String url) {
		return getHref(url, config);
	}

	public static String getHref(String url, TransitionConfig config) {
		if (url == null) {
			url = "";
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

}

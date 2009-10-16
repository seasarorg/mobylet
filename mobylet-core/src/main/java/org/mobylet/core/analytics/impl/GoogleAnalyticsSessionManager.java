package org.mobylet.core.analytics.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.mobylet.core.analytics.AnalyticsSession;
import org.mobylet.core.analytics.AnalyticsSessionManager;
import org.mobylet.core.util.SingletonUtils;

public class GoogleAnalyticsSessionManager implements AnalyticsSessionManager {

	protected Map<String, AnalyticsSession> sessionMap;

	public GoogleAnalyticsSessionManager() {
		sessionMap = new LinkedHashMap<String, AnalyticsSession>(8192) {
			private static final long serialVersionUID = 1L;
			@Override
			protected boolean removeEldestEntry(
					java.util.Map.Entry<String, AnalyticsSession> eldest) {
				return this.size() >
					SingletonUtils.get(GoogleAnalyticsConfig.class).getMaxSession();
			}
		};
	}

	@Override
	public AnalyticsSession get(String uniqueId) {
		AnalyticsSession session = sessionMap.get(uniqueId);
		if (session != null) {
			session.setPreviousTm(session.getCurrentTm());
			session.setCurrentTm(new Date());
			session.setVisitCount(session.getVisitCount() + 1);
			return session;
		} else {
			session = new AnalyticsSession();
			sessionMap.put(uniqueId, session);
			return session;
		}
	}

}

package org.mobylet.core.analytics.impl;

import java.util.Date;
import java.util.Map;
import java.util.WeakHashMap;

import org.mobylet.core.analytics.AnalyticsSession;
import org.mobylet.core.analytics.AnalyticsSessionManager;

public class GoogleAnalyticsSessionManager implements AnalyticsSessionManager {

	protected Map<String, AnalyticsSession> sessionMap;

	public GoogleAnalyticsSessionManager() {
		//sessionMap = new HashMap<String, AnalyticsSession>(8192);
		sessionMap = new WeakHashMap<String, AnalyticsSession>(8192);
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

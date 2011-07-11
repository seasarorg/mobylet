/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
			Date now = new Date();
			if (session.getAccessTm().getTime() + 1800000 < now.getTime() ) {
				session.setPreviousTm(session.getCurrentTm());
				session.setCurrentTm(now);
				session.setVisitCount(session.getVisitCount() + 1);
			}
			session.touch();
			return session;
		} else {
			session = new AnalyticsSession();
			sessionMap.put(uniqueId, session);
			return session;
		}
	}

}

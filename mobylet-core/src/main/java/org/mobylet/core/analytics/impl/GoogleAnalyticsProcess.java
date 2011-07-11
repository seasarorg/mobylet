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

import java.io.IOException;
import java.net.HttpURLConnection;

import org.mobylet.core.analytics.AnalyticsHelper;
import org.mobylet.core.analytics.AnalyticsParameters;
import org.mobylet.core.log.MobyletLogger;
import org.mobylet.core.util.HttpUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;

public class GoogleAnalyticsProcess implements Runnable {

	protected AnalyticsParameters parameters;

	public GoogleAnalyticsProcess(AnalyticsParameters parameters) {
		this.parameters = parameters;
	}

	@Override
	public void run() {
		GoogleAnalyticsConfig config =
			SingletonUtils.get(GoogleAnalyticsConfig.class);
		AnalyticsHelper helper = SingletonUtils.get(AnalyticsHelper.class);
		String url = helper.getURL(parameters);
		if (StringUtils.isEmpty(url)) {
			return;
		}
		HttpURLConnection connection = HttpUtils.getHttpUrlConnection(url);
		connection.setRequestProperty(
				"User-Agent", parameters.getCarrier().name());
		connection.setRequestProperty(
				"Accept-Language", parameters.getUseLanguage());
		connection.setConnectTimeout(config.getConnectionTimeout());
		try {
			connection.connect();
			connection.getInputStream();
		} catch (IOException e) {
			MobyletLogger logger = SingletonUtils.get(MobyletLogger.class);
			if (logger != null && logger.isLoggable())
				logger.log("[mobylet] GoogleAnalyticsへの通信処理に失敗 = " + e.getMessage());
		}
	}

}

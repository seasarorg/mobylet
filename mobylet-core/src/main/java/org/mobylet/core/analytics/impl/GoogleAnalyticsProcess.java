package org.mobylet.core.analytics.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import org.mobylet.core.analytics.AnalyticsHelper;
import org.mobylet.core.analytics.AnalyticsParameters;
import org.mobylet.core.util.HttpUtils;
import org.mobylet.core.util.InputStreamUtils;
import org.mobylet.core.util.SingletonUtils;

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
		HttpURLConnection connection = HttpUtils.getHttpUrlConnection(url);
		connection.setRequestProperty(
				"User-Agent", parameters.getUserAgent());
		connection.setRequestProperty(
				"Accept-Language", parameters.getUseLanguage());
		connection.setConnectTimeout(config.getConnectionTimeout());
		try {
			connection.connect();
			InputStream inputStream = connection.getInputStream();
			System.out.println("[ANALYTICS-RES] = " + new String(InputStreamUtils.getAllBytes(inputStream), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

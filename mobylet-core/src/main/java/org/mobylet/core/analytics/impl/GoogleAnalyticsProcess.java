package org.mobylet.core.analytics.impl;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.mobylet.core.Carrier;
import org.mobylet.core.analytics.AnalyticsHelper;
import org.mobylet.core.analytics.AnalyticsParameters;
import org.mobylet.core.util.HttpUtils;
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
		if (parameters.getCarrier() == Carrier.OTHER) {
			connection.setRequestProperty(
					"User-Agent", parameters.getUserAgent());
		} else {
			connection.setRequestProperty(
					"User-Agent", parameters.getCarrier().name());
		}
		connection.setRequestProperty(
				"Accept-Language", parameters.getUseLanguage());
		connection.setConnectTimeout(config.getConnectionTimeout());
		try {
			connection.connect();
			connection.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
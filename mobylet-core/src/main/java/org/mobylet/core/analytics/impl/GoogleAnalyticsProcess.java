package org.mobylet.core.analytics.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import org.mobylet.core.analytics.AnalyticsHelper;
import org.mobylet.core.util.HttpUtils;
import org.mobylet.core.util.InputStreamUtils;
import org.mobylet.core.util.SingletonUtils;

public class GoogleAnalyticsProcess implements Runnable {

	protected String id;
	
	public GoogleAnalyticsProcess(String id) {
		this.id = id;
	}
	
	@Override
	public void run() {
		AnalyticsHelper helper = SingletonUtils.get(AnalyticsHelper.class);
		String url = helper.getURL(id);
		HttpURLConnection connection = HttpUtils.getHttpUrlConnection(url);
		connection.setConnectTimeout(15000);
		try {
			connection.connect();
			InputStream inputStream = connection.getInputStream();
			System.out.println("[ANALYTICS-RES] = " + new String(InputStreamUtils.getAllBytes(inputStream), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

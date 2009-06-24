package org.mobylet.core.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.config.MobyletConfig;

public class HttpUtils {

	public static HttpURLConnection getHttpUrlConnection(String path) {
		MobyletConfig config = SingletonUtils.get(MobyletConfig.class);
		try {
			URL url = new URL(path);
			HttpURLConnection urlConnection = null;
			if (config.getHttpProxy() != null) {
				urlConnection =
					(HttpURLConnection)url.openConnection(config.getHttpProxy());
			} else {
				urlConnection = (HttpURLConnection)url.openConnection();
			}
			return urlConnection;
		} catch (MalformedURLException e) {
			throw new MobyletRuntimeException(
					"[Malformed URL] path = " + path, e);
		} catch (IOException e) {
			throw new MobyletRuntimeException(
					"[URL IO-Exception] path = " + path, e);
		}
	}
}

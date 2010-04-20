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
			//ReturnConnection
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

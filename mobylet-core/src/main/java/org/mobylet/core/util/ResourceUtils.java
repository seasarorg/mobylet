/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import org.mobylet.core.MobyletRuntimeException;

public class ResourceUtils {

	public static InputStream getResourceFileOrInputStream(String path) {
		if (StringUtils.isEmpty(path)) {
			return null;
		}
		//Network-Path
		if (PathUtils.isNetworkPath(path)) {
			HttpURLConnection httpURLConnection =
				HttpUtils.getHttpUrlConnection(path);
			try {
				return httpURLConnection.getInputStream();
			} catch (IOException e) {
				throw new MobyletRuntimeException(
						"[URL IO-Exception] path = " + path, e);
			}
		}
		//File-Path
		if (path.startsWith(File.separator)) {
			File f = new File(path);
			if (f != null && f.exists() && f.canRead()) {
				try {
					return new FileInputStream(f);
				} catch (FileNotFoundException e) {
					throw new MobyletRuntimeException(
							"[File Not Fount] filename = " + path, e);
				}
			}
		}
		//Resource-Path
		ClassLoader classLoader =
			Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			classLoader = ResourceUtils.class.getClassLoader();
		}
		if (classLoader == null) {
			return null;
		}
		return classLoader.getResourceAsStream(path);
	}



}

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
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.mobylet.core.MobyletRuntimeException;

public class ResourceUtils {

	public static InputStream getResourceFileOrInputStream(String path) {
		if (StringUtils.isEmpty(path)) {
			return null;
		}
		if (path.indexOf(File.separator) > 0) {
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
		ClassLoader classLoader =
			Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			classLoader = ResourceUtils.class.getClassLoader();
		}
		if (classLoader == null) {
			return null;
		}
		URL resourceUrl = classLoader.getResource(path);
		if (resourceUrl != null) {
			File f;
			try {
				f = new File(new URI(resourceUrl.toExternalForm()));
			} catch (URISyntaxException e) {
				throw new MobyletRuntimeException(
						"[URI Syntax Exception] uri = "
						+ resourceUrl.toExternalForm(), e);
			}
			if (f != null && f.exists() && f.canRead()) {
				try {
					return new FileInputStream(f);
				} catch (FileNotFoundException e) {
					throw new MobyletRuntimeException(
							"[File Not Found in Resource] filename = " + path, e);
				}
			}
		}
		return classLoader.getResourceAsStream(path);
	}
}

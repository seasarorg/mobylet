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
package org.mobylet.view.config;

import java.util.Properties;

import org.mobylet.core.config.MobyletInjectionConfig;
import org.mobylet.core.util.StringUtils;

public class CSSConfig extends MobyletInjectionConfig {

	public static final String KEY_LOCAL_BASE_PATH = "css.local.base.path";

	protected String localBasePath;


	public CSSConfig() {
		Properties config = getConfig();
		if (StringUtils.isNotEmpty(
				config.getProperty(KEY_LOCAL_BASE_PATH))) {
			localBasePath = config.getProperty(KEY_LOCAL_BASE_PATH);
			if (localBasePath.endsWith("/") ||
					localBasePath.endsWith("\\")) {
				localBasePath =
					localBasePath.substring(0, localBasePath.length()-1);
			}
		}
	}


	public String getLocalBasePath() {
		return localBasePath;
	}

	public void setLocalBasePath(String localBasePath) {
		this.localBasePath = localBasePath;
	}

}

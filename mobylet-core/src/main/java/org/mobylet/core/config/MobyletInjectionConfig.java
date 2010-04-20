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
package org.mobylet.core.config;

import java.io.InputStream;
import java.util.Properties;

import org.mobylet.core.log.MobyletLogger;
import org.mobylet.core.util.InputStreamUtils;
import org.mobylet.core.util.ResourceUtils;
import org.mobylet.core.util.SingletonUtils;

public abstract class MobyletInjectionConfig {

	protected Properties injectionConfig;

	public Properties getConfig() {
		if (injectionConfig != null) {
			return injectionConfig;
		} else {
			MobyletLogger logger = SingletonUtils.get(MobyletLogger.class);
			injectionConfig = new MobyletProperties();
			MobyletConfig config = SingletonUtils.get(MobyletConfig.class);
			String path = (config != null ? config.getConfigDir() : "") + getConfigName();
			InputStream inputStream = null;
			try {
				if (logger != null && logger.isLoggable())
					logger.log("[mobylet] InjectionConfig [" + path + "] の読み込み処理開始");
				inputStream =
					ResourceUtils.getResourceFileOrInputStream(path);
				if (inputStream != null) {
					injectionConfig.load(inputStream);
					if (logger != null && logger.isLoggable())
						logger.log("[mobylet] InjectionConfig [" + path + "] が読み込まれました");
				} else {
					if (logger != null && logger.isLoggable())
						logger.log("[mobylet] InjectionConfig [" + path + "] は見つかりませんでした");
				}
			} catch (Exception e) {
				if (logger != null && logger.isLoggable()) {
					logger.log("[mobylet] InjectionConfig [" + path + "] 参照時に例外発生 = " + e);
					e.printStackTrace();
				}
			} finally {
				InputStreamUtils.closeQuietly(inputStream);
			}
			return getConfig();
		}
	}

	public String getConfigName() {
		return this.getClass().getName() + ".properties";
	}

}

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
package org.mobylet.mail.config.impl;

import java.io.InputStream;
import java.util.Properties;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.config.MobyletConfig;
import org.mobylet.core.util.ResourceUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.mail.config.MailMimeConfig;

public class MobyletMailMimeConfig implements MailMimeConfig {

	public static final String CONFIG_PATH = "mobylet.mime.properties";

	protected Properties props;


	public MobyletMailMimeConfig() {
		String configDir =
			SingletonUtils.get(MobyletConfig.class).getConfigDir();
		String configPath = configDir + CONFIG_PATH;
		props = new Properties();
		try {
			InputStream is =
				ResourceUtils.getResourceFileOrInputStream(configPath);
			if (is == null) {
				is = ResourceUtils.getResourceFileOrInputStream(CONFIG_PATH);
			}
			if (is != null) {
				props.load(is);
			}
		} catch (Exception e) {
			throw new MobyletRuntimeException(
					configPath+"の読み込みに失敗しました", e);
		}
	}

	@Override
	public Properties getMimeProperties() {
		return props;
	}
}

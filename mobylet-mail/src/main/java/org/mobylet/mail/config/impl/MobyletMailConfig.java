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
package org.mobylet.mail.config.impl;

import java.io.InputStream;
import java.util.Properties;

import javax.mail.Session;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.config.MobyletConfig;
import org.mobylet.core.util.ResourceUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.mail.MailConstants;
import org.mobylet.mail.config.MailConfig;

public class MobyletMailConfig implements MailConfig {

	public static final String CONFIG_PATH = "mobylet.mail.properties";

	public static final String KEY_HOST = "mobylet.smtp.host";

	public static final String KEY_PORT = "mobylet.smtp.port";

	public static final String KEY_USER = "mobylet.smtp.user";

	public static final String KEY_PASSWORD = "mobylet.smtp.password";


	public static final String DEF_HOST = "localhost";

	public static final String DEF_PORT = "25";

	public static final String DEF_USER = "";

	public static final String DEF_PASS = "";


	protected Properties props;


	public MobyletMailConfig() {
		String configDir =
			SingletonUtils.get(MobyletConfig.class).getConfigDir();
		String configPath = configDir + CONFIG_PATH;
		props = new Properties();
		try {
			InputStream is =
				ResourceUtils.getResourceFileOrInputStream(configPath);
			if (is != null) {
				props.load(is);
			}
		} catch (Exception e) {
			throw new MobyletRuntimeException(
					configPath+"の読み込みに失敗しました", e);
		}
	}

	@Override
	public String getHost() {
		String host = props.getProperty(KEY_HOST);
		if (StringUtils.isEmpty(host)) {
			host = DEF_HOST;
		}
		return host;
	}

	@Override
	public String getPassword() {
		String pass = props.getProperty(KEY_PASSWORD);
		if (StringUtils.isEmpty(pass)) {
			pass = DEF_PASS;
		}
		return pass;
	}

	@Override
	public String getPort() {
		String port = props.getProperty(KEY_PORT);
		if (StringUtils.isEmpty(port)) {
			port = DEF_PORT;
		}
		return port;
	}

	@Override
	public String getUser() {
		String user = props.getProperty(KEY_USER);
		if (StringUtils.isEmpty(user)) {
			user = DEF_USER;
		}
		return user;
	}

	@Override
	public Session createSession() {
		Properties sessionProperties = new Properties();
		//HOST
		String host = getHost();
		sessionProperties.setProperty(MAIL_HOST, host);
		sessionProperties.setProperty(MAIL_SMTP_HOST, host);
		sessionProperties.setProperty(MAIL_SMTP_LOCALHOST, host);
		//PORT
		sessionProperties.setProperty(MAIL_SMTP_PORT, getPort());
		//USER
		sessionProperties.setProperty(MAIL_SMTP_USER, getUser());
		//PASS
		sessionProperties.setProperty(MAIL_SMTP_PASS, getPassword());
		//SMTP-AUTH
		if (StringUtils.isNotEmpty(getUser())) {
			sessionProperties.setProperty(MAIL_SMTP_AUTH, MailConstants.TRUE);
		}
		//Session-Created
		Session session = Session.getInstance(sessionProperties);
		return session;
	}

}

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

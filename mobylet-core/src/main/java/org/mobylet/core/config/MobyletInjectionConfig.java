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

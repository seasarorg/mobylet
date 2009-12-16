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
			injectionConfig = new MobyletProperties();
			MobyletConfig config = SingletonUtils.get(MobyletConfig.class);
			String path = config.getConfigDir() + getConfigName();
			InputStream inputStream = null;
			try {
				inputStream =
					ResourceUtils.getResourceFileOrInputStream(path);
				if (inputStream != null) {
					injectionConfig.load(inputStream);
					MobyletLogger logger = SingletonUtils.get(MobyletLogger.class);
					if (logger != null && logger.isLoggable())
						logger.log("[mobylet] InjectionConfig [" + path + "] が読み込まれました");
				} else {
					MobyletLogger logger = SingletonUtils.get(MobyletLogger.class);
					if (logger != null && logger.isLoggable())
						logger.log("[mobylet] InjectionConfig [" + path + "] は参照しませんでした");
				}
			} catch (Exception e) {
				MobyletLogger logger = SingletonUtils.get(MobyletLogger.class);
				if (logger != null && logger.isLoggable())
					logger.log("[mobylet] InjectionConfig [" + path + "] 参照時に例外発生 = " + e.getMessage());
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

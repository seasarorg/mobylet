package org.mobylet.core.config;

import java.util.Properties;

import org.mobylet.core.config.MobyletConfig;
import org.mobylet.core.util.ResourceUtils;
import org.mobylet.core.util.SingletonUtils;

public abstract class MobyletInjectionConfig {

	protected Properties injectionConfig;

	public Properties getConfig() {
		if (injectionConfig != null) {
			return injectionConfig;
		} else {
			injectionConfig = new Properties();
			MobyletConfig config = SingletonUtils.get(MobyletConfig.class);
			String path = getConfigName();
			try {
				injectionConfig.load(ResourceUtils.getResourceFileOrInputStream(
						config.getConfigDir() + path));
			} catch (Exception e) {
				//NOP
			}
			return getConfig();
		}
	}

	public String getConfigName() {
		return this.getClass().getName() + ".properties";
	}

}

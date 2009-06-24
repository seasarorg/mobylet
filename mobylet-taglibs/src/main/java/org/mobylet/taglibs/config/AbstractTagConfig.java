package org.mobylet.taglibs.config;

import java.util.Properties;

import org.mobylet.core.config.MobyletConfig;
import org.mobylet.core.util.ResourceUtils;
import org.mobylet.core.util.SingletonUtils;

public abstract class AbstractTagConfig {

	protected Properties tagConfig;

	public Properties getConfig() {
		if (tagConfig != null) {
			return tagConfig;
		} else {
			MobyletConfig config = SingletonUtils.get(MobyletConfig.class);
			String path = this.getClass().getName() + ".properties";
			try {
				tagConfig = new Properties();
				tagConfig.load(ResourceUtils.getResourceFileOrInputStream(
						config.getConfigDir() + path));
			} catch (Exception e) {
				//NOP
			}
			return getConfig();
		}
	}

}

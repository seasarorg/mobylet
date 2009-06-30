package org.mobylet.core.image;

import org.mobylet.core.config.MobyletInjectionConfig;


public class ImageConfig extends MobyletInjectionConfig {

	public static final String PKEY_IMGPATH = "img";

	public static final String PKEY_AUTOSCALE = "autoScale";

	public static final String PVAL_AUTOSCALE = "on";

	public static final String PKEY_WIDTH = "w";

	public static final String PKEY_HEIGHT = "h";


	public static final String CONFIG_KEY_LOCAL_BASE_DIR = "image.local.base.dir";

	public static final String CONFIG_KEY_CACHE_BASE_DIR = "image.cache.base.dir";


	public String getLocalBaseDirPath() {
		return getConfig().getProperty(CONFIG_KEY_LOCAL_BASE_DIR);
	}

	public String getCacheBaseDirPath() {
		return getConfig().getProperty(CONFIG_KEY_CACHE_BASE_DIR);
	}

	@Override
	public String getConfigName() {
		return "mobylet.image.properties";
	}

}

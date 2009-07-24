package org.mobylet.core.image;

import org.mobylet.core.config.MobyletInjectionConfig;
import org.mobylet.core.util.StringUtils;


public class ImageConfig extends MobyletInjectionConfig {

	public static final String PKEY_IMGPATH = "img";

	public static final String PKEY_AUTOSCALE = "autoScale";

	public static final String PKEY_SCALETYPE = "scaleType";

	public static final String PVAL_AUTOSCALE = "on";

	public static final String PKEY_WIDTH = "w";


	public static final String CONFIG_KEY_LOCAL_BASE_DIR = "image.local.base.dir";

	public static final String CONFIG_KEY_CACHE_BASE_DIR = "image.cache.base.dir";

	public static final String CONFIG_KEY_SCALE_SERVLET_PATH = "image.scale.servlet.path";

	public static final String CONFIG_KEY_DEFAULT_SCALETYPE = "image.default.scaletype";

	public static final String CONFIG_KEY_DEFAULT_SCALE_IMAGE_WIDTH = "image.default.scale.image.width";


	protected ScaleType defScaleType;


	public String getLocalBaseDirPath() {
		return getConfig().getProperty(CONFIG_KEY_LOCAL_BASE_DIR);
	}

	public String getCacheBaseDirPath() {
		return getConfig().getProperty(CONFIG_KEY_CACHE_BASE_DIR);
	}

	public boolean useScaleServlet() {
		return StringUtils.isNotEmpty(getScaleServletPath());
	}

	public String getScaleServletPath() {
		return getConfig().getProperty(CONFIG_KEY_SCALE_SERVLET_PATH);
	}

	public ScaleType getDefaultScaleType() {
		if (defScaleType == null) {
			String scaleTypeStr =
				getConfig().getProperty(CONFIG_KEY_DEFAULT_SCALETYPE);
			if (StringUtils.isNotEmpty(scaleTypeStr)) {
				defScaleType = ScaleType.valueOf(scaleTypeStr);
				if (defScaleType == null) {
					defScaleType = ScaleType.FITWIDTH;
				}
			} else {
				defScaleType = ScaleType.FITWIDTH;
			}
		}
		return defScaleType;
	}

	public String getDefaultScaleImageWidth() {
		return getConfig().getProperty(CONFIG_KEY_DEFAULT_SCALE_IMAGE_WIDTH);
	}

	@Override
	public String getConfigName() {
		return "mobylet.image.properties";
	}

}

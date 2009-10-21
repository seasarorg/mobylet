package org.mobylet.core.image;

import java.io.File;
import java.util.regex.Pattern;

import org.mobylet.core.config.MobyletInjectionConfig;
import org.mobylet.core.util.StringUtils;


public class ImageConfig extends MobyletInjectionConfig {

	public static final String PKEY_IMGPATH = "img";

	public static final String PKEY_AUTOSCALE = "autoScale";

	public static final String PKEY_SCALETYPE = "scaleType";

	public static final String PVAL_AUTOSCALE = "on";

	public static final String PKEY_WIDTH = "w";


	public static final String CONFIG_KEY_IMAGE_SOURCE = "image.source.type";

	public static final String CONFIG_KEY_LOCAL_BASE_DIR = "image.local.base.dir";

	public static final String CONFIG_KEY_CACHE_BASE_DIR = "image.cache.base.dir";

	public static final String CONFIG_KEY_SCALE_SERVLET_PATH = "image.scale.servlet.path";

	public static final String CONFIG_KEY_DEFAULT_SCALETYPE = "image.default.scaletype";

	public static final String CONFIG_KEY_DEFAULT_SCALE_IMAGE_WIDTH = "image.default.scale.image.width";

	public static final String CONFIG_KEY_IMAGE_SOURCE_ALLOW_URL = "image.source.url.allow";


	public static final String CONFIG_KEY_USE_IMAGEMAGICK = "imagemagick.use";

	public static final String CONFIG_KEY_IMAGEMAGICK_PATH = "imagemagick.path";

	public static final String CONFIG_KEY_IMAGEMAGICK_WORKDIR = "imagemagick.workdir";


	protected ScaleType defScaleType;

	protected ImageSourceType imageSourceType;

	protected boolean isInitializedAllowUrlRegex = false;
	
	protected Pattern allowUrlRegex;
	
	protected String imageMagickPath;

	protected String imageMagickWorkDir;


	public ImageSourceType getImageSourceType() {
		if (imageSourceType == null) {
			try {
				imageSourceType =
					ImageSourceType.valueOf(
							getConfig().getProperty(CONFIG_KEY_IMAGE_SOURCE));
			} catch (Exception e) {
				//NOP
			}
			if (imageSourceType == null) {
				imageSourceType = ImageSourceType.DEFAULT;
			}
		}
		return imageSourceType;
	}

	public Pattern getAllowUrlRegex() {
		if (!isInitializedAllowUrlRegex) {
			try {
				String urlRegex =
					getConfig().getProperty(CONFIG_KEY_IMAGE_SOURCE_ALLOW_URL);
				if (StringUtils.isNotEmpty(urlRegex)) {
					allowUrlRegex = Pattern.compile(urlRegex);
				}
			} catch (Exception e) {
				//NOP
			}
			isInitializedAllowUrlRegex = true;
		}
		return allowUrlRegex;
	}

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

	public boolean useImageMagick() {
		String useImageMagick = getConfig().getProperty(CONFIG_KEY_USE_IMAGEMAGICK);
		return "true".equalsIgnoreCase(useImageMagick);
	}

	public String getImageMagickPath() {
		if (imageMagickPath == null) {
			imageMagickPath = getConfig().getProperty(CONFIG_KEY_IMAGEMAGICK_PATH);
			if (StringUtils.isEmpty(imageMagickPath)) {
				imageMagickPath = "";
			} else if (!imageMagickPath.endsWith(File.separator)) {
				imageMagickPath = imageMagickPath + File.separator;
			}
		}
		return imageMagickPath;
	}
	
	public String getImageMagickWorkDir() {
		if (imageMagickWorkDir == null) {
			imageMagickWorkDir = getConfig().getProperty(CONFIG_KEY_IMAGEMAGICK_WORKDIR);
			if (StringUtils.isEmpty(imageMagickWorkDir)) {
				imageMagickWorkDir = "/tmp/mobylet/work/imagemagick/";
				new File(imageMagickWorkDir).mkdirs();
			} else if (!imageMagickWorkDir.endsWith(File.separator)) {
				imageMagickWorkDir = imageMagickWorkDir + File.separator;
			}
		}
		return imageMagickWorkDir;
	}

	@Override
	public String getConfigName() {
		return "mobylet.image.properties";
	}

}

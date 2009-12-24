package org.mobylet.view.designer;

import org.mobylet.core.image.ImageCodec;
import org.mobylet.core.image.ImageConfig;
import org.mobylet.core.image.ScaleType;
import org.mobylet.core.util.ImageUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.core.util.UrlUtils;

public class ImageDesigner extends SingletonDesigner {

	public static ImageConfig config = SingletonUtils.get(ImageConfig.class);


	public String getSrc(String src, double magniWidth, ScaleType scaleType, ImageCodec codec, boolean useFilter) {
		ScaleType pScaleType = scaleType == null ? ScaleType.FITWIDTH : scaleType;
		Double pMagniWidth = new Double(magniWidth);
		if (useFilter || !config.useScaleServlet()) {
			return useFilter(src, pMagniWidth, pScaleType, codec);
		} else {
			return useServlet(src, pMagniWidth, pScaleType);
		}
	}

	protected String useFilter(String src, Double magniWidth, ScaleType scaleType, ImageCodec codec) {
		if (magniWidth == null) {
			magniWidth = 1.0;
		}
		if (scaleType == null) {
			scaleType = config.getDefaultScaleType();
		}
		if (codec == null) {
			codec = ImageUtils.getImageCodec();
			if (codec == null) {
				return src;
			}
		}
		String imgSrc = src;
		if (StringUtils.isNotEmpty(imgSrc)) {
			imgSrc = UrlUtils.addParameter(
					imgSrc,
					ImageConfig.PKEY_AUTOSCALE,
					ImageConfig.PVAL_AUTOSCALE);
			imgSrc = UrlUtils.addParameter(
					imgSrc,
					ImageConfig.PKEY_WIDTH,
					magniWidth.toString());
			imgSrc = UrlUtils.addParameter(
					imgSrc,
					ImageConfig.PKEY_SCALETYPE,
					scaleType.name());
			imgSrc = UrlUtils.addParameter(
					imgSrc,
					ImageConfig.PKEY_CODEC,
					codec.name());
		}
		return imgSrc;
	}

	protected String useServlet(String src, Double magniWidth, ScaleType scaleType) {
		if (magniWidth == null ||
				scaleType == null) {
			return null;
		}
		String imgSrc = config.getScaleServletPath();
		if (StringUtils.isNotEmpty(imgSrc)) {
			imgSrc = UrlUtils.addParameter(
					imgSrc,
					ImageConfig.PKEY_IMGPATH,
					src);
			imgSrc = UrlUtils.addParameter(
					imgSrc,
					ImageConfig.PKEY_WIDTH,
					magniWidth.toString());
			imgSrc = UrlUtils.addParameter(
					imgSrc,
					ImageConfig.PKEY_SCALETYPE,
					scaleType.name());
		}
		return imgSrc;
	}
}

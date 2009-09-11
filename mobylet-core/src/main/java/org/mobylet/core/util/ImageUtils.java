package org.mobylet.core.util;

import javax.servlet.http.HttpServletRequest;

import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.device.DeviceDisplay;
import org.mobylet.core.image.ImageClipRectangle;
import org.mobylet.core.image.ImageCodec;
import org.mobylet.core.image.ImageConfig;
import org.mobylet.core.image.ScaleType;

public class ImageUtils {

	public static final String CONTENTTYPE_JPEG = "image/jpeg";

	public static final String CONTENTTYPE_GIF = "image/gif";

	public static final String CONTENTTYPE_PNG = "image/png";


	public static String getContentTypeStringByRequestURI() {
		String uri = RequestUtils.get().getRequestURI();
		return getContentTypeString(uri);
	}

	public static String getContentTypeString(String uri) {
		ImageCodec codec = getImageCodec(uri);
		if (codec != null) {
			return getContentTypeString(codec);
		}
		return null;
	}

	public static String getContentTypeString(ImageCodec codec) {
		if (codec == null) {
			return null;
		}
		switch (codec) {
		case JPG:
			return CONTENTTYPE_JPEG;
		case GIF:
			return CONTENTTYPE_GIF;
		case PNG:
			return CONTENTTYPE_PNG;
		default:
			return null;
		}
	}

	public static ImageCodec getImageCodec() {
		String uri = RequestUtils.get().getRequestURI();
		return getImageCodec(uri);
	}

	public static ImageCodec getImageCodec(String uri) {
		if (StringUtils.isNotEmpty(uri)) {
			int index = -1;
			if ((index = uri.indexOf('?')) > 0 ||
					(index = uri.indexOf(';')) > 0) {
				uri = uri.substring(0, index);
			}
			String suffix = uri.substring(uri.lastIndexOf('.')+1, uri.length());
			if (suffix.startsWith("jpg") ||
					suffix.startsWith("jpeg") ||
					suffix.startsWith("JPG") ||
					suffix.startsWith("JPEG")) {
				return ImageCodec.JPG;
			} else if (suffix.startsWith("gif") ||
					suffix.startsWith("GIF")) {
				return ImageCodec.GIF;
			} else if (suffix.startsWith("png") ||
					suffix.startsWith("PNG")) {
				return ImageCodec.PNG;
			}
		}
		return null;
	}

	public static ScaleType getScaleType() {
		return getScaleType(
				RequestUtils.get().getParameter(ImageConfig.PKEY_SCALETYPE));
	}

	public static ScaleType getScaleType(String scaleTypeStr) {
		ImageConfig config = SingletonUtils.get(ImageConfig.class);
		ScaleType scaleType = null;
		if (StringUtils.isNotEmpty(scaleTypeStr)) {
			scaleType = ScaleType.valueOf(scaleTypeStr);
			if (scaleType == null) {
				scaleType = config.getDefaultScaleType();
			}
		} else {
			scaleType = config.getDefaultScaleType();
		}
		return scaleType;
	}

	public static ImageClipRectangle getClipRectangle(
			int width, int height, int newWidth, ScaleType scaleType) {
		if (scaleType == null) {
			new ImageClipRectangle(0, 0, width, height);
		}
		switch (scaleType) {
		case CLIPSQUARE:
			if (width > height) {
				int w = (int)(width * (double)newWidth/(double)height);
				return new ImageClipRectangle(
						(w - newWidth) / 2, 0, w, newWidth);
			} else {
				int h = (int)(height * (double)newWidth/(double)width);
				return new ImageClipRectangle(
						0, (h - newWidth) / 2, newWidth, h);
			}
		case INSQUARE:
			if (width > height) {
				return getClipRectangle(width, height, newWidth, ScaleType.FITWIDTH);
			} else {
				return new ImageClipRectangle(
						0, 0,
						(int)(width * (double)newWidth/(double)height),
						newWidth);
			}
		case FITWIDTH:
			return new ImageClipRectangle(
					0, 0, newWidth,
					(int)(height * (double)newWidth/(double)width));
		}
		return null;
	}

	public static Integer getScaledWidth() {
		Mobylet m = MobyletFactory.getInstance();
		DeviceDisplay dp = m.getDisplay();
		HttpServletRequest request = RequestUtils.get();
		String w = request.getParameter(ImageConfig.PKEY_WIDTH);
		Integer iw = null;
		if (dp != null) {
			if (StringUtils.isNotEmpty(w)) {
				try {
					iw = (int)(Double.valueOf(w) * dp.getWidth());
				} catch (NumberFormatException e) {
					//NOP
				}
			}
		}
		return iw;
	}

	public static boolean isAutoScale() {
		ImageCodec codec = ImageUtils.getImageCodec();
		if (codec != null) {
			HttpServletRequest request = RequestUtils.get();
			if (ImageConfig.PVAL_AUTOSCALE.equalsIgnoreCase(
					request.getParameter(ImageConfig.PKEY_AUTOSCALE)) &&
					StringUtils.isNotEmpty(request.getParameter(ImageConfig.PKEY_WIDTH))) {
				return true;
			}
		}
		return false;
	}

}

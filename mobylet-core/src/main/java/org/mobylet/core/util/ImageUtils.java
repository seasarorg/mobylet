package org.mobylet.core.util;

import javax.servlet.http.HttpServletRequest;

import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.device.DeviceDisplay;
import org.mobylet.core.image.ImageCodec;
import org.mobylet.core.image.ImageConfig;

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

	public static int getScaledWidth() {
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
					(StringUtils.isNotEmpty(request.getParameter(ImageConfig.PKEY_HEIGHT)) ||
							StringUtils.isNotEmpty(request.getParameter(ImageConfig.PKEY_WIDTH)))) {
				return true;
			}
		}
		return false;
	}

}

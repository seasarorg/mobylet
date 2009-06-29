package org.mobylet.core.util;

import javax.servlet.http.HttpServletRequest;

import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.device.DeviceDisplay;
import org.mobylet.core.http.MobyletContext;
import org.mobylet.core.http.image.MobyletImageContentType;
import org.mobylet.core.image.ImageConfig;

public class ImageUtils {

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
		MobyletContext mobyletContext = RequestUtils.getMobyletContext();
		MobyletImageContentType contentType = mobyletContext.get(MobyletImageContentType.class);
		if (contentType != null && contentType.isImage()) {
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

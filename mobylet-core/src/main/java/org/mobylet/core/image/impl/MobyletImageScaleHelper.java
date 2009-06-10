package org.mobylet.core.image.impl;

import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;

import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.device.DeviceDisplay;
import org.mobylet.core.http.MobyletContentType;
import org.mobylet.core.http.MobyletContext;
import org.mobylet.core.image.ImageScaleHelper;
import org.mobylet.core.image.ImageScaler;
import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;

public class MobyletImageScaleHelper implements ImageScaleHelper {

	public static final String PKEY_AUTOSCALE = "autoScale";

	public static final String PVAL_AUTOSCALE = "on";

	public static final String PKEY_WIDTH = "w";

	public static final String PKEY_HEIGHT = "h";


	public boolean isAutoScaleImage() {
		MobyletContext mobyletContext = RequestUtils.getMobyletContext();
		MobyletContentType contentType = mobyletContext.get(MobyletContentType.class);
		if (contentType != null && contentType.isImage()) {
			HttpServletRequest request = RequestUtils.get();
			if (PVAL_AUTOSCALE.equalsIgnoreCase(request.getParameter(PKEY_AUTOSCALE)) &&
					(StringUtils.isNotEmpty(request.getParameter(PKEY_HEIGHT)) ||
							StringUtils.isNotEmpty(request.getParameter(PKEY_WIDTH)))) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void autoScale(ServletOutputStream outStream, InputStream inStream) {
		Mobylet m = MobyletFactory.getInstance();
		DeviceDisplay dp = m.getDisplay();
		HttpServletRequest request = RequestUtils.get();
		String w = request.getParameter(PKEY_WIDTH);
		String h = request.getParameter(PKEY_HEIGHT);
		Integer iw = null;
		Integer ih = null;
		if (StringUtils.isNotEmpty(w)) {
			try {
				iw = (int)(Double.valueOf(w) * dp.getWidth());
			} catch (NumberFormatException e) {
				//NOP
			}
		}
		if (StringUtils.isNotEmpty(h)) {
			try {
				ih = (int)(Double.valueOf(h) * dp.getHeight());
			} catch (NumberFormatException e) {
				//NOP
			}
		}
		SingletonUtils.get(ImageScaler.class).resize(
				inStream,
				outStream,
				RequestUtils.getMobyletContext()
				.get(MobyletContentType.class).getImageCodec(),
				iw,
				ih);
	}

}

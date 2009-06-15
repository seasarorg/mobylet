package org.mobylet.gae.image;
import javax.servlet.http.HttpServletRequest;

import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.device.DeviceDisplay;
import org.mobylet.core.image.ImageScaleConfig;
import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;


public class GaeImageScaleHelper implements ImageScaleConfig {

	public Image getScaledImage(Image in) {
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
		return SingletonUtils.get(GaeImageScaleHelper.class).resize(
				in,
				iw,
				ih);
	}

	public Image resize(Image in, Integer width, Integer height) {
		int imgWidth = in.getWidth();
		int imgHeight = in.getHeight();
		if (width != null && height != null) {
			imgWidth = width;
			imgHeight = height;
		} else if (width != null && height == null) {
			imgHeight = (int)(imgHeight * (double)width/(double)imgWidth);
			imgWidth = width;
		} else if (width == null && height != null) {
			imgWidth = (int)(imgWidth * (double)height/(double)imgHeight);
			imgHeight = height;
		}
		ImagesService imagesService = ImagesServiceFactory.getImagesService();
		Transform transformer = ImagesServiceFactory.makeResize(imgWidth, imgHeight);
		return imagesService.applyTransform(transformer, in);
	}

}

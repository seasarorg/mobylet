package org.mobylet.gae.image;
import java.awt.Dimension;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.image.ImageCodec;
import org.mobylet.core.image.impl.MobyletImageScaler;
import org.mobylet.core.util.InputStreamUtils;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;


public class GaeImageScaler extends MobyletImageScaler {

	@Override
	public void scale(InputStream imgStream, OutputStream outImage,
			ImageCodec type, int newWidth) {
		try {
			//CreateImage
			Image image = ImagesServiceFactory.makeImage(
					InputStreamUtils.getAllBytes(imgStream));
			//CalcNewSize
			Dimension newSize = getNewSize(
					image.getWidth(), image.getHeight(), newWidth);
			int width = (int)newSize.getWidth();
			int height = (int)newSize.getHeight();
			//Transform
			ImagesService imagesService = ImagesServiceFactory.getImagesService();
			Transform transformer = ImagesServiceFactory.makeResize(width, height);
			image = imagesService.applyTransform(transformer, image);
			//Output
			outImage.write(image.getImageData());
		} catch (IOException e) {
			throw new MobyletRuntimeException("画像の出力に失敗しました", e);
		} finally {
			InputStreamUtils.closeQuietly(imgStream);
		}
	}

}

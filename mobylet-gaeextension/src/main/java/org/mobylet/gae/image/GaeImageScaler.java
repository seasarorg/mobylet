package org.mobylet.gae.image;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.image.ImageClipRectangle;
import org.mobylet.core.image.ImageCodec;
import org.mobylet.core.image.ScaleType;
import org.mobylet.core.image.impl.MobyletImageScaler;
import org.mobylet.core.util.InputStreamUtils;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;


public class GaeImageScaler extends MobyletImageScaler {

	@Override
	public void scale(InputStream imgStream, OutputStream outImage,
			ImageCodec type, int newWidth, ScaleType scaleType) {
		try {
			//CreateImage
			Image image = ImagesServiceFactory.makeImage(
					InputStreamUtils.getAllBytes(imgStream));
			//CalcNewSize
			ImageClipRectangle rectangle = getClipRectangle(
					image.getWidth(), image.getHeight(), newWidth, scaleType);
			int width = rectangle.getWidth();
			int height = rectangle.getHeight();
			//Transform
			ImagesService imagesService = ImagesServiceFactory.getImagesService();
			//Resize
			Transform transformer = ImagesServiceFactory.makeResize(width, height);
			image = imagesService.applyTransform(transformer, image);
			//Crop
			if (scaleType == ScaleType.CLIPSQUARE) {
				transformer = ImagesServiceFactory.makeCrop(
						(double)rectangle.getX()/(double)width,
						(double)rectangle.getY()/(double)height,
						(double)(rectangle.getX() + newWidth)/(double)width,
						(double)(rectangle.getY() + newWidth)/(double)height);
				image = imagesService.applyTransform(transformer, image);
			}
			//Output
			outImage.write(image.getImageData());
		} catch (IOException e) {
			throw new MobyletRuntimeException("画像の出力に失敗しました", e);
		} finally {
			InputStreamUtils.closeQuietly(imgStream);
		}
	}

}

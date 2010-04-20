/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.mobylet.gae.image;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.image.ImageClipRectangle;
import org.mobylet.core.image.ImageCodec;
import org.mobylet.core.image.ImageConfig;
import org.mobylet.core.image.ScaleType;
import org.mobylet.core.image.impl.MobyletImageScaler;
import org.mobylet.core.util.InputStreamUtils;
import org.mobylet.core.util.SingletonUtils;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;

public class GaeImageScaler extends MobyletImageScaler {

	@Override
	public void scale(InputStream imgStream, OutputStream outImage,
			ImageCodec type, Integer newWidth, ScaleType scaleType) {
		try {
			//CreateImage
			Image image = ImagesServiceFactory.makeImage(
					InputStreamUtils.getAllBytes(imgStream));
			if (newWidth == null) {
				ImageConfig config = SingletonUtils.get(ImageConfig.class);
				String defWidth = null;
				if ((defWidth = config.getDefaultScaleImageWidth()) != null) {
					newWidth = Integer.parseInt(defWidth);
				} else {
					newWidth = image.getWidth();
				}
			}
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

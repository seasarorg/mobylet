package org.mobylet.core.image;

import java.io.InputStream;
import java.io.OutputStream;

public interface ImageScaler {

	public void scale(InputStream imgStream, OutputStream outImage,
			ImageCodec imageCodec, int newWidth, ScaleType scaleType);

	public ImageClipRectangle getClipRectangle(
			int width, int height, int newWidth, ScaleType scaleType);

}

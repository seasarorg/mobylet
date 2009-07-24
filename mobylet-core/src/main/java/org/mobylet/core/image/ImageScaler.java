package org.mobylet.core.image;

import java.io.InputStream;
import java.io.OutputStream;

public interface ImageScaler {

	public void scale(InputStream imgStream, OutputStream outImage,
			ImageCodec imageCodec, Integer newWidth, ScaleType scaleType);

	public ImageClipRectangle getClipRectangle(
			Integer width, Integer height, Integer newWidth, ScaleType scaleType);

}

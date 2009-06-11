package org.mobylet.core.image;

import java.io.InputStream;

import org.mobylet.core.http.MobyletServletOutputStream;

public interface ImageScaler {

	public int resize(InputStream imgStream, MobyletServletOutputStream scaledImage,
			ImageCodec suffix, Integer width, Integer height);

}

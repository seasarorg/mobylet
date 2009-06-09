package org.mobylet.core.image;

import java.io.InputStream;
import java.io.OutputStream;

public interface ImageScaler {

	public void resize(InputStream imgStream, OutputStream scaledImage,
			ImageCodec suffix, Integer width, Integer height);

}

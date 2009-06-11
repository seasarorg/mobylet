package org.mobylet.core.image;

import java.io.InputStream;

import org.mobylet.core.http.MobyletServletOutputStream;


public interface ImageScaleHelper {

	public boolean isAutoScaleImage();

	public int autoScale(MobyletServletOutputStream outStream, InputStream inStream);

}

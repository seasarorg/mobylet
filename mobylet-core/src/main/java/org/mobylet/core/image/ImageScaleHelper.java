package org.mobylet.core.image;

import java.io.InputStream;

import javax.servlet.ServletOutputStream;


public interface ImageScaleHelper {

	public boolean isAutoScaleImage();

	public void autoScale(ServletOutputStream outStream, InputStream inStream);

}

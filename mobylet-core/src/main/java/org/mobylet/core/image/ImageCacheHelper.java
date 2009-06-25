package org.mobylet.core.image;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface ImageCacheHelper {

	public String getImagePath(File localDir, String path);

	public String getCacheFilePath(HttpServletRequest request, File dir, String path);

	public void writeCacheImage(File dir, String path, byte[] b);

	public void write(HttpServletResponse response, byte[] b);

}

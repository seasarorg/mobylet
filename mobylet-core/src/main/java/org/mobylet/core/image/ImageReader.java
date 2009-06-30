package org.mobylet.core.image;

import java.io.File;
import java.io.InputStream;

public interface ImageReader {

	public File getImageBase();

	public InputStream getStream(String path);

	public File getFile(String path);

}

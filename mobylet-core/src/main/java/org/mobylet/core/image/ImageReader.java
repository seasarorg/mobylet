package org.mobylet.core.image;

import java.io.File;

public interface ImageReader {

	public File getImageBase();

	public String constructPath(String path);

	public ConnectionStream getStream(String path);

	public File getFile(String path);

}

package org.mobylet.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class ResourceUtils {

	public static InputStream getResourceFileOrInputStream(String path)
			throws FileNotFoundException, URISyntaxException {
		if (StringUtils.isEmpty(path)) {
			return null;
		}
		if (path.indexOf(File.separator) > 0) {
			File f = new File(path);
			if (f != null && f.exists() && f.canRead()) {
				return new FileInputStream(f);
			}
		}
		ClassLoader classLoader =
			Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			classLoader = ResourceUtils.class.getClassLoader();
		}
		if (classLoader == null) {
			return null;
		}
		URL resourceUrl = classLoader.getResource(path);
		if (resourceUrl != null) {
			File f = new File(new URI(resourceUrl.toExternalForm()));
			if (f != null && f.exists() && f.canRead()) {
				return new FileInputStream(f);
			}
		}
		return classLoader.getResourceAsStream(path);
	}
}

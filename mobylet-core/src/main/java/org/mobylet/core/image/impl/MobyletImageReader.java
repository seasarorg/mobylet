package org.mobylet.core.image.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.image.ImageConfig;
import org.mobylet.core.image.ImageReader;
import org.mobylet.core.util.HttpUtils;
import org.mobylet.core.util.PathUtils;
import org.mobylet.core.util.ResourceUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;

public class MobyletImageReader implements ImageReader {

	protected File imageBaseUri;

	protected boolean isInitializedUri = false;


	@Override
	public File getImageBase() {
		if (isInitializedUri) {
			return imageBaseUri;
		}
		ImageConfig config = SingletonUtils.get(ImageConfig.class);
		String localBaseDirPath = config.getLocalBaseDirPath();
		if (StringUtils.isNotEmpty(localBaseDirPath)) {
			File localBaseDir = new File(localBaseDirPath);
			if (localBaseDir.exists() &&
					localBaseDir.isDirectory() &&
					localBaseDir.canRead()) {
				imageBaseUri = localBaseDir;
			}
		}
		isInitializedUri = true;
		return getImageBase();
	}

	@Override
	public InputStream getStream(String path) {
		InputStream imageStream = null;
		if (PathUtils.isNetworkPath(path)) {
			HttpURLConnection connection =
				HttpUtils.getHttpUrlConnection(path);
			try {
				imageStream = connection.getInputStream();
			} catch (IOException e) {
				throw new MobyletRuntimeException(
						"ストリームをオープンできません path = " + path, e);
			} finally {
				connection.disconnect();
			}
		} else {
			imageStream =
				ResourceUtils.getResourceFileOrInputStream(path);
		}
		return imageStream;
	}

	@Override
	public File getFile(String path) {
		if (getImageBase() == null) {
			return null;
		}
		return new File(
				getImageBase().getAbsolutePath() + File.separator + path);
	}


}

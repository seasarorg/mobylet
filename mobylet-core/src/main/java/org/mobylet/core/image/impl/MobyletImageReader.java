package org.mobylet.core.image.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.image.ConnectionStream;
import org.mobylet.core.image.ImageConfig;
import org.mobylet.core.image.ImageReader;
import org.mobylet.core.util.HttpUtils;
import org.mobylet.core.util.PathUtils;
import org.mobylet.core.util.ResourceUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.core.util.URLUtils;

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
	public ConnectionStream getStream(String path) {
		InputStream imageStream = null;
		if (PathUtils.isNetworkPath(path)) {
			HttpURLConnection connection =
				HttpUtils.getHttpUrlConnection(path);
			try {
				imageStream = connection.getInputStream();
			} catch (IOException e) {
				throw new MobyletRuntimeException(
						"ストリームをオープンできません path = " + path, e);
			}
			return new ConnectionStream(connection, imageStream);
		} else {
			imageStream =
				ResourceUtils.getResourceFileOrInputStream(path);
		}
		return new ConnectionStream(null, imageStream);
	}

	@Override
	public File getFile(String path) {
		if (getImageBase() == null) {
			return null;
		}
		return new File(
				getImageBase().getAbsolutePath() + File.separator + path);
	}

	@Override
	public String constructPath(String path) {
		if (StringUtils.isEmpty(path)) {
			return path;
		} else if (PathUtils.isNetworkPath(path)) {
			return path;
		}
		File imageBase;
		if ((imageBase = getImageBase()) == null) {
			String currentUrl = URLUtils.getCurrentUrl();
			int index = -1;
			if ((index = currentUrl.lastIndexOf('/')) > 0) {
				currentUrl = currentUrl.substring(0, index+1);
			} else {
				currentUrl = currentUrl + "/";
			}
			return currentUrl + path;
		} else {
			return imageBase.getAbsolutePath() + File.separator + path;
		}
	}


}

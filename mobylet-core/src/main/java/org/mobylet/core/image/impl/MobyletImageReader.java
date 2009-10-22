package org.mobylet.core.image.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.image.ConnectionStream;
import org.mobylet.core.image.ImageConfig;
import org.mobylet.core.image.ImageReader;
import org.mobylet.core.image.ImageSourceType;
import org.mobylet.core.util.HttpUtils;
import org.mobylet.core.util.InputStreamUtils;
import org.mobylet.core.util.PathUtils;
import org.mobylet.core.util.ResourceUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.core.util.UrlUtils;

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
		ImageConfig config = SingletonUtils.get(ImageConfig.class);
		InputStream imageStream = null;
		if (PathUtils.isNetworkPath(path)) {
			//CheckImageSource
			if (config.getImageSourceType() == ImageSourceType.LOCAL) {
				return null;
			}
			//CheckAllowUrl
			if (config.getAllowUrlRegex() != null &&
					!config.getAllowUrlRegex().matcher(path).matches()) {
				return null;
			}
			//CheckLimitSize
			if (config.getNetworkLimitSize() > 0) {
				HttpURLConnection checkConnection = null;
				try {
					checkConnection = HttpUtils.getHttpUrlConnection(path);
					checkConnection.setRequestMethod("HEAD");
					checkConnection.connect();
					if (checkConnection.getContentLength() > config.getNetworkLimitSize()) {
						return null;
					}
				} catch (Exception e) {
					return null;
				} finally {
					if (checkConnection != null) {
						checkConnection.disconnect();
					}
				}
			}
			//GetConnection
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
			//CheckImageSource
			if (config.getImageSourceType() == ImageSourceType.NETWORK) {
				return null;
			}
			imageStream =
				ResourceUtils.getResourceFileOrInputStream(path);
			//CheckLimitSize
			if (config.getLocalLimitSize() > 0) {
				try {
					int size = imageStream.available();
					if (size > config.getLocalLimitSize()) {
						InputStreamUtils.closeQuietly(imageStream);
						return null;
					}
				} catch (IOException e) {
					InputStreamUtils.closeQuietly(imageStream);
					return null;
				}
			}
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
			String currentUrl = UrlUtils.getCurrentUrl();
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

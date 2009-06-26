package org.mobylet.core.image.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.device.DeviceDisplay;
import org.mobylet.core.image.ImageCacheHelper;
import org.mobylet.core.image.ImageScaleConfig;
import org.mobylet.core.util.HttpUtils;
import org.mobylet.core.util.PathUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.core.util.URLUtils;

public class MobyletImageCacheHelper implements ImageCacheHelper {

	public static final String CONJUNCTION_SIZE = "+";

	public static final String CONJUNCTION_DATE = "-";


	@Override
	public String getCacheFilePath(HttpServletRequest request, File dir, String path) {
		Mobylet m = MobyletFactory.getInstance();
		DeviceDisplay display = m.getDisplay();
		if (display != null && StringUtils.isNotEmpty(path)) {
			String cacheFileName = PathUtils.getUniqueFilePath(path);
			String w = request.getParameter(ImageScaleConfig.PKEY_WIDTH);
			String h = request.getParameter(ImageScaleConfig.PKEY_HEIGHT);
			cacheFileName = cacheFileName +
			(StringUtils.isNotEmpty(w) ?
					CONJUNCTION_SIZE + "w" + w + "x" + display.getWidth() : "") +
			(StringUtils.isNotEmpty(h) ?
					CONJUNCTION_SIZE + "h" + h + "x" + display.getHeight() : "");
			if (PathUtils.isNetworkPath(path)) {
				HttpURLConnection connection =
					HttpUtils.getHttpUrlConnection(path);
				try {
					connection.setRequestMethod("HEAD");
				} catch (ProtocolException e) {
					throw new MobyletRuntimeException(
							"このコネクションではHEAD要求が行えません : " +
							connection.getClass().getName(), e);
				}
				cacheFileName = cacheFileName + CONJUNCTION_DATE +
						PathUtils.getUniqueFilePath(
								connection.getHeaderField("Last-Modified"));
				connection.disconnect();
			} else {
				File localImage = new File(path);
				if (localImage.exists()) {
					cacheFileName = cacheFileName +
							CONJUNCTION_DATE + localImage.lastModified();
				}
			}
			return dir.getAbsolutePath() + cacheFileName;
		}
		return null;
	}

	@Override
	public String getImagePath(File localDir, String path) {
		String imagePath = null;
		if (!PathUtils.isNetworkPath(path)) {
			if (localDir == null || !localDir.exists() || !localDir.canRead()) {
				String currentUrl = URLUtils.getCurrentUrl();
				if (StringUtils.isNotEmpty(currentUrl)) {
					currentUrl = currentUrl.substring(0, currentUrl.lastIndexOf('/')+1);
					if (path.startsWith(File.separator)) {
						imagePath = path.substring(1);
					}
					imagePath = currentUrl + path;
				}
				return imagePath;
			} else {
				if (PathUtils.isClimbPath(path)) {
					throw new MobyletRuntimeException(
							"危険なパスが指定されています path = " + path, null);
				}
				if (path.startsWith(File.separator)) {
					imagePath = path.substring(1);
				}
				return localDir.getAbsolutePath() + imagePath;
			}
		}
		return path;
	}

	@Override
	public void write(HttpServletResponse response, byte[] b) {
		if (b == null) {
			return;
		}
		response.setContentLength(b.length);
		try {
			response.getOutputStream().write(b);
			response.getOutputStream().flush();
		} catch (IOException e) {
			throw new MobyletRuntimeException("Imageの表出に失敗", e);
		}
	}

	@Override
	public void writeCacheImage(File dir, String path, byte[] b) {
		Mobylet m = MobyletFactory.getInstance();
		DeviceDisplay display = m.getDisplay();
		if (display != null &&
				StringUtils.isNotEmpty(path) &&
				dir != null && dir.exists()) {
			File cacheFile = new File(dir.getAbsolutePath() + path);
			//既にキャッシュがある場合は終了
			if (cacheFile.exists()) {
				return;
			} else {
				//古いキャッシュの削除
				File[] delFiles = dir.listFiles(
						new GcCacheFileFilter(cacheFile.getAbsolutePath()));
				if (delFiles != null && delFiles.length > 0) {
					for (File delFile : delFiles) {
						delFile.delete();
					}
				}
			}
			try {
				if (cacheFile.createNewFile()) {
					FileOutputStream cacheOutStream = null;
					try {
						cacheOutStream = new FileOutputStream(cacheFile);
						cacheOutStream.write(b);
					} catch (IOException e) {
						throw e;
					} finally {
						if (cacheOutStream != null) {
							try {
								cacheOutStream.close();
							} catch (IOException e) {
								//NOP
							}
						}
					}
				} else {
					throw new MobyletRuntimeException(
							"キャッシュファイルの作成に失敗[false] path = " +
							cacheFile.getAbsolutePath(), null);
				}
			} catch (IOException e) {
				throw new MobyletRuntimeException(
						"キャッシュファイルの作成に失敗 path = " +
						cacheFile.getAbsolutePath(), e);
			}
		}
	}


	public class GcCacheFileFilter implements FilenameFilter {

		public String prefixPath = null;

		public GcCacheFileFilter(String path) {
			if (StringUtils.isNotEmpty(path)) {
				int index = path.lastIndexOf(CONJUNCTION_DATE);
				if (index > 0) {
					prefixPath = path.substring(0, index+1);
				}
			}
		}

		@Override
		public boolean accept(File dir, String name) {
			if (StringUtils.isNotEmpty(prefixPath) &&
					name.startsWith(prefixPath)) {
				return true;
			} else {
				return false;
			}
		}

	}
}

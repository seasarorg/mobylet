package org.mobylet.core.image.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URI;

import javax.servlet.http.HttpServletRequest;

import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.device.DeviceDisplay;
import org.mobylet.core.image.ImageCacheHelper;
import org.mobylet.core.image.ImageConfig;
import org.mobylet.core.image.ImageReader;
import org.mobylet.core.util.HttpUtils;
import org.mobylet.core.util.InputStreamUtils;
import org.mobylet.core.util.OutputStreamUtils;
import org.mobylet.core.util.PathUtils;
import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;

public class MobyletImageCacheHelper implements ImageCacheHelper {

	public static final String CONJUNCTION_SIZE = "+";

	public static final String CONJUNCTION_BSIZE = "x";

	public static final String CONJUNCTION_DATE = "-";


	protected URI cacheBaseUri;

	protected boolean isInitializedUri = false;


	@Override
	public boolean existsCache(String key) {
		URI cacheBase = getCacheBase();
		if (cacheBase == null) {
			return false;
		}
		File cacheBaseDir = new File(cacheBase);
		File cacheFile = new File(
				cacheBaseDir.getAbsolutePath() + File.separator + key);
		return cacheFile.exists() &&
				cacheFile.isFile() &&
				cacheFile.canRead();
	}

	@Override
	public InputStream get(String key) {
		if (!existsCache(key)) {
			return null;
		}
		File cacheFile = getCacheFile(key);
		try {
			return new FileInputStream(cacheFile);
		} catch (FileNotFoundException e) {
			throw new MobyletRuntimeException(
					"File NotFound path = " + cacheFile.getAbsolutePath(), e);
		}
	}

	@Override
	public URI getCacheBase() {
		if (isInitializedUri) {
			return cacheBaseUri;
		}
		ImageConfig config = SingletonUtils.get(ImageConfig.class);
		String cacheBaseDirPath = config.getCacheBaseDirPath();
		if (StringUtils.isNotEmpty(cacheBaseDirPath)) {
			File cacheBaseDir = new File(cacheBaseDirPath);
			if (cacheBaseDir.exists() &&
					cacheBaseDir.isDirectory() &&
					cacheBaseDir.canWrite()) {
				cacheBaseUri = cacheBaseDir.toURI();
			} else if (cacheBaseDir.mkdirs() &&
					cacheBaseDir.canWrite()) {
				cacheBaseUri = cacheBaseDir.toURI();
			}
		}
		isInitializedUri = true;
		return getCacheBase();
	}

	@Override
	public String getCacheKey(String imgPath) {
		HttpServletRequest request = RequestUtils.get();
		Mobylet m = MobyletFactory.getInstance();
		DeviceDisplay display = m.getDisplay();
		//ExistsBrowserSize
		if (display != null &&
				StringUtils.isNotEmpty(imgPath)) {
			String cacheFileName = PathUtils.getUniqueFilePath(imgPath);
			String w = request.getParameter(ImageConfig.PKEY_WIDTH);
			String h = request.getParameter(ImageConfig.PKEY_HEIGHT);
			cacheFileName = cacheFileName +
			(StringUtils.isNotEmpty(w) ?
					CONJUNCTION_SIZE +
					ImageConfig.PKEY_WIDTH + w +
					CONJUNCTION_BSIZE + display.getWidth() : "") +
			(StringUtils.isNotEmpty(h) ?
					CONJUNCTION_SIZE +
					ImageConfig.PKEY_HEIGHT + h +
					CONJUNCTION_BSIZE+ display.getHeight() : "");
			//NetworkPath
			if (PathUtils.isNetworkPath(imgPath)) {
				HttpURLConnection connection =
					HttpUtils.getHttpUrlConnection(imgPath);
				try {
					connection.setRequestMethod("HEAD");
				} catch (ProtocolException e) {
					throw new MobyletRuntimeException(
							"このコネクションではHEAD要求が行えません : " +
							connection.getClass().getName(), e);
				} finally {
					connection.disconnect();
				}
				String uniqueVersionString =
					connection.getHeaderField("Last-Modified");
				if (StringUtils.isEmpty(uniqueVersionString)) {
					uniqueVersionString =
						connection.getHeaderField("Content-Length");
				}
				if (StringUtils.isEmpty(uniqueVersionString)) {
					uniqueVersionString = "1";
				}
				cacheFileName = cacheFileName + CONJUNCTION_DATE +
						PathUtils.getUniqueFilePath(uniqueVersionString);
			}
			//LocalPath
			else {
				ImageReader imageReader = SingletonUtils.get(ImageReader.class);
				File localFile = imageReader.getFile(imgPath);
				if (localFile != null &&
						localFile.exists() &&
						localFile.canRead()) {
					cacheFileName = cacheFileName +
							CONJUNCTION_DATE + localFile.lastModified();
				}
			}
			return cacheFileName;
		}
		return null;
	}

	@Override
	public void put(String key, InputStream imgStream) {
		if (!enableCache()) {
			return;
		}
		Mobylet m = MobyletFactory.getInstance();
		DeviceDisplay display = m.getDisplay();
		if (display != null &&
				StringUtils.isNotEmpty(key)) {
			File cacheFile = getCacheFile(key);
			//既にキャッシュがある場合は終了
			if (cacheFile.exists()) {
				return;
			} else {
				//古いキャッシュの削除
				File[] delFiles = cacheFile.getParentFile().listFiles(
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
						cacheOutStream.write(
								InputStreamUtils.getAllBytes(imgStream));
					} catch (IOException e) {
						throw e;
					} finally {
						OutputStreamUtils.closeQuietly(cacheOutStream);
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

	@Override
	public boolean enableCache() {
		return getCacheBase() != null;
	}

	protected File getCacheFile(String key) {
		return new File(
				new File(getCacheBase()).getAbsolutePath() +
				File.separator + key);
	}


	public class GcCacheFileFilter implements FilenameFilter {

		public String prefixPath = null;

		public GcCacheFileFilter(String path) {
			if (StringUtils.isNotEmpty(path)) {
				int from = path.lastIndexOf(File.separator);
				int until = path.lastIndexOf(CONJUNCTION_DATE);
				if (until > 0) {
					prefixPath = path.substring(from+1, until+1);
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

package org.mobylet.core.http;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.image.ImageCacheHelper;
import org.mobylet.core.image.ImageScaleHelper;
import org.mobylet.core.util.HttpUtils;
import org.mobylet.core.util.PathUtils;
import org.mobylet.core.util.ResourceUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;

public class MobyletImageScaleServlet extends HttpServlet {

	private static final long serialVersionUID = -8330083988206718597L;

	public static final String KEY_IMGPATH = "img";

	public File imageDir = null;

	public File cacheDir = null;


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//画像パスを取得
		String path = req.getParameter(KEY_IMGPATH);
		if (StringUtils.isEmpty(path)) {
			return;
		}
		ImageCacheHelper cacheHelper = SingletonUtils.get(ImageCacheHelper.class);
		//GetRealPath
		path = cacheHelper.getImagePath(imageDir, path);
		//Content-Type
		resp.setContentType(
				MobyletContentType.getContentTypeStringByImageSuffix(path));
		//CacheProcess
		String cacheFilePath = readCacheProcess(req, resp, path, cacheHelper);
		if (StringUtils.isEmpty(cacheFilePath) &&
				cacheDir != null && cacheDir.exists()) {
			return;
		}
		//ReadImage
		ByteArrayOutputStream imageOutStream = readImageProcess(path);
		//Write
		byte[] imageData = imageOutStream.toByteArray();
		cacheHelper.write(resp, imageData);
		//CacheWrite
		cacheHelper.writeCacheImage(cacheDir, cacheFilePath, imageData);
	}

	protected ByteArrayOutputStream readImageProcess(String path) throws IOException {
		//Buffered-Image
		ByteArrayOutputStream imageOutStream =
			new ByteArrayOutputStream(8192);
		MobyletServletOutputStream msos =
			new MobyletServletOutputStream(imageOutStream);
		InputStream imageStream = null;
		HttpURLConnection connection = null;
		//DynamicScaleProcess
		if (PathUtils.isNetworkPath(path)) {
			connection = HttpUtils.getHttpUrlConnection(path);
			imageStream = connection.getInputStream();
		} else {
			imageStream =
				ResourceUtils.getResourceFileOrInputStream(path);
		}
		//Resize-Image
		SingletonUtils.get(ImageScaleHelper.class).autoScale(msos, imageStream);
		//Stream&ConnectionClose
		if (imageStream != null) {
			try {
				imageStream.close();
			} catch (IOException e) {
				//NOP
			}
		}
		if (connection != null) {
			connection.disconnect();
		}
		return imageOutStream;
	}

	protected String readCacheProcess(HttpServletRequest req,
			HttpServletResponse resp, String path, ImageCacheHelper cacheHelper) {
		String cacheFilePath = null;
		if (cacheDir != null && cacheDir.exists()) {
			cacheFilePath = cacheHelper.getCacheFilePath(req, cacheDir, path);
			if (StringUtils.isNotEmpty(cacheFilePath)) {
				File cacheFile = new File(cacheFilePath);
				if (cacheFile.exists() && cacheFile.canRead()) {
					InputStream imageStream = null;
					try {
						imageStream = new FileInputStream(cacheFile);
						byte[] imageBytes = new byte[imageStream.available()];
						imageStream.read(imageBytes);
						//Write
						cacheHelper.write(resp, imageBytes);
					} catch (IOException e) {
						throw new MobyletRuntimeException(
								"キャッシュ画像の表出に失敗 path = " + cacheFilePath, e);
					} finally {
						if (imageStream != null) {
							try {
								imageStream.close();
							} catch (Exception ee) {
								//NOP
							}
						}
					}
					return null;
				}
			}
		}
		return cacheFilePath;
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String baseDir = config.getInitParameter("mobylet.imagescaler.basedir");
		if (StringUtils.isNotEmpty(baseDir)) {
			if (baseDir.endsWith(File.separator)) {
				imageDir = new File(baseDir);
			} else {
				imageDir = new File(baseDir + File.separator);
			}
			if (!this.imageDir.mkdirs()) {
				this.imageDir = null;
			}
		}
		String cacheDir = config.getInitParameter("mobylet.imagescaler.cachedir");
		if (StringUtils.isNotEmpty(cacheDir)) {
			if (cacheDir.endsWith(File.separator)) {
				this.cacheDir = new File(cacheDir);
			} else {
				this.cacheDir = new File(cacheDir + File.separator);
			}
			if (!this.cacheDir.mkdirs()) {
				this.cacheDir = null;
			}
		}
	}


}

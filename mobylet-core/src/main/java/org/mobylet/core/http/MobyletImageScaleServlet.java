package org.mobylet.core.http;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.device.DeviceDisplay;
import org.mobylet.core.image.ImageScaleConfig;
import org.mobylet.core.image.ImageScaleHelper;
import org.mobylet.core.util.HttpUtils;
import org.mobylet.core.util.PathUtils;
import org.mobylet.core.util.ResourceUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.core.util.URLUtils;

public class MobyletImageScaleServlet extends HttpServlet {

	private static final long serialVersionUID = -8330083988206718597L;

	public static final String KEY_IMGPATH = "img";

	public String imageDir = null;

	public String cacheDir = null;


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//画像パスを取得
		String path = req.getParameter(KEY_IMGPATH);
		if (StringUtils.isEmpty(path)) {
			return;
		}
		//SecurityCheck
		boolean isNetworkPath = PathUtils.isNetworkPath(path);
		if (!isNetworkPath) {
			if (StringUtils.isEmpty(imageDir)) {
				String currentUrl = URLUtils.getCurrentUrl();
				if (StringUtils.isNotEmpty(currentUrl)) {
					currentUrl = currentUrl.substring(0, currentUrl.lastIndexOf('/')+1);
					if (path.startsWith(File.separator)) {
						path = path.substring(1);
					}
					path = currentUrl + path;
				} else {
					return;
				}
				isNetworkPath = true;
			} else {
				if (PathUtils.isClimbPath(path)) {
					throw new MobyletRuntimeException(
							"危険なパスが指定されています path = " + path, null);
				}
				if (path.startsWith(File.separator)) {
					path = path.substring(1);
				}
				path = imageDir + path;
			}
		}
		//Content-Type
		resp.setContentType(
				MobyletContentType.getContentTypeStringByImageSuffix(path));
		//ImageStream
		InputStream imageStream = null;
		HttpURLConnection connection = null;
		//Buffered-Image
		ByteArrayOutputStream imageOutStream =
			new ByteArrayOutputStream(8192);
		MobyletServletOutputStream msos =
			new MobyletServletOutputStream(imageOutStream);
		//CacheProcess
		File cacheFile = null;
		Mobylet m = MobyletFactory.getInstance();
		DeviceDisplay display = m.getDisplay();
		if (StringUtils.isNotEmpty(cacheDir) &&
				display != null) {
			String cacheFileName = PathUtils.getUniqueFilePath(path);
			String w = req.getParameter(ImageScaleConfig.PKEY_WIDTH);
			String h = req.getParameter(ImageScaleConfig.PKEY_HEIGHT);
			if (isNetworkPath) {
				connection =
					HttpUtils.getHttpUrlConnection(path);
				connection.setRequestMethod("HEAD");
				cacheFileName = cacheFileName + "+" +
						PathUtils.getUniqueFilePath(
								connection.getHeaderField("Last-Modified"));
				connection.disconnect();
			} else {
				File localImage = new File(path);
				if (localImage.exists()) {
					cacheFileName = cacheFileName + "+" + localImage.lastModified();
				}
			}
			cacheFileName = cacheFileName +
				(StringUtils.isNotEmpty(w) ?
						"w" + w + "x" + display.getWidth() : "") +
				(StringUtils.isNotEmpty(h) ?
						"h" + h + "x" + display.getHeight() : "");
			cacheFile = new File(cacheDir + cacheFileName);
			if (cacheFile != null &&
					cacheFile.exists() &&
					cacheFile.canRead()) {
				imageStream = new FileInputStream(cacheFile);
				byte[] imageBytes = new byte[imageStream.available()];
				imageStream.read(imageBytes);
				//Write
				resp.setContentLength(imageBytes.length);
				resp.getOutputStream().write(imageBytes);
				resp.getOutputStream().flush();
				//Close&Return
				imageStream.close();
				return;
			}
		}
		//DynamicScaleProcess
		if (isNetworkPath) {
			connection =
				HttpUtils.getHttpUrlConnection(path);
			imageStream = connection.getInputStream();
		} else {
			imageStream =
				ResourceUtils.getResourceFileOrInputStream(path);
		}
		//Resize-Image
		int length =
			SingletonUtils.get(ImageScaleHelper.class).autoScale(
				msos, imageStream);
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
		//Write
		resp.setContentLength(length);
		resp.getOutputStream().write(imageOutStream.toByteArray());
		resp.getOutputStream().flush();
		//CacheWrite
		if (display != null &&
				new File(cacheDir).exists() &&
				cacheFile != null &&
				!cacheFile.exists()) {
			if (cacheFile.createNewFile()) {
				FileOutputStream cacheOutStream = null;
				try {
					cacheOutStream = new FileOutputStream(cacheFile);
					cacheOutStream.write(imageOutStream.toByteArray());
				} catch (IOException e) {
					//キャッシュファイルの作成に失敗
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
				//ファイル作成に失敗
			}
		}
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String baseDir = config.getInitParameter("mobylet.imagescaler.basedir");
		if (StringUtils.isNotEmpty(baseDir)) {
			if (baseDir.endsWith(File.separator)) {
				imageDir = baseDir;
			} else {
				imageDir = baseDir + File.separator;
			}
		}
		String cacheDir = config.getInitParameter("mobylet.imagescaler.cachedir");
		if (StringUtils.isNotEmpty(cacheDir)) {
			if (cacheDir.endsWith(File.separator)) {
				this.cacheDir = cacheDir;
			} else {
				this.cacheDir = cacheDir + File.separator;
			}
		}
	}


}

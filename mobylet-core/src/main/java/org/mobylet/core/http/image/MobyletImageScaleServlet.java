package org.mobylet.core.http.image;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mobylet.core.http.MobyletServletOutputStream;
import org.mobylet.core.image.ImageCacheHelper;
import org.mobylet.core.image.ImageCodec;
import org.mobylet.core.image.ImageConfig;
import org.mobylet.core.image.ImageReader;
import org.mobylet.core.image.ImageScaler;
import org.mobylet.core.util.ImageUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;

public class MobyletImageScaleServlet extends HttpServlet {

	private static final long serialVersionUID = -8330083988206718597L;

	public File imageDir = null;

	public File cacheDir = null;


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//GetPath
		String path = req.getParameter(ImageConfig.PKEY_IMGPATH);
		if (StringUtils.isEmpty(path)) {
			return;
		}
		//GetImageCodec
		ImageCodec codec = ImageUtils.getImageCodec(path);
		if (codec == null) {
			return;
		}
		//Helpers
		ImageReader imageReader = SingletonUtils.get(ImageReader.class);
		ImageScaler imageScaler = SingletonUtils.get(ImageScaler.class);
		ImageCacheHelper cacheHelper = SingletonUtils.get(ImageCacheHelper.class);
		boolean enableCache = cacheHelper.enableCache();
		//---------------------------------------------------------------------
		// Read-Image
		//---------------------------------------------------------------------
		//ImageInputStream
		InputStream imageStream;
		OutputStream outStream;
		//CacheKey
		String key = null;
		//EnableCache
		if (enableCache) {
			//GetKey
			key = cacheHelper.getCacheKey(path);
			//ExsistsCache
			if (cacheHelper.existsCache(key)) {
				imageStream = cacheHelper.get(key);
			}
			//NotExistsCache
			else {
				imageStream = imageReader.getStream(path);
			}
			outStream = new ByteArrayOutputStream(4096);
		}
		//UnEnableCache
		else {
			imageStream = imageReader.getStream(path);
			outStream = new MobyletServletOutputStream(resp.getOutputStream());
		}
		//---------------------------------------------------------------------
		// Set-ContentType
		//---------------------------------------------------------------------
		resp.setContentType(ImageUtils.getContentTypeString(codec));
		//---------------------------------------------------------------------
		// Convert-Image
		//---------------------------------------------------------------------
		imageScaler.scale(
				imageStream,
				outStream,
				codec,
				ImageUtils.getScaledWidth());
		//---------------------------------------------------------------------
		// WriteResponse
		//---------------------------------------------------------------------
		byte[] imageBytes = null;
		if (outStream instanceof ByteArrayOutputStream) {
			imageBytes =
				ByteArrayOutputStream.class.cast(outStream).toByteArray();
			if (imageBytes != null) {
				resp.setContentLength(imageBytes.length);
				resp.getOutputStream().write(imageBytes);
			}
			resp.flushBuffer();
			// WriteCache
			if (enableCache &&
					imageBytes != null &&
					StringUtils.isNotEmpty(key)) {
				cacheHelper.put(key, new ByteArrayInputStream(imageBytes));
			}
		} else if (outStream instanceof MobyletServletOutputStream) {
			MobyletServletOutputStream msos =
				MobyletServletOutputStream.class.cast(outStream);
			resp.setContentLength(msos.getLength());
			resp.flushBuffer();
		}
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String baseDir = config.getInitParameter("mobylet.imagescaler.basedir");
		if (StringUtils.isNotEmpty(baseDir)) {
			imageDir = new File(baseDir);
			if (!imageDir.exists() && !imageDir.mkdirs()) {
				imageDir = null;
			}
		}
		String cacheDir = config.getInitParameter("mobylet.imagescaler.cachedir");
		if (StringUtils.isNotEmpty(cacheDir)) {
			this.cacheDir = new File(cacheDir);
			if (!this.cacheDir.exists() && !this.cacheDir.mkdirs()) {
				this.cacheDir = null;
			}
		}
	}


}

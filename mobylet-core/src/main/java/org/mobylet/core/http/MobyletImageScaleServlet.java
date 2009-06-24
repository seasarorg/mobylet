package org.mobylet.core.http;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.image.ImageScaleHelper;
import org.mobylet.core.util.PathUtils;
import org.mobylet.core.util.ResourceUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.core.util.URLUtils;

public class MobyletImageScaleServlet extends HttpServlet {

	private static final long serialVersionUID = -8330083988206718597L;

	public static final String KEY_IMGPATH = "img";

	public String imageDir = null;


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
		//Resize-Image
		ByteArrayOutputStream imageOutStream =
			new ByteArrayOutputStream(8192);
		MobyletServletOutputStream msos =
			new MobyletServletOutputStream(imageOutStream);
		int length =
			SingletonUtils.get(ImageScaleHelper.class).autoScale(
				msos, ResourceUtils.getResourceFileOrInputStream(path));
		//Write
		resp.setContentLength(length);
		resp.getOutputStream().write(imageOutStream.toByteArray());
		resp.getOutputStream().flush();
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
	}


}

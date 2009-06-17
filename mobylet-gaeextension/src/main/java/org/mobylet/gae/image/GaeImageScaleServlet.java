package org.mobylet.gae.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.http.MobyletContentType;
import org.mobylet.core.util.StringUtils;

import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesServiceFactory;

public class GaeImageScaleServlet extends HttpServlet {

	private static final long serialVersionUID = -776458083694815211L;

	public static final String KEY_IMGPATH = "img";

	public static final GaeImageScaleHelper scaleHelper = new GaeImageScaleHelper();


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//画像パスを取得
		String path = req.getParameter(KEY_IMGPATH);
		if (StringUtils.isEmpty(path)) {
			return;
		} else {
			if (path.startsWith("/")) {
				path = path.substring(1);
			}
		}
		//Content-Type
		resp.setContentType(
				MobyletContentType.getContentTypeStringByImageSuffix(path));
		//Read-Image-File
		FileInputStream fis = null;
		byte[] imageData = null;
		try {
			fis = new FileInputStream(new File(path));
			imageData = new byte[fis.available()];
			fis.read(imageData);
		} catch (Exception e) {
			throw new MobyletRuntimeException("画像パス取得時にエラー : path = " + path, e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					//NOP
				}
			}
		}
		//Resize-Image
		Image image = scaleHelper.getScaledImage(ImagesServiceFactory.makeImage(imageData));
		imageData = image.getImageData();
		//Write
		resp.setContentLength(imageData.length);
		resp.getOutputStream().write(imageData);
		resp.getOutputStream().flush();
	}
}

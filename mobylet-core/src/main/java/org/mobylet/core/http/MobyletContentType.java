package org.mobylet.core.http;

import org.mobylet.core.image.ImageCodec;
import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.StringUtils;

public class MobyletContentType {

	protected String contentType;

	public static String getContentTypeStringByImageSuffix() {
		String uri = RequestUtils.get().getRequestURI();
		if (StringUtils.isNotEmpty(uri)) {
			String suffix = uri.substring(uri.lastIndexOf('.')+1, uri.length());
			if (suffix.startsWith("jpg") ||
					suffix.startsWith("jpeg") ||
					suffix.startsWith("JPG") ||
					suffix.startsWith("JPEG")) {
				return "image/jpeg";
			} else if (suffix.startsWith("gif") ||
					suffix.startsWith("GIF")) {
				return "image/gif";
			} else if (suffix.startsWith("png") ||
					suffix.startsWith("PNG")) {
				return "image/png";
			}
		}
		return null;
	}

	public MobyletContentType(String contentType) {
		this.contentType = contentType;
	}

	public boolean isImage() {
		if (StringUtils.isEmpty(contentType)) {
			return false;
		}
		return contentType.startsWith("image/");
	}

	public ImageCodec getImageCodec() {
		if (!isImage()) {
			return null;
		}
		String subContentType = contentType.substring(6);
		if (subContentType.startsWith("jpeg") ||
				subContentType.startsWith("jpg")) {
			return ImageCodec.JPG;
		} else if (subContentType.startsWith("gif")){
			return ImageCodec.GIF;
		} else if (subContentType.startsWith("png") ||
				subContentType.startsWith("png")) {
			return ImageCodec.PNG;
		}
		return null;
	}

}

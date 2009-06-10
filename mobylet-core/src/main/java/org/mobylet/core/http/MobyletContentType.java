package org.mobylet.core.http;

import org.mobylet.core.image.ImageCodec;
import org.mobylet.core.util.StringUtils;

public class MobyletContentType {

	protected String contentType;

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

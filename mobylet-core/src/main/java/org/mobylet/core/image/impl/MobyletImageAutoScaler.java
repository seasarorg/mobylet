package org.mobylet.core.image.impl;

import javax.servlet.http.HttpServletRequest;

import org.mobylet.core.http.MobyletContentType;
import org.mobylet.core.http.MobyletContext;
import org.mobylet.core.image.ImageAutoScaler;
import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.StringUtils;

public class MobyletImageAutoScaler implements ImageAutoScaler {

	public boolean isAutoScaleImage() {
		MobyletContext mobyletContext = RequestUtils.getMobyletContext();
		MobyletContentType contentType = mobyletContext.get(MobyletContentType.class);
		if (contentType != null && contentType.isImage()) {
			HttpServletRequest request = RequestUtils.get();
			if ("on".equalsIgnoreCase(request.getParameter("autoScale")) &&
					(StringUtils.isNotEmpty(request.getParameter("h")) ||
							StringUtils.isNotEmpty(request.getParameter("w")))) {
				return true;
			}
		}
		return false;
	}

}

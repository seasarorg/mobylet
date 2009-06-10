package org.mobylet.taglibs.impl;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.mobylet.core.image.impl.MobyletImageScaleHelper;
import org.mobylet.core.util.StringUtils;
import org.mobylet.taglibs.MobyletDynamicSimpleTagSupport;
import org.mobylet.taglibs.utils.JspWriterUtils;
import org.mobylet.taglibs.utils.UrlUtils;

public class AutoScaleImageTag extends MobyletDynamicSimpleTagSupport {

	public static final String TAG = "img";

	protected String src = "";

	protected String magniWidth = "";

	protected String magniHeight = "";


	@Override
	public void doTag() throws JspException, IOException {
		String imgSrc = src;
		if (StringUtils.isNotEmpty(imgSrc)) {
			imgSrc = UrlUtils.addParameter(
					imgSrc,
					MobyletImageScaleHelper.PKEY_AUTOSCALE,
					MobyletImageScaleHelper.PVAL_AUTOSCALE);
			imgSrc = UrlUtils.addParameter(
					imgSrc,
					MobyletImageScaleHelper.PKEY_WIDTH,
					magniWidth);
			imgSrc = UrlUtils.addParameter(
					imgSrc,
					MobyletImageScaleHelper.PKEY_HEIGHT,
					magniHeight);
		}
		addAttribute("src", imgSrc);
		JspWriterUtils.write(
				getJspContext().getOut(),
				STAG + TAG + getDynamicAttributesStringBuilder().toString() + ETAG);

	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getMagniWidth() {
		return magniWidth;
	}

	public void setMagniWidth(String magniWidth) {
		this.magniWidth = magniWidth;
	}

	public String getMagniHeight() {
		return magniHeight;
	}

	public void setMagniHeight(String magniHeight) {
		this.magniHeight = magniHeight;
	}

}

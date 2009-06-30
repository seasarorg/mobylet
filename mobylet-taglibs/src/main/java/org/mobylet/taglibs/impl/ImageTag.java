package org.mobylet.taglibs.impl;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.mobylet.core.image.ImageConfig;
import org.mobylet.core.util.StringUtils;
import org.mobylet.taglibs.MobyletDynamicSimpleTagSupport;
import org.mobylet.taglibs.config.ImageTagConfig;
import org.mobylet.taglibs.utils.JspWriterUtils;
import org.mobylet.taglibs.utils.UrlUtils;

public class ImageTag extends MobyletDynamicSimpleTagSupport {

	public static final String TAG = "img";

	public static ImageTagConfig config = new ImageTagConfig();


	protected String src = "";

	protected String magniWidth = "";

	protected String magniHeight = "";


	@Override
	public void doTag() throws JspException, IOException {
		if(config.useServlet()) {
			useServlet();
		} else {
			useFilter();
		}
	}

	protected void useFilter() {
		String imgSrc = src;
		if (StringUtils.isNotEmpty(imgSrc)) {
			imgSrc = UrlUtils.addParameter(
					imgSrc,
					ImageConfig.PKEY_AUTOSCALE,
					ImageConfig.PVAL_AUTOSCALE);
			imgSrc = UrlUtils.addParameter(
					imgSrc,
					ImageConfig.PKEY_WIDTH,
					magniWidth);
			imgSrc = UrlUtils.addParameter(
					imgSrc,
					ImageConfig.PKEY_HEIGHT,
					magniHeight);
		}
		addAttribute("src", imgSrc);
		JspWriterUtils.write(
				getJspContext().getOut(),
				STAG + TAG + getDynamicAttributesStringBuilder().toString() + ETAG);
	}

	protected void useServlet() {
		String imgSrc = config.getServletPath();
		if (StringUtils.isNotEmpty(imgSrc)) {
			imgSrc = UrlUtils.addParameter(
					imgSrc,
					ImageConfig.PKEY_IMGPATH,
					src);
			imgSrc = UrlUtils.addParameter(
					imgSrc,
					ImageConfig.PKEY_WIDTH,
					magniWidth);
			imgSrc = UrlUtils.addParameter(
					imgSrc,
					ImageConfig.PKEY_HEIGHT,
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

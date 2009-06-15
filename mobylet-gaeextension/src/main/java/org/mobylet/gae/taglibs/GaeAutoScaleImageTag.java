package org.mobylet.gae.taglibs;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.mobylet.core.image.ImageScaleConfig;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.gae.image.GaeImageScaleServlet;
import org.mobylet.gae.taglibs.config.GaeAutoScaleImageTagConfig;
import org.mobylet.taglibs.MobyletDynamicSimpleTagSupport;
import org.mobylet.taglibs.utils.JspWriterUtils;
import org.mobylet.taglibs.utils.UrlUtils;

public class GaeAutoScaleImageTag extends MobyletDynamicSimpleTagSupport {

	public static final String TAG = "img";

	public static final GaeAutoScaleImageTagConfig config =
		(SingletonUtils.get(GaeAutoScaleImageTagConfig.class) != null ?
				SingletonUtils.get(GaeAutoScaleImageTagConfig.class) :
					new GaeAutoScaleImageTagConfig());

	protected String src = "";

	protected String magniWidth = "";

	protected String magniHeight = "";


	@Override
	public void doTag() throws JspException, IOException {
		String imgSrc = config.getServletPath();
		imgSrc = UrlUtils.addParameter(
				imgSrc,
				GaeImageScaleServlet.KEY_IMGPATH,
				src);
		imgSrc = UrlUtils.addParameter(
				imgSrc,
				ImageScaleConfig.PKEY_WIDTH,
				magniWidth);
		imgSrc = UrlUtils.addParameter(
				imgSrc,
				ImageScaleConfig.PKEY_HEIGHT,
				magniHeight);
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

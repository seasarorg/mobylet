package org.mobylet.taglibs.impl;

import javax.servlet.jsp.JspException;

import org.mobylet.core.util.StringUtils;
import org.mobylet.taglibs.utils.JspWriterUtils;
import org.mobylet.view.design.GpsDesign;
import org.mobylet.view.design.TagAttribute;
import org.mobylet.view.designer.GpsDesigner;

public class GpsTag extends ATag {

	private static final long serialVersionUID = -4285604125713972051L;

	protected String kickBackUrl;

	protected boolean useEasySurveying = true;


	@Override
	public int doStartTag() throws JspException {
		GpsDesigner designer = GpsDesigner.getDesigner();
		GpsDesign gpsDesign = designer.getGpsDesign(kickBackUrl);
		if (gpsDesign == null ||
				StringUtils.isEmpty(gpsDesign.getUrl())) {
			return EVAL_BODY_BUFFERED;
		}
		addAttribute("href", gpsDesign.getUrl());
		TagAttribute attribute = gpsDesign.getTagAttribute();
		if (attribute != null) {
			addAttribute(attribute.getName(), attribute.getValue());
		}
		JspWriterUtils.write(
				pageContext.getOut(),
				STAG + TAG + getDynamicAttributesStringBuilder().toString() + ETAG);
		return EVAL_BODY_BUFFERED;
	}

	@Override
	public int doEndTag() throws JspException {
		recycle();
		return super.doEndTag();
	}

	public String getKickBackUrl() {
		return kickBackUrl;
	}

	public void setKickBackUrl(String kickBackUrl) {
		this.kickBackUrl = kickBackUrl;
	}

}

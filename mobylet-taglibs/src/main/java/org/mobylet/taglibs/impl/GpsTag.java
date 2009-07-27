package org.mobylet.taglibs.impl;

import javax.servlet.jsp.JspException;

import org.mobylet.core.util.StringUtils;
import org.mobylet.taglibs.utils.JspWriterUtils;
import org.mobylet.view.design.GpsDesign;
import org.mobylet.view.design.TagAttribute;
import org.mobylet.view.designer.GpsDesigner;
import org.mobylet.view.designer.SingletonDesigner;

public class GpsTag extends ATag {

	private static final long serialVersionUID = -4285604125713972051L;

	protected String kickBackUrl;

	protected boolean useEasySurveying = true;


	@Override
	public int doStartTag() throws JspException {
		try {
			GpsDesigner designer =
				SingletonDesigner.getDesigner(GpsDesigner.class);
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
		} catch (Exception e) {
			throw new JspException(e);
		}
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			recycle();
			return super.doEndTag();
		} catch (Exception e) {
			throw new JspException(e);
		}
	}

	public String getKickBackUrl() {
		return kickBackUrl;
	}

	public void setKickBackUrl(String kickBackUrl) {
		this.kickBackUrl = kickBackUrl;
	}

}

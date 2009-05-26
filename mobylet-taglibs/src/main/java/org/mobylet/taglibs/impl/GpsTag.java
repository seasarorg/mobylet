package org.mobylet.taglibs.impl;

import javax.servlet.jsp.JspException;

import org.mobylet.core.Carrier;
import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.util.StringUtils;
import org.mobylet.taglibs.Attribute.EmptyValue;
import org.mobylet.taglibs.utils.JspWriterUtils;
import org.mobylet.taglibs.utils.UrlUtils;

public class GpsTag extends ATag {

	private static final long serialVersionUID = -4285604125713972051L;

	protected String kickBackUrl;

	protected boolean useEasySurveying = true;


	@Override
	public int doStartTag() throws JspException {
		Mobylet m = MobyletFactory.getInstance();
		if (m.getCarrier() == Carrier.OTHER) {
			return EVAL_BODY_BUFFERED;
		}
		//GPS情報取得URL
		if (StringUtils.isNotEmpty(kickBackUrl) &&
				m.getDevice() != null &&
				m.getDevice().hasGps()) {
			addAttribute("href", getGpsUrl(kickBackUrl));
			if (m.getCarrier() == Carrier.DOCOMO) {
				addAttribute("lcs", new EmptyValue());
			}
		}
		//簡易位置取得URL
		else if (StringUtils.isNotEmpty(kickBackUrl) &&
				isUseEasySurveying()) {
			addAttribute("href", getEasyGpsUrl(kickBackUrl));
		}
		JspWriterUtils.write(
				pageContext.getOut(),
				STAG + TAG + getDynamicAttributesStringBuilder().toString() + ETAG);
		return EVAL_BODY_BUFFERED;
	}

	protected String getGpsUrl(String url) {
		url = constructUrl(url);
		switch (MobyletFactory.getInstance().getCarrier()) {
		case DOCOMO:
			return constructUrl(url);
		case AU:
			url = url + UrlUtils.AMP + "var=1&datum=0&unit=0&acry=0&number=0";
			return "device:gpsone?url=" + url.replace('?', '&');
		case SOFTBANK:
			return "location:gps?url=" + url.replace('?', '&');
		}
		return "";
	}

	protected String getEasyGpsUrl(String url) {
		url = constructUrl(url);
		switch (MobyletFactory.getInstance().getCarrier()) {
		case DOCOMO:
			return "http://w1m.docomo.ne.jp/cp/iarea?" +
				"ecode=OPENAREACODE&msn=OPENAREAKEY&posinfo=2" +
				"&nl=" + url.replace('?', '&').replaceAll("[=]", "%3d");
		case AU:
			return "device:location?url=" + url.replace('?', '&');
		case SOFTBANK:
			return "location:auto?url=" + url.replace('?', '&');
		}
		return "";
	}

	public String getKickBackUrl() {
		return kickBackUrl;
	}

	public void setKickBackUrl(String kickBackUrl) {
		this.kickBackUrl = kickBackUrl;
	}

	public boolean isUseEasySurveying() {
		return useEasySurveying;
	}

	public void setUseEasySurveying(boolean useEasySurveying) {
		this.useEasySurveying = useEasySurveying;
	}

}

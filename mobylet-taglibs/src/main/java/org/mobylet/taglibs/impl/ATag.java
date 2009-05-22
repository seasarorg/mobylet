package org.mobylet.taglibs.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;

import org.mobylet.core.Carrier;
import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.taglibs.MobyletDynamicBodyTagSupport;
import org.mobylet.taglibs.config.ATagConfig;
import org.mobylet.taglibs.utils.JspWriterUtils;
import org.mobylet.taglibs.utils.UrlUtils;

public class ATag extends MobyletDynamicBodyTagSupport {

	private static final long serialVersionUID = 6951351713553308618L;

	public static final String TAG = "a";

	public static final ATagConfig config =
		(SingletonUtils.get(ATagConfig.class) != null ?
				SingletonUtils.get(ATagConfig.class) : new ATagConfig());

	protected String href;

	protected boolean isUidQueryRequired = config.isUidQueryRequired();

	protected boolean isGuidQueryRequired = config.isGuidQueryRequired();

	protected boolean isSessionQueryRequired = config.isSessionQueryRequired();

	protected boolean isUidOrGuidQueryRequiredInSecure =
		config.isUidOrGuidQueryRequiredInSecure();


	@Override
	public int doStartTag() throws JspException {
		if (href == null) {
			href = "";
		}
		Mobylet m = MobyletFactory.getInstance();
		HttpServletRequest request = RequestUtils.get();
		//SessionQuery
		if (isSessionQueryRequired()) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				href = UrlUtils.addSession(href, session.getId());
			}
		}
		//SecureQuery
		if (isUidOrGuidQueryRequiredInSecure() &&
				m.getCarrier() == Carrier.DOCOMO &&
				(href.startsWith("https:") ||
						(!href.startsWith("http:") && request.isSecure()))) {
			String id = m.getGuid();
			if (StringUtils.isEmpty(id)) {
				id = m.getUid();
				if (StringUtils.isNotEmpty(id)) {
					href = UrlUtils.addParameter(href, "uid", id);
				}
			}
		//UidQuery
		} else if (isUidQueryRequired() &&
				m.getCarrier() == Carrier.DOCOMO &&
				(href.startsWith("http:") ||
						(!href.startsWith("https:") && !request.isSecure()))) {
			href = UrlUtils.addParameter(href, "uid", "NULLGWDOCOMO");
		//GuidQuery
		} else if (isGuidQueryRequired() &&
				m.getCarrier() == Carrier.DOCOMO &&
				(href.startsWith("http:") ||
						(!href.startsWith("https:") && !request.isSecure()))) {
			href = UrlUtils.addParameter(href, "guid", "ON");
		}
		//URL
		addAttribute("href", href);
		JspWriterUtils.write(
				pageContext.getOut(),
				STAG + TAG + getDynamicAttributesStringBuilder().toString() + ETAG);
		//BodyBuffered
		return EVAL_BODY_BUFFERED;
	}

	@Override
	public int doEndTag() throws JspException {
		JspWriterUtils.write(
				pageContext.getOut(),
				getBodyContent().getString() + STAG + SL + TAG + ETAG);
		return EVAL_PAGE;
	}


	public boolean isUidQueryRequired() {
		return isUidQueryRequired;
	}

	public void setUidQueryRequired(boolean isUidQueryRequired) {
		this.isUidQueryRequired = isUidQueryRequired;
	}

	public boolean isGuidQueryRequired() {
		return isGuidQueryRequired;
	}

	public void setGuidQueryRequired(boolean isGuidQueryRequired) {
		this.isGuidQueryRequired = isGuidQueryRequired;
	}

	public boolean isSessionQueryRequired() {
		return isSessionQueryRequired;
	}

	public void setSessionQueryRequired(boolean isSessionQueryRequired) {
		this.isSessionQueryRequired = isSessionQueryRequired;
	}

	public boolean isUidOrGuidQueryRequiredInSecure() {
		return isUidOrGuidQueryRequiredInSecure;
	}

	public void setUidOrGuidQueryRequiredInSecure(
			boolean isUidOrGuidQueryRequiredInSecure) {
		this.isUidOrGuidQueryRequiredInSecure = isUidOrGuidQueryRequiredInSecure;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}
}

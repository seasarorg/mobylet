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

	protected boolean isSessionCookiePriority = config.isSessionCookiePriority();

	protected boolean isUidOrGuidQueryRequiredInSecure =
		config.isUidOrGuidQueryRequiredInSecure();


	@Override
	public int doStartTag() throws JspException {
		//URL
		addAttribute("href", constructUrl(href));
		JspWriterUtils.write(
				pageContext.getOut(),
				STAG + TAG + getDynamicAttributesStringBuilder().toString() + ETAG);
		//BodyBuffered
		return EVAL_BODY_BUFFERED;
	}

	protected String constructUrl(String url) {
		if (url == null) {
			url = "";
		}
		Mobylet m = MobyletFactory.getInstance();
		HttpServletRequest request = RequestUtils.get();
		//Session
		HttpSession session = request.getSession(false);
		if (session != null) {
			if (!isSessionCookiePriority() ||
					(isSessionCookiePriority() && !m.hasCookies())) {
				url = UrlUtils.addSession(url, session.getId());
			}
		}
		//SecureQuery
		if (isUidOrGuidQueryRequiredInSecure() &&
				m.getCarrier() == Carrier.DOCOMO &&
				(url.startsWith("https:") ||
						(!url.startsWith("http:") && request.isSecure()))) {
			String id = m.getGuid();
			if (StringUtils.isEmpty(id)) {
				id = m.getUid();
				if (StringUtils.isNotEmpty(id)) {
					url = UrlUtils.addParameter(url, "uid", id);
				}
			}
		//UidQuery
		} else if (isUidQueryRequired() &&
				m.getCarrier() == Carrier.DOCOMO &&
				(url.startsWith("http:") ||
						(!url.startsWith("https:") && !request.isSecure()))) {
			url = UrlUtils.addParameter(url, "uid", "NULLGWDOCOMO");
		//GuidQuery
		} else if (isGuidQueryRequired() &&
				m.getCarrier() == Carrier.DOCOMO &&
				(url.startsWith("http:") ||
						(!url.startsWith("https:") && !request.isSecure()))) {
			url = UrlUtils.addParameter(url, "guid", "ON");
		}
		return url;
	}

	@Override
	public int doEndTag() throws JspException {
		JspWriterUtils.write(
				pageContext.getOut(),
				getBodyContent().getString() + STAG + SL + TAG + ETAG);
		recycle();
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

	public boolean isSessionCookiePriority() {
		return isSessionCookiePriority;
	}

	public void setSessionCookiePriority(boolean isSessionCookiePriority) {
		this.isSessionCookiePriority = isSessionCookiePriority;
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

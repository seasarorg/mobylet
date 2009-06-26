package org.mobylet.taglibs.impl;

import javax.servlet.jsp.JspException;

import org.mobylet.core.util.StringUtils;
import org.mobylet.taglibs.utils.JspWriterUtils;
import org.mobylet.taglibs.utils.UrlUtils;

public class FormTag extends TransitionTag {

	private static final long serialVersionUID = 1L;

	public static final String TAG = "form";


	protected String action;

	protected String method;



	@Override
	public int doStartTag() throws JspException {
		//URL
		addAttribute("action", constructUrl(action));
		addAttribute("method", method);
		JspWriterUtils.write(
				pageContext.getOut(),
				STAG + TAG + getDynamicAttributesStringBuilder().toString() + ETAG);
		//HiddenTag
		String hiddenTag = constructInnerTag(action);
		if (StringUtils.isNotEmpty(hiddenTag)) {
			JspWriterUtils.write(pageContext.getOut(), hiddenTag);
		}
		//BodyBuffered
		return EVAL_BODY_BUFFERED;
	}

	protected String constructUrl(String url) {
		if (url == null) {
			url = "";
		}
		//Session
		String sessionId = getSessionId();
		if (StringUtils.isNotEmpty(sessionId)) {
			url = UrlUtils.addSession(url, sessionId);
		}
		return url;
	}

	protected String constructInnerTag(String url) {
		//Query
		Entry paramEntry = getAppendParameter(url);
		if (paramEntry != null) {
			return STAG + "input type=\"hidden\" name=\"" +
				paramEntry.getKey() +
				"\" value=\"" +
				paramEntry.getValue() +
				"\" " + SL + ETAG;
		}
		return null;
	}

	@Override
	public int doEndTag() throws JspException {
		JspWriterUtils.write(
				pageContext.getOut(),
				getBodyContent().getString() + STAG + SL + TAG + ETAG);
		recycle();
		return EVAL_PAGE;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}

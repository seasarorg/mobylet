/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.mobylet.taglibs.impl;

import javax.servlet.jsp.JspException;

import org.mobylet.taglibs.utils.JspWriterUtils;
import org.mobylet.view.designer.AnchorDesigner;
import org.mobylet.view.designer.SingletonDesigner;

public class ATag extends TransitionTag {

	private static final long serialVersionUID = 6951351713553308618L;

	public static final String TAG = "a";


	protected String href;


	@Override
	public int doStartTag() throws JspException {
		try {
			//Designer
			AnchorDesigner designer =
				SingletonDesigner.getDesigner(AnchorDesigner.class);
			//URL
			addAttribute("href", designer.getHref(href, getConfig()));
			JspWriterUtils.write(
					pageContext.getOut(),
					STAG + TAG + getDynamicAttributesStringBuilder().toString() + ETAG);
			//BodyBuffered
			return EVAL_BODY_BUFFERED;
		} catch (Exception e) {
			throw new JspException(e);
		}
	}

	@Override
	public int doEndTag() throws JspException {
		try {
			JspWriterUtils.write(
					pageContext.getOut(),
					getBodyContent().getString() + STAG + SL + TAG + ETAG);
			recycle();
			return EVAL_PAGE;
		} catch (Exception e) {
			throw new JspException(e);
		}
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}
}

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

import org.mobylet.core.util.StringUtils;
import org.mobylet.taglibs.utils.JspWriterUtils;
import org.mobylet.view.design.GpsDesign;
import org.mobylet.view.designer.GpsDesigner;
import org.mobylet.view.designer.SingletonDesigner;
import org.mobylet.view.xhtml.TagAttribute;

public class GpsTag extends ATag {

	private static final long serialVersionUID = -4285604125713972051L;

	protected String kickBackUrl;


	@Override
	public int doStartTag() throws JspException {
		try {
			GpsDesigner designer =
				SingletonDesigner.getDesigner(GpsDesigner.class);
			GpsDesign gpsDesign = designer.getGpsDesign(kickBackUrl, getConfig());
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

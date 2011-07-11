/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.mobylet.core.util.StringUtils;
import org.mobylet.taglibs.utils.JspWriterUtils;
import org.mobylet.view.designer.OutDesigner;
import org.mobylet.view.designer.SingletonDesigner;

public class OutTag extends SimpleTagSupport {

	protected String value;

	protected boolean escapeXml = true;

	protected boolean breakToBr = true;


	@Override
	public void doTag() throws JspException, IOException {
		try {
			if (StringUtils.isEmpty(value)) {
				return;
			}
			OutDesigner designer =
				SingletonDesigner.getDesigner(OutDesigner.class);
			String outString = designer.get(value, escapeXml, breakToBr);
			JspWriterUtils.write(
					getJspContext().getOut(), outString);
		} catch (Exception e) {
			throw new JspException(e);
		}
	}


	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isEscapeXml() {
		return escapeXml;
	}

	public void setEscapeXml(boolean escapeXml) {
		this.escapeXml = escapeXml;
	}

	public boolean isBreakToBr() {
		return breakToBr;
	}

	public void setBreakToBr(boolean breakToBr) {
		this.breakToBr = breakToBr;
	}

}

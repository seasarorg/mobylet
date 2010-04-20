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

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.mobylet.core.util.StringUtils;
import org.mobylet.taglibs.MobyletDynamicSimpleTagSupport;
import org.mobylet.taglibs.utils.JspWriterUtils;
import org.mobylet.view.designer.CSSDesigner;
import org.mobylet.view.designer.SingletonDesigner;

public class CSSTag extends MobyletDynamicSimpleTagSupport {

	protected String src;

	protected String charset;

	@Override
	public void doTag() throws JspException, IOException {
		if (StringUtils.isEmpty(charset)) {
			charset = "UTF-8";
		}
		try {
			CSSDesigner designer =
				SingletonDesigner.getDesigner(CSSDesigner.class);
			JspWriterUtils.write(
					getJspContext().getOut(),
					designer.includeCSS(src, charset)
					);
		} catch (Exception e) {
			throw new JspException(e);
		}
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

}

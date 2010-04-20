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
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.taglibs.utils.JspWriterUtils;

public class CharsetTag extends SimpleTagSupport {


	protected String type = "content";

	@Override
	public void doTag() throws JspException, IOException {
		try {
			Mobylet m = MobyletFactory.getInstance();
			String charsetName = m.getDialect().getContentCharsetName();
			if ("native".equalsIgnoreCase(type)) {
				charsetName = m.getDialect().getCharacterEncodingCharsetName();
			} else if ("real".equalsIgnoreCase(type)) {
				charsetName = m.getDialect().getCharsetName();
			}
			JspWriterUtils.write(
					getJspContext().getOut(),
					charsetName
					);
		} catch (Exception e) {
			throw new JspException(e);
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}

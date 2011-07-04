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

import org.mobylet.core.Carrier;
import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.taglibs.MobyletDynamicSimpleTagSupport;
import org.mobylet.taglibs.utils.JspWriterUtils;

public class HiddenGuidTag extends MobyletDynamicSimpleTagSupport {

	public static final String TAG = "input type=\"hidden\"";

	@Override
	public void doTag() throws JspException, IOException {
		try {
			Mobylet m = MobyletFactory.getInstance();
			if (m.getCarrier() == Carrier.DOCOMO) {
				addAttribute("name", "guid");
				String guid = m.getGuid();
				addAttribute("value", guid != null ? guid : "");
				JspWriterUtils.write(
						getJspContext().getOut(),
						STAG + TAG + getDynamicAttributesStringBuilder().toString() + SL + ETAG
						);
			}
			recycle();
		} catch (Exception e) {
			throw new JspException(e);
		}
	}

}

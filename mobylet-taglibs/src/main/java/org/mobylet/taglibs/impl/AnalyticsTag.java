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

import org.mobylet.core.Carrier;
import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.analytics.AnalyticsExecutor;
import org.mobylet.core.analytics.impl.GoogleAnalyticsConfig;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.taglibs.utils.JspWriterUtils;

public class AnalyticsTag extends SimpleTagSupport {

	protected String id;

	protected GoogleAnalyticsConfig config =
		SingletonUtils.get(GoogleAnalyticsConfig.class);

	@Override
	public void doTag() throws JspException, IOException {
		Mobylet m = MobyletFactory.getInstance();
		if (m == null ||
				m.getCarrier() == Carrier.OTHER) {
			if (config.useJs()) {
				JspWriterUtils.write(
						getJspContext().getOut(),
						"<script type=\"text/javascript\">" +
						"var gaJsHost = ((\"https:\" == document.location.protocol) ? \"https://ssl.\" : \"http://www.\");" +
						"document.write(unescape(\"%3Cscript src='\" + gaJsHost + \"google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E\"));" +
						"</script>" +
						"<script type=\"text/javascript\">" +
						"var pageTracker = _gat._getTracker(\"" + id + "\");" +
						"pageTracker._trackPageview();" +
						"</script>"
						);
			}
		} else {
			AnalyticsExecutor executor =
				SingletonUtils.get(AnalyticsExecutor.class);
			executor.execute(id);
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}

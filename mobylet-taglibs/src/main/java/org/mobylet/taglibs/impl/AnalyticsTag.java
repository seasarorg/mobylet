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

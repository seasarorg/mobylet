package org.mobylet.taglibs.impl;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.mobylet.core.analytics.AnalyticsExecutor;
import org.mobylet.core.util.SingletonUtils;

public class AnalyticsTag extends SimpleTagSupport {

	protected String id;


	@Override
	public void doTag() throws JspException, IOException {
		AnalyticsExecutor executor =
			SingletonUtils.get(AnalyticsExecutor.class);
		executor.execute(id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}

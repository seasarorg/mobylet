package org.mobylet.gae.http;

import javax.servlet.FilterConfig;

import org.mobylet.core.http.MobyletFilter;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.gae.initializer.impl.GaeMobyletInitializer;

public class GaeMobyletFilter extends MobyletFilter {

	@Override
	protected void initInitializer(FilterConfig filterConfig) {
		SingletonUtils.put(new GaeMobyletInitializer());
		super.initInitializer(filterConfig);
	}
}

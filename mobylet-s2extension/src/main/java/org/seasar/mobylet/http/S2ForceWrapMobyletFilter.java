package org.seasar.mobylet.http;

import javax.servlet.FilterConfig;

import org.mobylet.core.http.ForceWrapMobyletFilter;
import org.mobylet.core.util.SingletonUtils;
import org.seasar.mobylet.holder.S2MobyletSingletonHolder;

public class S2ForceWrapMobyletFilter extends ForceWrapMobyletFilter {

	@Override
	protected void initSingletonContainer(FilterConfig filterConfig) {
		if (!SingletonUtils.isInitialized()) {
			SingletonUtils.initialize(S2MobyletSingletonHolder.class);
		}
		super.initSingletonContainer(filterConfig);
	}
}

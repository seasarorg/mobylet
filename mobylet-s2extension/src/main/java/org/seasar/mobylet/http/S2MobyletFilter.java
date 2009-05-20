package org.seasar.mobylet.http;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.mobylet.core.http.MobyletFilter;
import org.mobylet.core.util.SingletonUtils;
import org.seasar.mobylet.holder.S2MobyletSingletonHolder;

public class S2MobyletFilter extends MobyletFilter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		SingletonUtils.initialize(S2MobyletSingletonHolder.class);
	}
}

package org.mobylet.core.analytics.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.mobylet.core.analytics.AnalyticsExecutor;
import org.mobylet.core.analytics.AnalyticsHelper;
import org.mobylet.core.util.SingletonUtils;

public class GoogleAnalyticsExecutor implements AnalyticsExecutor {

	protected ExecutorService pool;

	public GoogleAnalyticsExecutor() {
		GoogleAnalyticsConfig config =
			SingletonUtils.get(GoogleAnalyticsConfig.class);
		pool = Executors.newFixedThreadPool(config.getMaxThread());
	}

	@Override
	public void execute(String id) {
		AnalyticsHelper helper = SingletonUtils.get(AnalyticsHelper.class);
		helper.prepare();
		pool.execute(new GoogleAnalyticsProcess(id));
	}

}

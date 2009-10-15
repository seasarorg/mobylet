package org.mobylet.core.analytics.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.mobylet.core.analytics.AnalyticsExecutor;
import org.mobylet.core.analytics.AnalyticsHelper;
import org.mobylet.core.util.SingletonUtils;

public class GoogleAnalyticsExecutor implements AnalyticsExecutor {

	protected ExecutorService pool;

	public GoogleAnalyticsExecutor() {
	}

	@Override
	public void execute(String urchinId) {
		if (pool == null) {
			synchronized(this) {
				if (pool == null) {
					GoogleAnalyticsConfig config = SingletonUtils.get(GoogleAnalyticsConfig.class);
					pool = Executors.newFixedThreadPool(config.getMaxThread());
				}
			}
		}
		try {
			AnalyticsHelper helper = SingletonUtils.get(AnalyticsHelper.class);
			pool.execute(
					new GoogleAnalyticsProcess(
							helper.getParameters(urchinId)));
		} catch (Throwable t) {
			//NOP
		}
	}
}

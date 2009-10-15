package org.mobylet.core.analytics.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.mobylet.core.analytics.AnalyticsExecutor;
import org.mobylet.core.analytics.AnalyticsHelper;
import org.mobylet.core.util.SingletonUtils;

public class GoogleAnalyticsExecutor implements AnalyticsExecutor {

	protected ExecutorService pool;

	protected boolean isInitialized = false;


	public GoogleAnalyticsExecutor() {
	}

	@Override
	public void execute(String urchinId) {
		if (!isInitialized) {
			synchronized(this) {
				if (!isInitialized) {
					GoogleAnalyticsConfig config = SingletonUtils.get(GoogleAnalyticsConfig.class);
					Integer maxThread = config.getMaxThread();
					if (maxThread > 0) {
						pool = Executors.newFixedThreadPool(config.getMaxThread());
					}
					isInitialized = true;
				}
			}
		}
		try {
			AnalyticsHelper helper = SingletonUtils.get(AnalyticsHelper.class);
			GoogleAnalyticsProcess process =
				new GoogleAnalyticsProcess(helper.getParameters(urchinId));
			if (pool != null) {
				pool.execute(process);
			} else {
				process.run();
			}
		} catch (Throwable t) {
			//NOP
		}
	}
}

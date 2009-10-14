package org.mobylet.core.analytics.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.mobylet.core.analytics.AnalyticsExecutor;

public class GoogleAnalyticsExecutor implements AnalyticsExecutor {

	protected ExecutorService pool;
	
	public GoogleAnalyticsExecutor() {
		pool = Executors.newFixedThreadPool(30);
	}
	
	@Override
	public void execute(String id) {
		pool.execute(new GoogleAnalyticsProcess(id));
	}

}

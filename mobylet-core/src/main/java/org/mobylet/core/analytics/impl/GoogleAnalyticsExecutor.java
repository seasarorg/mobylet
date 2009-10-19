package org.mobylet.core.analytics.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.analytics.AnalyticsExecutor;
import org.mobylet.core.analytics.AnalyticsHelper;
import org.mobylet.core.analytics.AnalyticsParameters;
import org.mobylet.core.util.SingletonUtils;

public class GoogleAnalyticsExecutor implements AnalyticsExecutor {

	protected ExecutorService pool;

	protected GoogleAnalyticsConfig config;

	protected boolean isInitialized = false;


	public GoogleAnalyticsExecutor() {
		//GAE対応
		// : コンストラクタでThreadPoolを生成しない
	}

	@Override
	public void execute(String urchinId) {
		initialize();
		try {
			AnalyticsHelper helper = SingletonUtils.get(AnalyticsHelper.class);
			AnalyticsParameters parameters = helper.getParameters(urchinId);
			if (isIgnoreUserAgent(parameters)) {
				return;
			}
			GoogleAnalyticsProcess process =
				new GoogleAnalyticsProcess(parameters);
			if (pool != null) {
				pool.execute(process);
			} else {
				process.run();
			}
		} catch (Throwable t) {
			//NOP
		}
	}

	protected boolean isIgnoreUserAgent(AnalyticsParameters parameters) {
		try {
			if (config.isIgnoreMobileCrawler) {
				Matcher mobileMatcher =
					config.getRegexMobileCrawler().matcher(parameters.getUserAgent());
				if (mobileMatcher.find()) {
					return true;
				} else if (config.isIgnoreCrawler &&
					config.getRegexCrawler().matcher(parameters.getUserAgent()).find()) {
					return true;
				}
			} else if (config.isIgnoreCrawler &&
					config.getRegexCrawler().matcher(parameters.getUserAgent()).find()) {
				return true;
			}
			return false;
		} catch (Exception e) {
			throw new MobyletRuntimeException(
					"除外UserAgent検証処理にて例外発生", e);
		}
	}

	protected void initialize() {
		if (!isInitialized) {
			synchronized(this) {
				if (!isInitialized) {
					config = SingletonUtils.get(GoogleAnalyticsConfig.class);
					Integer maxThread = config.getMaxThread();
					if (maxThread > 0) {
						pool = Executors.newFixedThreadPool(config.getMaxThread());
					}
					isInitialized = true;
				}
			}
		}
	}

}

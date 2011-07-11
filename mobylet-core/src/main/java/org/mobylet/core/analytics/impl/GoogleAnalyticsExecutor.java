/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.mobylet.core.analytics.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.analytics.AnalyticsExecutor;
import org.mobylet.core.analytics.AnalyticsHelper;
import org.mobylet.core.analytics.AnalyticsParameters;
import org.mobylet.core.log.MobyletLogger;
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
			MobyletLogger logger = SingletonUtils.get(MobyletLogger.class);
			if (logger != null && logger.isLoggable())
				logger.log("[mobylet] GoogleAnalyticsExecutor#executeで例外が発生したためスレッドを終了します = " + t.getMessage());
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
			MobyletLogger logger = SingletonUtils.get(MobyletLogger.class);
			if (logger != null && logger.isLoggable())
				logger.log("[mobylet] 除外UserAgent検証処理で例外発生 = " + e.getMessage());
			throw new MobyletRuntimeException(
					"除外UserAgent検証処理にて例外発生", e);
		}
	}

	protected boolean isInitialized() {
		return isInitialized;
	}

	protected void initialize() {
		if (!isInitialized()) {
			synchronized(this) {
				if (!isInitialized()) {
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

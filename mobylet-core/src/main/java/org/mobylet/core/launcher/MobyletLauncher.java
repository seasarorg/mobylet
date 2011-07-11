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
package org.mobylet.core.launcher;

import java.nio.charset.Charset;

import org.mobylet.core.config.MobyletConfig;
import org.mobylet.core.config.xml.MobyletConfigXmlReader;
import org.mobylet.core.define.DefCharset;
import org.mobylet.core.initializer.MobyletInitializer;
import org.mobylet.core.log.MobyletLogger;
import org.mobylet.core.log.impl.LoggerImpl;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;

public class MobyletLauncher {

	public static void launch() {
		LaunchConfig launchConfig = new LaunchConfig();
		initSingletonContainer();
		initLogger(launchConfig);
		initDefaultCharset(launchConfig);
		initInitializer(launchConfig);
	}

	public static void initSingletonContainer() {
		if (!SingletonUtils.isInitialized()) {
			SingletonUtils.initialize(null);
		}
	}

	public static void initLogger(LaunchConfig launchConfig) {
		String loggerClassName = "";
		if (launchConfig != null) {
			loggerClassName = launchConfig.getInitParameter("mobylet.logger.class");
			if (loggerClassName == null) {
				loggerClassName = "";
			}
		}
		MobyletLogger logger = null;
		if (StringUtils.isEmpty(loggerClassName)) {
			logger = new LoggerImpl();
		} else {
			try {
				Class<?> clazz = Class.forName(loggerClassName);
				Class<? extends MobyletLogger> loggerClass = clazz.asSubclass(MobyletLogger.class);
				logger = loggerClass.newInstance();
			} catch (ClassNotFoundException e) {
				logger = new LoggerImpl();
				if (logger.isLoggable())
					logger.log("[mobylet] 指定されたLoggerクラスが見つかりませんでした = " + loggerClassName);
			} catch (ClassCastException e) {
				logger = new LoggerImpl();
				if (logger.isLoggable())
					logger.log("[mobylet] 指定されたLoggerクラスがMobyletLoggerの実装ではありません = " + loggerClassName);
			} catch (InstantiationException e) {
				logger = new LoggerImpl();
				if (logger.isLoggable())
					logger.log("[mobylet] 指定されたLoggerクラスのインスタンスを生成できませんでした  = " + loggerClassName);
			} catch (IllegalAccessException e) {
				logger = new LoggerImpl();
				if (logger.isLoggable())
					logger.log("[mobylet] 指定されたLoggerクラスにアクセスできませんでした  = " + loggerClassName);
			}
		}
		if (logger != null) {
			SingletonUtils.put(logger);
		} else {
			SingletonUtils.put(new LoggerImpl());
		}
	}

	public static void initInitializer(LaunchConfig launchConfig) {
		String configDir = "";
		if (launchConfig != null) {
			configDir = launchConfig.getInitParameter("mobylet.config.dir");
			if (configDir == null) {
				configDir = "";
			}
		}
		MobyletConfigXmlReader configXml =
			new MobyletConfigXmlReader(configDir);
		MobyletConfig config = configXml.getConfig();
		//初期化
		for (MobyletInitializer initializer : config.getInitializers()) {
			initializer.initialize();
			SingletonUtils.put(initializer);
		}
	}

	public static void initDefaultCharset(LaunchConfig launchConfig) {
		String defCharsetName = null;
		if (launchConfig != null &&
				(defCharsetName =
					launchConfig.getInitParameter("encoding")) != null) {
			Charset defCharset = Charset.forName(defCharsetName);
			if (defCharset != null) {
				SingletonUtils.put(defCharset);
			}
		}
		if (SingletonUtils.get(Charset.class) == null) {
			SingletonUtils.put(Charset.forName(DefCharset.WIN31J));
		}
	}

}

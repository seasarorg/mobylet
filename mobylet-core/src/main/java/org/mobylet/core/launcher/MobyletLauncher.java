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

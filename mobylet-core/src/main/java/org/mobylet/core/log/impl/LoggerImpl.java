package org.mobylet.core.log.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.mobylet.core.log.MobyletLogger;

public class LoggerImpl implements MobyletLogger {

	protected Level logLevel = Level.INFO;

	protected Logger logger =
		Logger.getLogger(MobyletLogger.class.getCanonicalName());

	@Override
	public boolean isLoggable() {
		return logger.isLoggable(logLevel);
	}

	@Override
	public void log(String msg) {
		logger.log(logLevel, msg);
	}

}

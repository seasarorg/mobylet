package org.mobylet.core.log.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.mobylet.core.log.MobyletLogger;

public class LoggerImpl implements MobyletLogger {

	protected Logger logger =
		Logger.getLogger(MobyletLogger.class.getCanonicalName());

	@Override
	public boolean isLoggable() {
		return logger.isLoggable(getLevel());
	}

	@Override
	public void log(String msg) {
		logger.log(getLevel(), msg);
	}

	protected Level getLevel() {
		return Level.INFO;
	}

}

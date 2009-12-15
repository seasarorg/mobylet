package org.mobylet.core.log.impl;

import java.util.logging.Level;

public class LoggerFine extends LoggerImpl {

	@Override
	protected Level getLevel() {
		return Level.FINE;
	}

}

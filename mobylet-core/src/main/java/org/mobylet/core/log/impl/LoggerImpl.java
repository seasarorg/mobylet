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

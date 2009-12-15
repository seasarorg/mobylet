package org.mobylet.core.log;

public interface MobyletLogger {

	boolean isLoggable();

	void log(String msg);

}

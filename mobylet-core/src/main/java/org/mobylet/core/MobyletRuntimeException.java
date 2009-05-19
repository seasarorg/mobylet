package org.mobylet.core;

public class MobyletRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -43750892036452500L;

	protected String message;

	protected Exception cause;


	public MobyletRuntimeException(String msg, Exception e) {
		message = msg;
		cause = e;
	}

	@Override
	public Throwable getCause() {
		return cause;
	}

}

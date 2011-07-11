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
package org.mobylet.core;

/**
 *
 * <p>mobyletフレームワーク内で発生した例外をラップする例外クラス.</p>
 *
 * @author stakeuchi
 *
 */
public class MobyletRuntimeException extends RuntimeException {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -43750892036452500L;

	/**
	 * 例外メッセージ
	 */
	protected String message;

	/**
	 * 原因例外
	 */
	protected Exception cause;


	/**
	 * <p>新たな例外インスタンスを作る。</p>
	 *
	 * @param msg	例外メッセージ
	 * @param e		原因例外
	 */
	public MobyletRuntimeException(String msg, Exception e) {
		message = msg;
		cause = e;
	}

	/*
	 * (非 Javadoc)
	 * @see java.lang.Throwable#getCause()
	 */
	@Override
	public Throwable getCause() {
		return cause;
	}

	/*
	 * (非 Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return message;
	}

}

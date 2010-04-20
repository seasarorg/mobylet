/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.mobylet.core.define;

/**
 *
 * <p>charset定義</p>
 *
 * @author stakeuchi
 *
 */
public interface DefCharset {

	/**
	 * mobylet-charsetで定義したdocomo用の文字コード
	 */
	public static final String DOCOMO = "x-mobylet-docomo";

	/**
	 * mobylet-charsetで定義したau用の文字コード
	 */
	public static final String AU = "x-mobylet-au";

	/**
	 * UTF-8
	 */
	public static final String UTF8 = "utf-8";

	/**
	 * SJIS
	 */
	public static final String SJIS = "shift_jis";

	/**
	 * Win31j-Charset
	 */
	public static final String WIN31J = "windows-31j";

}

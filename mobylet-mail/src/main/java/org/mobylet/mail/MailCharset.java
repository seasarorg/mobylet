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
package org.mobylet.mail;

import java.nio.charset.Charset;

import org.mobylet.charset.MobyletCharsetPool;
import org.mobylet.core.define.DefCharset;

public interface MailCharset {

	public static final Charset DOCOMO =
		MobyletCharsetPool.getInstance().charsetForName(DefCharset.DOCOMO);

	public static final Charset AU =
		MobyletCharsetPool.getInstance().charsetForName(
				MailConstants.CHARSET_AU_MAIL_SJIS);

	public static final Charset SOFTBANK =
		Charset.forName(DefCharset.UTF8);

	public static final Charset OTHER =
		Charset.forName(MailConstants.CHARSET_ISO_2022_1);

}

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
package org.mobylet.mail.util;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

import org.mobylet.core.Carrier;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.mail.MailConstants;
import org.mobylet.mail.selector.MailCharsetSelector;

public class DataHandlerUtils {

	public static DataHandler getDataHandler(Carrier carrier, String source) {
		//Get-Charset
		MailCharsetSelector charsetSelector =
			SingletonUtils.get(MailCharsetSelector.class);
		Charset encodingCharset =
			charsetSelector.getEncodingCharset(carrier);
		//Build
		if (source == null) {
			source = "";
		}
		//Convert-Emoji
		ByteBuffer buf = null;
		buf = ByteBuffer.wrap(
				MailEmojiUtils.convert(carrier, source).getBytes(encodingCharset));
		DataSource dataSource =
			new ByteArrayDataSource(
					buf.array(), MailConstants.APPLICATION_OCTETSTREAM
			);
		return new DataHandler(dataSource);
	}

}

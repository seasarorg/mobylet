/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.mobylet.core.http;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.io.Writer;

import org.mobylet.core.Carrier;
import org.mobylet.core.emoji.Emoji;
import org.mobylet.core.emoji.EmojiPoolFamily;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;

public class MobyletPrintWriter extends PrintWriter {

	protected Carrier outCarrier;

	protected EmojiPoolFamily family;


	public MobyletPrintWriter(Writer out, Carrier outCarrier) {
		super(out);
		this.outCarrier = outCarrier;
		family = SingletonUtils.get(EmojiPoolFamily.class);
	}

	/**
	 * {@inheritDoc}
	 * @see java.io.PrintWriter#write(int)
	 */
	@Override
	public void write(int c) {
		if (family != null) {
			char ch = (char)c;
			Emoji e = family.getEmoji(ch);
			if (e == null) {
				super.write(c);
			} else {
				Emoji related = e.getRelated(outCarrier);
				if (related == null) {
					super.write(c);
				} else {
					char[] codes = related.getCodes();
					if (codes != null) {
						for (char code : codes) {
							super.write(code);
						}
					}
				}
			}
		} else {
			super.write(c);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see java.io.PrintWriter#write(char[], int, int)
	 */
	@Override
	public void write(char buf[], int off, int len) {
		if (family != null) {
			CharArrayWriter caw = new CharArrayWriter(len);
			for (int i=off; i<off+len; i++) {
				Emoji e = family.getEmoji(buf[i]);
				if (e == null) {
					caw.append(buf[i]);
				} else {
					Emoji related = e.getRelated(outCarrier);
					if (related == null) {
						caw.append(buf[i]);
					} else {
						char[] codes = related.getCodes();
						if (codes != null) {
							for (char code : codes) {
								caw.append(code);
							}
						}
					}
				}
			}
			super.write(caw.toCharArray());
		} else {
			super.write(buf, off, len);
		}
	}

	/**
	 * {@inheritDoc}
	 * @see java.io.PrintWriter#write(java.lang.String, int, int)
	 */
	@Override
	public void write(String s, int off, int len) {
		if (StringUtils.isNotEmpty(s)) {
			super.write(s.toCharArray(), off, len);
		}
	}

}

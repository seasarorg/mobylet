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
package org.mobylet.core.util;

import org.mobylet.core.Carrier;
import org.mobylet.core.emoji.EmojiPoolFamily;

public class EmojiUtils {

	public static String replaceEmoji(String src, String replace) {
		if (StringUtils.isEmpty(src)) {
			return null;
		}
		if (replace == null) {
			replace = "";
		}
		EmojiPoolFamily family = SingletonUtils.get(EmojiPoolFamily.class);
		StringBuilder sb = new StringBuilder();
		for (char c : src.toCharArray()) {
			if (family.isEmoji(c)) {
				sb.append(replace);
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String removeEmoji(String src) {
		return replaceEmoji(src, "");
	}

	public static String getEmoji(String name) {
		return getEmoji(name, Carrier.DOCOMO);
	}

	public static String getEmoji(String name, Carrier carrier) {
		EmojiPoolFamily family = SingletonUtils.get(EmojiPoolFamily.class);
		return new String(family.getEmojiPool(carrier).get(name).getCodes());
	}

}

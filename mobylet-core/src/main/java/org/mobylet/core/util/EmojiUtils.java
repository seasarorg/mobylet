package org.mobylet.core.util;

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

}

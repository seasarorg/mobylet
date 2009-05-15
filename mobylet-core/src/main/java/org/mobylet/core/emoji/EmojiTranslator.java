package org.mobylet.core.emoji;

import org.mobylet.core.Carrier;
import org.mobylet.core.util.StringUtils;


public class EmojiTranslator {

	protected EmojiStocker context;

	protected Carrier outCarrier;


	public void initialize(Carrier in, Carrier out) {
		// TODO 自動生成されたメソッド・スタブ
		outCarrier = out;
	}

	public char[] translate(char inchar) {
		Emoji e = context.get(inchar);
		if (e != null) {
			return e.getRelated(outCarrier).getCodes();
		}
		return null;
	}

	public String translate(String inString) {
		if (StringUtils.isNotEmpty(inString)) {
			StringBuilder sb = new StringBuilder();
			for (char c : inString.toCharArray()) {
				sb.append(translate(c));
			}
			return sb.toString();
		} else {
			return inString;
		}
	}

}

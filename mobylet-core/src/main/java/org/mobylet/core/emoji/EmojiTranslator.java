package org.mobylet.core.emoji;

import org.mobylet.core.Carrier;

public interface EmojiTranslator {

	public char translate(Carrier in, Carrier out, char inchar);

	public String translate(Carrier in, Carrier out, String inString);

}

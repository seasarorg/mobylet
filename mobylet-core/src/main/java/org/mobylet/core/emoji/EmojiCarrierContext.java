package org.mobylet.core.emoji;

import java.util.HashMap;
import java.util.Map;

import org.mobylet.core.Carrier;

public class EmojiCarrierContext {

	protected Carrier carrier;

	protected char minEmoji = 0xFFFF;

	protected char maxEmoji = 0x0000;

	protected Map<Character, Emoji> tmpEmojiMap;

	protected Emoji[] emojiArray;


	public EmojiCarrierContext(Carrier carrier) {
		this.carrier = carrier;
		tmpEmojiMap = new HashMap<Character, Emoji>(512);
	}

	public void put(char c) {
		Emoji e = new Emoji(carrier, c);
		tmpEmojiMap.put(c, e);
		if (c < minEmoji) {
			minEmoji = c;
		}
		if (c > maxEmoji) {
			maxEmoji = c;
		}
	}
}

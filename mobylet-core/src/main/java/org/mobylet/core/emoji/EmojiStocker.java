package org.mobylet.core.emoji;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.mobylet.core.Carrier;
import org.mobylet.core.define.DefineChar;

public class EmojiStocker {

	protected Carrier carrier;

	protected char minEmoji = DefineChar.DEF_MAX_CHAR;

	protected char maxEmoji = DefineChar.DEF_MIN_CHAR;

	protected Map<Character, Emoji> tmpEmojiMap;

	protected Emoji[] emojiArray;


	protected boolean isConstructed = false;


	public EmojiStocker(Carrier carrier) {
		this.carrier = carrier;
		tmpEmojiMap = new HashMap<Character, Emoji>(512);
	}

	public Carrier getCarrier() {
		return carrier;
	}

	public void put(char c) {
		Emoji e = new Emoji(carrier, c);
		put(e);
	}

	public void put(Emoji e) {
		char[] c = e.getCodes();
		if (c == null || c.length != 1) {
			return;
		}
		tmpEmojiMap.put(c[0], e);
		if (c[0] < minEmoji) {
			minEmoji = c[0];
		}
		if (c[0] > maxEmoji) {
			maxEmoji = c[0];
		}
		isConstructed = false;
	}

	public boolean isEmoji(char c) {
		if (!isConstructed) {
			construct();
		}
		return minEmoji <= c && c <= maxEmoji
			&& emojiArray[c - minEmoji] != null;
	}

	public Emoji get(char c) {
		if (isEmoji(c)) {
			return emojiArray[c - minEmoji];
		} else {
			return null;
		}
	}

	public Emoji getUnConstructed(char c) {
		return tmpEmojiMap.get(c);
	}

	public char getMinEmoji() {
		return minEmoji;
	}

	public char getMaxEmoji() {
		return maxEmoji;
	}

	public void construct() {
		emojiArray = new Emoji[maxEmoji - minEmoji + 1];
		Set<Entry<Character, Emoji>> entrySet = tmpEmojiMap.entrySet();
		for (Entry<Character, Emoji> emojiEntry : entrySet) {
			emojiArray[emojiEntry.getKey() - minEmoji] = emojiEntry.getValue();
		}
		isConstructed = true;
	}

}

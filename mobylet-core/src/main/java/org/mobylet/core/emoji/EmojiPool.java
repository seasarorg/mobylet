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
package org.mobylet.core.emoji;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.mobylet.core.Carrier;
import org.mobylet.core.define.DefChar;

public class EmojiPool {

	protected Carrier carrier;

	protected int minEmoji = DefChar.MAX_CHAR;

	protected int maxEmoji = DefChar.MIN_CHAR;

	protected Map<Character, Emoji> emojiMap;

	protected Emoji[] emojiArray;


	protected boolean isConstructed = false;


	public EmojiPool(Carrier carrier) {
		this.carrier = carrier;
		emojiMap = new HashMap<Character, Emoji>(512);
	}

	public void construct() {
		if (maxEmoji != DefChar.MIN_CHAR &&
				minEmoji != DefChar.MAX_CHAR) {
			emojiArray = new Emoji[maxEmoji - minEmoji + 1];
			Set<Entry<Character, Emoji>> entrySet = emojiMap.entrySet();
			for (Entry<Character, Emoji> emojiEntry : entrySet) {
				emojiArray[emojiEntry.getKey() - minEmoji] = emojiEntry.getValue();
			}
		}
		isConstructed = true;
	}

	public Carrier getCarrier() {
		return carrier;
	}

	public Emoji putOnce(char c) {
		Emoji e = getUnConstructed(c);
		if (e != null) {
			return e;
		}
		return put(c);
	}

	public Emoji put(char c) {
		Emoji e = new Emoji(carrier, c);
		return put(e);
	}

	public Emoji put(Emoji e) {
		char[] c = e.getCodes();
		if (c == null || c.length != 1) {
			return null;
		}
		emojiMap.put(c[0], e);
		if (c[0] < minEmoji) {
			minEmoji = c[0];
		}
		if (c[0] > maxEmoji) {
			maxEmoji = c[0];
		}
		isConstructed = false;
		return e;
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
		return emojiMap.get(c);
	}

	public char getMinEmoji() {
		return (char)minEmoji;
	}

	public char getMaxEmoji() {
		return (char)maxEmoji;
	}

}

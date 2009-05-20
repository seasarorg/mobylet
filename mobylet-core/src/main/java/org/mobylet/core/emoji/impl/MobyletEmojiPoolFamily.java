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
package org.mobylet.core.emoji.impl;

import java.util.HashMap;
import java.util.Map;

import org.mobylet.core.Carrier;
import org.mobylet.core.define.DefChar;
import org.mobylet.core.define.DefPath;
import org.mobylet.core.emoji.Emoji;
import org.mobylet.core.emoji.EmojiPool;
import org.mobylet.core.emoji.EmojiPoolFamily;
import org.mobylet.core.emoji.EmojiPoolReader;
import org.mobylet.core.util.SingletonUtils;

public class MobyletEmojiPoolFamily implements EmojiPoolFamily {


	protected Map<Carrier, EmojiPool> family;

	protected EmojiPool[] poolArray;

	protected char minEmoji = DefChar.MAX_CHAR;

	protected char maxEmoji = DefChar.MIN_CHAR;


	public MobyletEmojiPoolFamily() {
		initialize();
	}

	@Override
	public EmojiPool getEmojiPool(Carrier carrier) {
		return family.get(carrier);
	}

	protected void initialize() {
		family = new HashMap<Carrier, EmojiPool>();
		EmojiPoolReader reader = SingletonUtils.get(EmojiPoolReader.class);
		stock(reader, DefPath.EMOJIXML_PATH_D);
		stock(reader, DefPath.EMOJIXML_PATH_A);
		stock(reader, DefPath.EMOJIXML_PATH_S);
		poolArray = new EmojiPool[family.size()];
		int index = 0;
		for (EmojiPool pool : family.values()) {
			pool.construct();
			poolArray[index++] = pool;
			if (pool.getMinEmoji() < minEmoji) {
				minEmoji = pool.getMinEmoji();
			}
			if (pool.getMaxEmoji() > maxEmoji) {
				maxEmoji = pool.getMaxEmoji();
			}
		}
	}

	protected void stock(EmojiPoolReader reader, String path) {
		EmojiPool pool = null;
		if (reader != null &&
				(pool = reader.read(path).get()) != null) {
			family.put(pool.getCarrier(), pool);
		}
	}

	@Override
	public Emoji getEmoji(char c) {
		if (poolArray == null) {
			return null;
		}
		if (c < minEmoji || c > maxEmoji) {
			return null;
		}
		for (EmojiPool stocker : poolArray) {
			if (stocker.isEmoji(c)) {
				return stocker.get(c);
			}
		}
		return null;
	}

	@Override
	public boolean isEmoji(char c) {
		if (poolArray != null &&
				minEmoji <= c && c <= maxEmoji) {
			for (EmojiPool pool : poolArray) {
				if (pool.isEmoji(c)) {
					return true;
				}
			}
		}
		return false;
	}

}

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

	protected EmojiPool[] stockerArray;

	protected char minEmoji = DefChar.MAX_CHAR;

	protected char maxEmoji = DefChar.MIN_CHAR;


	public MobyletEmojiPoolFamily() {
		initialize();
	}

	@Override
	public EmojiPool getEmojiStocker(Carrier carrier) {
		return family.get(carrier);
	}

	protected void initialize() {
		family = new HashMap<Carrier, EmojiPool>();
		EmojiPoolReader reader = SingletonUtils.get(EmojiPoolReader.class);
		stock(reader, DefPath.EMOJIXML_PATH_D);
		stock(reader, DefPath.EMOJIXML_PATH_A);
		stock(reader, DefPath.EMOJIXML_PATH_S);
		stockerArray = new EmojiPool[family.size()];
		int index = 0;
		for (EmojiPool stocker : family.values()) {
			stocker.construct();
			stockerArray[index++] = stocker;
			if (stocker.getMinEmoji() < minEmoji) {
				minEmoji = stocker.getMinEmoji();
			}
			if (stocker.getMaxEmoji() > maxEmoji) {
				maxEmoji = stocker.getMaxEmoji();
			}
		}
	}

	protected void stock(EmojiPoolReader reader, String path) {
		EmojiPool stocker = null;
		if (reader != null &&
				(stocker = reader.read(path).get()) != null) {
			family.put(stocker.getCarrier(), stocker);
		}
	}

	@Override
	public Emoji getEmoji(char c) {
		if (stockerArray == null) {
			return null;
		}
		if (c < minEmoji || c > maxEmoji) {
			return null;
		}
		for (EmojiPool stocker : stockerArray) {
			if (stocker.isEmoji(c)) {
				return stocker.get(c);
			}
		}
		return null;
	}

}

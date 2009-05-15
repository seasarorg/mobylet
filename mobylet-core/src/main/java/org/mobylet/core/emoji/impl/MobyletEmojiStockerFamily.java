package org.mobylet.core.emoji.impl;

import java.util.HashMap;
import java.util.Map;

import org.mobylet.core.Carrier;
import org.mobylet.core.define.DefChar;
import org.mobylet.core.define.DefPath;
import org.mobylet.core.emoji.Emoji;
import org.mobylet.core.emoji.EmojiStocker;
import org.mobylet.core.emoji.EmojiStockerFamily;
import org.mobylet.core.emoji.EmojiStockerReader;
import org.mobylet.core.util.SingletonUtils;

public class MobyletEmojiStockerFamily implements EmojiStockerFamily {


	protected Map<Carrier, EmojiStocker> family;

	protected EmojiStocker[] stockerArray;

	protected char minEmoji = DefChar.MAX_CHAR;

	protected char maxEmoji = DefChar.MIN_CHAR;


	public MobyletEmojiStockerFamily() {
		initialize();
	}

	@Override
	public EmojiStocker getEmojiStocker(Carrier carrier) {
		return family.get(carrier);
	}

	protected void initialize() {
		family = new HashMap<Carrier, EmojiStocker>();
		EmojiStockerReader reader = SingletonUtils.get(EmojiStockerReader.class);
		stock(reader, DefPath.EMOJIXML_PATH_D);
		stock(reader, DefPath.EMOJIXML_PATH_A);
		stock(reader, DefPath.EMOJIXML_PATH_S);
		stockerArray = new EmojiStocker[family.size()];
		int index = 0;
		for (EmojiStocker stocker : family.values()) {
			stockerArray[index++] = stocker;
			if (stocker.getMinEmoji() < minEmoji) {
				minEmoji = stocker.getMinEmoji();
			}
			if (stocker.getMaxEmoji() > maxEmoji) {
				maxEmoji = stocker.getMaxEmoji();
			}
		}
	}

	protected void stock(EmojiStockerReader reader, String path) {
		EmojiStocker stocker = null;
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
		for (EmojiStocker stocker : stockerArray) {
			if (stocker.isEmoji(c)) {
				return stocker.get(c);
			}
		}
		return null;
	}

}

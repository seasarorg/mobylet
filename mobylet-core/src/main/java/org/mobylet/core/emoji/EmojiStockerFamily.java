package org.mobylet.core.emoji;

import org.mobylet.core.Carrier;


public interface EmojiStockerFamily {

	public EmojiStocker getEmojiStocker(Carrier carrier);

	public Emoji getEmoji(char c);

}

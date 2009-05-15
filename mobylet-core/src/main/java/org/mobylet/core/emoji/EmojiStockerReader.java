package org.mobylet.core.emoji;

public interface EmojiStockerReader {

	public EmojiStockerReader read(String path);

	public EmojiStocker get();

}

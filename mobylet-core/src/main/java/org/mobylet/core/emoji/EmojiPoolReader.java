package org.mobylet.core.emoji;

public interface EmojiPoolReader {

	public EmojiPoolReader read(String path);

	public EmojiPool get();

}

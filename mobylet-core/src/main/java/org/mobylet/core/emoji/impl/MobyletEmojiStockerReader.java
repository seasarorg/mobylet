package org.mobylet.core.emoji.impl;

import org.mobylet.core.emoji.EmojiStocker;
import org.mobylet.core.emoji.EmojiStockerReader;
import org.mobylet.core.util.XmlUtils;
import org.xml.sax.helpers.DefaultHandler;

public class MobyletEmojiStockerReader
	extends DefaultHandler implements EmojiStockerReader {

	protected EmojiStocker stocker;


	@Override
	public EmojiStocker get() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public EmojiStockerReader read(String path) {
		XmlUtils.parseSax(path, this);
		return this;
	}

}

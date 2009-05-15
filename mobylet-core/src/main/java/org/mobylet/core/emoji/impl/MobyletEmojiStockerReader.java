package org.mobylet.core.emoji.impl;

import java.util.Stack;

import org.mobylet.core.Carrier;
import org.mobylet.core.emoji.Emoji;
import org.mobylet.core.emoji.EmojiStocker;
import org.mobylet.core.emoji.EmojiStockerReader;
import org.mobylet.core.util.StringUtils;
import org.mobylet.core.util.XmlUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MobyletEmojiStockerReader
	extends DefaultHandler implements EmojiStockerReader, MobyletEmojiStockerXml {


	protected EmojiStocker stocker;

	protected Stack<String> tagStack;

	protected Emoji targetEmoji;

	protected Carrier relationCarrier;

	protected String relationValue;



	@Override
	public EmojiStocker get() {
		return stocker;
	}

	@Override
	public EmojiStockerReader read(String path) {
		XmlUtils.parseSax(path, this);
		return this;
	}

	@Override
	public void startDocument() throws SAXException {
		tagStack = new Stack<String>();
		super.startDocument();
	}

	@Override
	public void startElement(String url, String localName, String name,
			Attributes attributes) throws SAXException {
		tagStack.push(name);
		if (TAG_EMOJISTOCK.equals(name)) {
			Carrier carrier = Carrier.OTHER;
			String carrierStr = attributes.getValue(ATT_STOCKCARRIER);
			if (StringUtils.isNotEmpty(carrierStr)) {
				carrier = Carrier.valueOf(carrierStr);
			}
			stocker = new EmojiStocker(carrier);
		} else if (TAG_EMOJI.equals(name)) {
			String codeStr = attributes.getValue(ATT_CODE);
			if (StringUtils.isNotEmpty(codeStr) &&
					codeStr.startsWith("0x")) {
				char c = (char)Integer.parseInt(codeStr.substring(2), 16);
				targetEmoji = stocker.put(c);
			}
		} else if (TAG_RELATION.equals(name)) {
			Carrier carrier = Carrier.OTHER;
			String carrierStr = attributes.getValue(ATT_CARRIER);
			if (StringUtils.isNotEmpty(carrierStr)) {
				carrier = Carrier.valueOf(carrierStr);
			}
			relationCarrier = carrier;
		}
	}

	@Override
	public void endElement(String uri, String localName, String name) throws SAXException {
		tagStack.pop();
		if (TAG_RELATION.equals(name)) {
			if (StringUtils.isNotEmpty(relationValue)) {
				//TODO これから
			}
		}
	}

	@Override
	public void characters(char[] ac, int i, int j) throws SAXException {
		// TODO 自動生成されたメソッド・スタブ
		super.characters(ac, i, j);
	}

}

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

import java.util.Stack;

import org.mobylet.core.Carrier;
import org.mobylet.core.emoji.Emoji;
import org.mobylet.core.emoji.EmojiPool;
import org.mobylet.core.emoji.EmojiPoolFamily;
import org.mobylet.core.emoji.EmojiPoolReader;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.core.util.XmlUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MobyletEmojiPoolReader
	extends DefaultHandler implements EmojiPoolReader, MobyletEmojiPoolXml {


	protected EmojiPool pool;

	protected Stack<String> tagStack;

	protected Emoji targetEmoji;

	protected Carrier relationCarrier;

	protected String relationValue;



	@Override
	public EmojiPool get() {
		return pool;
	}

	@Override
	public EmojiPoolReader read(String path) {
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
			String carrierStr = attributes.getValue(ATT_CARRIER);
			if (StringUtils.isNotEmpty(carrierStr)) {
				carrier = Carrier.valueOf(carrierStr);
			}
			pool = SingletonUtils.get(EmojiPoolFamily.class).getEmojiPool(carrier);
		} else if (TAG_EMOJI.equals(name)) {
			String codeStr = attributes.getValue(ATT_CODE);
			if (StringUtils.isNotEmpty(codeStr) &&
					codeStr.startsWith("0x")) {
				char c = (char)Integer.parseInt(codeStr.substring(2), 16);
				targetEmoji = pool.putOnce(c);
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
				EmojiPool relationPool =
					SingletonUtils.get(EmojiPoolFamily.class).getEmojiPool(relationCarrier);
				Emoji e = null;
				if (relationValue.startsWith("0x")) {
					char c = (char)Integer.parseInt(relationValue.substring(2), 16);
					e = relationPool.getUnConstructed(c);
					if (e == null) {
						e = relationPool.putOnce(c);
					}
				} else {
					e = new Emoji(relationCarrier, relationValue);
				}
				targetEmoji.relate(relationCarrier, e);
				relationCarrier = null;
				relationValue = null;
			}
		} else if (TAG_EMOJI.equals(name)) {
			targetEmoji = null;
		}
	}

	@Override
	public void characters(char[] ac, int i, int j) throws SAXException {
		if (TAG_RELATION.equals(tagStack.peek())) {
			if (StringUtils.isEmpty(relationValue)) {
				relationValue = new String(ac, i, j);
			} else {
				relationValue += new String(ac, i, j);
			}
		}
	}

}

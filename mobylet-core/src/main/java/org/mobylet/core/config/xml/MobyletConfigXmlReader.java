package org.mobylet.core.config.xml;

import java.util.Stack;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.config.MobyletConfig;
import org.mobylet.core.initializer.MobyletInitializer;
import org.mobylet.core.initializer.impl.MobyletInitializerImpl;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.core.util.XmlUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class MobyletConfigXmlReader
	extends DefaultHandler implements MobyletConfigXml {

	public static final String CONFIG_PATH = "mobylet.xml";

	protected Stack<String> tagStack;

	protected MobyletConfig config;

	protected String value;


	public MobyletConfigXmlReader(String configDir) {
		config = new MobyletConfig(configDir);
		if (!XmlUtils.parseSax(configDir + CONFIG_PATH, this)) {
			config.addInitializer(new MobyletInitializerImpl());
			SingletonUtils.put(config);
		}
	}

	public MobyletConfig getConfig() {
		return config;
	}

	@Override
	public void startDocument() throws SAXException {
		tagStack = new Stack<String>();
	}

	@Override
	public void endDocument() throws SAXException {
		SingletonUtils.put(config);
	}

	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		tagStack.push(name);
	}

	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {
		tagStack.pop();
		//BaseDir
		if (name.equals(TAG_BASEDIR)) {
			String parent = tagStack.size() > 0 ? tagStack.peek() : null;
			if (parent == null) {
				value = null;
				return;
			}
			if (parent.equals(TAG_DEVICE) &&
					StringUtils.isNotEmpty(value)) {
				config.setDeviceDir(value);
			}
			else if (parent.equals(TAG_EMOJI) &&
					StringUtils.isNotEmpty(value)) {
				config.setEmojiDir(value);
			}
		}
		//Initializer
		else if (name.equals(TAG_CHAIN)) {
			String parent = tagStack.size() > 0 ? tagStack.peek() : null;
			if (parent == null) {
				value = null;
				return;
			}
			if (parent.equals(TAG_INITIALIZERS) &&
					StringUtils.isNotEmpty(value)) {
				Class<?> clazz = null;
				try {
					clazz = Class.forName(value);
				} catch (ClassNotFoundException e) {
					throw new MobyletRuntimeException(
							"INITIALIZERクラスが見つかりません = " + value, e);
				}
				if (MobyletInitializer.class.isAssignableFrom(clazz)) {
					MobyletInitializer initializer = null;
					try {
						initializer = MobyletInitializer.class.cast(clazz.newInstance());
					} catch (InstantiationException e) {
						throw new MobyletRuntimeException(
								"INITIALIZERクラスを生成出来ません = " + value, e);
					} catch (IllegalAccessException e) {
						throw new MobyletRuntimeException(
								"INITIALIZERクラスにアクセス出来ません = " + value, e);
					}
					config.addInitializer(initializer);
				}
			}
		}
		value = null;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String tag = tagStack.peek();
		if (tag.equals(TAG_CHAIN) ||
				tag.equals(TAG_BASEDIR)) {
			if (StringUtils.isEmpty(value)) {
				value = new String(ch, start, length);
			} else {
				value += new String(ch, start, length);
			}
		}
	}
}

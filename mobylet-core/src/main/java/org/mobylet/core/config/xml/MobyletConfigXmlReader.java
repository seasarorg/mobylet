/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package org.mobylet.core.config.xml;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.util.Stack;

import org.mobylet.core.Carrier;
import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.config.MobyletConfig;
import org.mobylet.core.config.enums.JSession;
import org.mobylet.core.config.enums.SecureGateway;
import org.mobylet.core.initializer.MobyletInitializer;
import org.mobylet.core.initializer.impl.MobyletInitializerImpl;
import org.mobylet.core.log.MobyletLogger;
import org.mobylet.core.type.ContentType;
import org.mobylet.core.util.ConfigUtils;
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

	protected String tmpProxyHost;

	protected String tmpProxyPort;



	public MobyletConfigXmlReader(String configDir) {
		MobyletLogger logger = SingletonUtils.get(MobyletLogger.class);
		if (logger != null && logger.isLoggable())
			logger.log("[mobylet] " + configDir + CONFIG_PATH + " の読み込み処理開始");
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
		MobyletLogger logger = SingletonUtils.get(MobyletLogger.class);
		if (logger != null && logger.isLoggable())
			logger.log("[mobylet] mobylet.xml が読み込まれました");
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
		//ParseValue
		if (StringUtils.isNotEmpty(value)) {
			value = ConfigUtils.parseValue(value);
		}
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
			else if (parent.equals(TAG_IP) &&
					StringUtils.isNotEmpty(value)) {
				config.setIpDir(value);
			}
		}
		//ImagePath
		else if (name.equals(TAG_IMAGE_PATH)) {
			String parent = tagStack.size() > 0 ? tagStack.peek() : null;
			if (parent == null) {
				value = null;
				return;
			}
			if (parent.equals(TAG_EMOJI) &&
					StringUtils.isNotEmpty(value)) {
				config.setEmojiImagePath(value);
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
		//ThroughCarrier
		else if (name.equals(TAG_CARRIER)) {
			String parent = tagStack.size() > 0 ? tagStack.peek() : null;
			if (parent == null) {
				value = null;
				return;
			}
			if (parent.equals(TAG_THROUGH) &&
					StringUtils.isNotEmpty(value)) {
				Carrier carrier = null;
				if (StringUtils.isEmpty(value) ||
						(carrier = Carrier.valueOf(value)) == null) {
					throw new MobyletRuntimeException(
							"指定したCarrierが見つかりません = " + value, null);
				}
				config.addThroughCarrier(carrier);
			}
		}
		//ProxyHost/Port
		else if (name.equals(TAG_HOST) ||
				name.equals(TAG_PORT)) {
			String parent = tagStack.size() > 0 ? tagStack.peek() : null;
			if (parent == null) {
				value = null;
				return;
			}
			if (parent.equals(TAG_PROXY) &&
					StringUtils.isNotEmpty(value)) {
				if (name.equals(TAG_HOST)) {
					tmpProxyHost = value;
				} else if (name.equals(TAG_PORT)) {
					tmpProxyPort = value;
				}
			}
		}
		//Proxy
		else if (name.equals(TAG_PROXY)) {
			int proxyPort = 80;
			try {
				proxyPort = Integer.parseInt(tmpProxyPort);
			} catch (Exception e) {
				MobyletLogger logger = SingletonUtils.get(MobyletLogger.class);
				if (logger != null && logger.isLoggable())
					logger.log("[mobylet] MobyletConfig : proxyのport番号が異常値のため[80]を使用します = " + tmpProxyPort);
			}
			SocketAddress proxyAddress =
				new InetSocketAddress(tmpProxyHost, proxyPort);
			config.setHttpProxy(new Proxy(Proxy.Type.HTTP, proxyAddress));
			tmpProxyHost = null;
			tmpProxyPort = null;
		}
		//ContentType
		else if (name.equals(TAG_CONTENT_TYPE)) {
			String parent = tagStack.size() > 0 ? tagStack.peek() : null;
			if (parent == null) {
				value = null;
				return;
			}
			if (parent.equals(TAG_DEFAULT) &&
					StringUtils.isNotEmpty(value)) {
				ContentType contentType = ContentType.valueOf(value);
				config.setContentType(contentType);
			}
		}
		//SslHeader
		else if (name.equals(TAG_SSL_HEADER)) {
			config.setSslHeader(value);
		}
		//JSession
		else if (name.equals(TAG_JSESSION)) {
			JSession jSession = JSession.valueOf(value);
			if (jSession != null) {
				config.setJSession(jSession);
			}
		}
		//SecureGateway
		else if (name.equals(TAG_SECURE_GATEWAY)) {
			SecureGateway secureGateway = SecureGateway.valueOf(value);
			if (secureGateway != null) {
				config.setSecureGateway(secureGateway);
			}
		}
		//MobyletClass
		else if (name.equals(TAG_MOBYLET_CLASS) ||
				name.equals(TAG_MOBYLET_CLASS_TMP)) {
			String parent = tagStack.size() > 0 ? tagStack.peek() : null;
			if (parent == null) {
				value = null;
				return;
			}
			if (parent.equals(TAG_IMPLEMENTS) &&
					StringUtils.isNotEmpty(value)) {
				config.setMobyletClass(value);
			}
		}
		//CSSInjection
		else if (name.equals(TAG_CSS_EXPAND)) {
			try {
				if (!value.trim().isEmpty()) {
					boolean useCSSExpand = Boolean.valueOf(value);
					config.setUseCSSExpand(useCSSExpand);
				}
			} catch (Exception e) {
				//NOP
				//旧設定用下位互換ロジックのため
				//例外発生の場合は新ロジックを適用
			}
		}
		else if (name.equals(TAG_CSS_EXPAND_EXECUTE)) {
			String parent = tagStack.size() > 0 ? tagStack.peek() : null;
			if (parent == null) {
				value = null;
				return;
			}
			if (parent.equals(TAG_CSS_EXPAND) &&
					StringUtils.isNotEmpty(value)) {
				boolean useCSSExpand = Boolean.valueOf(value.trim());
				config.setUseCSSExpand(useCSSExpand);
			}
		}
		else if (name.equals(TAG_CSS_EXPAND_REMOVED_CLASS)) {
			String parent = tagStack.size() > 0 ? tagStack.peek() : null;
			if (parent == null) {
				value = null;
				return;
			}
			if (parent.equals(TAG_CSS_EXPAND) &&
					StringUtils.isNotEmpty(value)) {
				boolean isRemovedClass = Boolean.valueOf(value.trim());
				config.setCSSExpandRemovedClass(isRemovedClass);
			}
		}
		value = null;
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String tag = tagStack.peek();
		if (tag.equals(TAG_CHAIN) ||
				tag.equals(TAG_BASEDIR) ||
				tag.equals(TAG_IMAGE_PATH) ||
				tag.equals(TAG_CARRIER) ||
				tag.equals(TAG_CONTENT_TYPE) ||
				tag.equals(TAG_SSL_HEADER) ||
				tag.equals(TAG_JSESSION) ||
				tag.equals(TAG_SECURE_GATEWAY) ||
				tag.equals(TAG_MOBYLET_CLASS) ||
				tag.equals(TAG_MOBYLET_CLASS_TMP) ||
				tag.equals(TAG_CSS_EXPAND) ||
				tag.equals(TAG_CSS_EXPAND_EXECUTE) ||
				tag.equals(TAG_CSS_EXPAND_REMOVED_CLASS) ||
				tag.equals(TAG_HOST) ||
				tag.equals(TAG_PORT)) {
			if (StringUtils.isEmpty(value)) {
				value = new String(ch, start, length);
			} else {
				value += new String(ch, start, length);
			}
		}
	}
}

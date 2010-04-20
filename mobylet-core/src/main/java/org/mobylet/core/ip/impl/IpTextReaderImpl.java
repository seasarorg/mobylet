/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.mobylet.core.ip.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.mobylet.core.Carrier;
import org.mobylet.core.config.MobyletConfig;
import org.mobylet.core.define.DefCharset;
import org.mobylet.core.ip.IpAddress;
import org.mobylet.core.ip.IpAddressList;
import org.mobylet.core.ip.IpTextReader;
import org.mobylet.core.log.MobyletLogger;
import org.mobylet.core.util.ResourceUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;

public class IpTextReaderImpl implements IpTextReader {

	private static final String EXTENSION = "EXTENSION";

	private static final String SUFIX = ".txt";

	protected Map<Carrier, IpAddressList> ipMap;


	public IpTextReaderImpl() {
		initialize();
	}

	@Override
	public IpAddressList getIpAddressList(Carrier carrier) {
		return ipMap.get(carrier);
	}

	protected void initialize() {
		ipMap = new HashMap<Carrier, IpAddressList>();
		IpAddressList extensionIpList = readIpText(EXTENSION);
		putIpList(Carrier.DOCOMO, extensionIpList);
		putIpList(Carrier.AU, extensionIpList);
		putIpList(Carrier.SOFTBANK, extensionIpList);
	}

	protected void putIpList(Carrier carrier, IpAddressList extensionList) {
		IpAddressList ipList = readIpText(carrier.name());
		ipList.addAll(extensionList);
		ipMap.put(carrier, ipList);
	}

	protected IpAddressList readIpText(String type) {
		IpAddressList ipList = new IpAddressList();
		if (StringUtils.isEmpty(type)) {
			return ipList;
		}
		MobyletConfig config = SingletonUtils.get(MobyletConfig.class);
		String path = config.getIpDir() + type + SUFIX;
		MobyletLogger logger = SingletonUtils.get(MobyletLogger.class);
		if (logger != null && logger.isLoggable())
			logger.log("[mobylet] " + path + " の読み込み処理開始");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(
					new InputStreamReader(
							ResourceUtils.getResourceFileOrInputStream(path),
							DefCharset.UTF8));
			String line = null;
			int count = 0;
			while ((line = reader.readLine()) != null) {
				if (StringUtils.isNotEmpty(line) &&
						!line.startsWith("#")) {
					ipList.add(new IpAddress(line.trim()));
					count++;
				}
			}
			if (logger != null && logger.isLoggable())
				logger.log("[mobylet] " + path + " が読み込まれました - IPアドレス帯域数 = " + count);
		} catch (UnsupportedEncodingException e) {
			if (logger != null && logger.isLoggable())
				logger.log("[mobylet] 文字コード[" + DefCharset.UTF8 + "]が利用できません");
		} catch (Exception e) {
			if (logger != null && logger.isLoggable())
				logger.log("[mobylet] IPアドレステキスト [" + path + "]が読み込めません");
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					//NOP
				}
			}
		}
		return ipList;
	}

}

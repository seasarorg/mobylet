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
package org.mobylet.core.dialect.impl;

import java.nio.charset.Charset;

import javax.servlet.http.HttpServletResponse;

import org.mobylet.core.dialect.MobyletDialect;
import org.mobylet.core.ip.IpAddressList;
import org.mobylet.core.ip.IpTextReader;
import org.mobylet.core.selector.CharsetSelector;
import org.mobylet.core.type.SmartPhoneType;
import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;

public abstract class AbstractDialect implements MobyletDialect {

	protected HttpServletResponse response;

	protected CharsetSelector charsetSelector;

	protected IpTextReader ipTextReader;

	protected String contentTypeString = null;

	protected String xContentTypeString = null;


	protected AbstractDialect() {
		charsetSelector = SingletonUtils.get(CharsetSelector.class);
		ipTextReader = SingletonUtils.get(IpTextReader.class);
	}

	@Override
	public String getCharsetName() {
		return SingletonUtils.get(CharsetSelector.class).getCharsetName(getCarrier());
	}

	@Override
	public Charset getCharset() {
		return SingletonUtils.get(CharsetSelector.class).getCharset(getCarrier());
	}

	@Override
	public String getContentCharsetName() {
		return getCharsetName();
	}

	@Override
	public String getCharacterEncodingCharsetName() {
		return SingletonUtils.get(CharsetSelector.class)
				.getCharacterEncodingCharsetName(getCarrier());
	}

	@Override
	public String getContentTypeString() {
		if (StringUtils.isEmpty(contentTypeString)) {
			contentTypeString = "text/html; charset=" + getContentCharsetName();
		}
		return contentTypeString;
	}

	@Override
	public String getXContentTypeString() {
		if (StringUtils.isEmpty(xContentTypeString)) {
			xContentTypeString =
				"application/xhtml+xml; charset=" + getContentCharsetName();
		}
		return xContentTypeString;
	}

	@Override
	public boolean isGatewayIp() {
		IpAddressList ipList =
			ipTextReader.getIpAddressList(getCarrier());
		if (ipList == null) {
			return false;
		} else {
			return ipList.containsIp(RequestUtils.get().getRemoteAddr());
		}
	}

	@Override
	public SmartPhoneType getSmartPhoneType() {
		String userAgent = RequestUtils.getUserAgent();
		if (StringUtils.isEmpty(userAgent)) {
			return null;
		} else {
			if (userAgent.indexOf("iPhone") >= 0) {
				return SmartPhoneType.IPHONE;
			} else if (userAgent.indexOf("Android") >= 0) {
				return SmartPhoneType.ANDROID;
			} else {
				return null;
			}
		}
	}

	@Override
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

}

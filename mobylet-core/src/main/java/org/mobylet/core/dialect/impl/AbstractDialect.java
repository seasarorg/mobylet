package org.mobylet.core.dialect.impl;

import java.nio.charset.Charset;

import org.mobylet.core.dialect.MobyletDialect;
import org.mobylet.core.ip.IpAddressList;
import org.mobylet.core.ip.IpTextReader;
import org.mobylet.core.selector.CharsetSelector;
import org.mobylet.core.type.SmartPhoneType;
import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;

public abstract class AbstractDialect implements MobyletDialect {

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

}

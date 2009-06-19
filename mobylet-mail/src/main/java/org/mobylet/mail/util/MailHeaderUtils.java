package org.mobylet.mail.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mobylet.core.Carrier;
import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.mail.MailConstants;
import org.mobylet.mail.selector.MailCharsetSelector;

public class MailHeaderUtils implements MailConstants {

	private static final Pattern PATTERN_ENCODEDHEADER =
		Pattern.compile("([\\p{Cntrl}]+ )?=\\?[a-zA-Z0-9_-]+\\?B\\?[a-zA-Z0-9/+=\\r\\n]+\\?=");

	private static final Pattern PATTERN_ENCODEDHEADER_CHARSET =
		Pattern.compile("=\\?[a-zA-Z0-9_-]+\\?");

	private static final Pattern PATTERN_ENCODEDHEADER_BODY =
		Pattern.compile("\\?B\\?[a-zA-Z0-9/+=\\r\\n]+\\?=");



	public static String encodeHeaderString(
			Carrier carrier, String srcString) {
		if (StringUtils.isEmpty(srcString)) {
			return "";
		}
		//Ascii-Check
		boolean isAsciiAll = true;
		for (char c : srcString.toCharArray()) {
			if (c > 0xFF) {
				isAsciiAll = false;
				break;
			}
		}
		if (isAsciiAll) {
			return srcString;
		}
		//Get-Charset
		MailCharsetSelector charsetSelector =
			SingletonUtils.get(MailCharsetSelector.class);
		String encodingCharset = charsetSelector.getEncodingCharset(carrier);
		String notifyCharset = charsetSelector.getNotifyCharset(carrier);
		byte[] encodedBytes = null;
		try {
			encodedBytes = MailEmojiUtils.convert(
					carrier, srcString).getBytes(encodingCharset);
		} catch (UnsupportedEncodingException e) {
			throw new MobyletRuntimeException(
					"Unsupported Charset = " + encodingCharset, e);
		}
		String base64String = Base64Utils.encode(encodedBytes);
		String base64EncodedString = "=?" + notifyCharset + "?B?" + base64String + "?=";
		base64EncodedString = base64EncodedString.replaceAll("[\\r\\n]", "");
		return base64EncodedString;
	}

	public static String decodeHeaderString(
			Carrier carrier, String srcString) {
		if (StringUtils.isEmpty(srcString)) {
			return "";
		}
		//Find-Base64String
		Matcher encodedStringMatcher = PATTERN_ENCODEDHEADER.matcher(srcString);
		StringBuffer sBuf = new StringBuffer();
		StringBuilder repBuf = new StringBuilder();
		Charset charset = null;
		while (encodedStringMatcher.find()) {
			String encodedString = encodedStringMatcher.group();
			Matcher charsetMatcher = PATTERN_ENCODEDHEADER_CHARSET.matcher(encodedString);
			//Charset
			if (charsetMatcher.find()) {
				String charsetString = charsetMatcher.group();
				charsetString = charsetString.substring(2, charsetString.length() - 1);
				charset = Charset.forName(charsetString);
				if (charset.contains(Charset.forName(CHARSET_JIS_STANDARD))) {
					if (carrier == Carrier.AU) {
						//TODO auの文字コードに変更
						charset = Charset.forName(CHARSET_ISO_2022_1);
					} else {
						charset = Charset.forName(CHARSET_ISO_2022_1);
					}
				}
			}
			//HeaderBody
			Matcher bodyMatcher = PATTERN_ENCODEDHEADER_BODY.matcher(encodedString);
			if (bodyMatcher.find()) {
				String bodyString = bodyMatcher.group();
				bodyString = bodyString.substring(3, bodyString.length() - 2);
				byte[] decodedBodyBytes = Base64Utils.decode(bodyString);
				String decodedBodyString = null;
				try {
					decodedBodyString = new String(decodedBodyBytes, charset.name());
				} catch (UnsupportedEncodingException e) {
					throw new MobyletRuntimeException(
							"文字コード変換に失敗 charset = " + charset, e);
				}
				encodedStringMatcher.appendReplacement(sBuf, decodedBodyString);
				String trimString = sBuf.toString().trim();
				repBuf.append(trimString);
				sBuf.delete(0, sBuf.length());
			}
		}
		encodedStringMatcher.appendTail(sBuf);
		String trimString = sBuf.toString().trim();
		repBuf.append(trimString);
		return repBuf.toString().trim();
	}
}

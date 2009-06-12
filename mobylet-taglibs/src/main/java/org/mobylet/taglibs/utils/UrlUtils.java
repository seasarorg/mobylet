package org.mobylet.taglibs.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.mobylet.core.MobyletFactory;
import org.mobylet.core.util.StringUtils;
import org.mobylet.taglibs.MobyletRenderingException;

public class UrlUtils {

	public static String Q = "?";

	public static String AMP = "&";

	public static String EQ = "=";

	public static String SC = ";";

	public static String SP = "#";

	public static String addParameter(String url, String pKey, String pValue) {
		return addParameter(url, pKey, pValue, true);
	}

	public static String addParameter(String url, String pKey, String pValue, boolean sanitize) {
		if (StringUtils.isEmpty(pKey)) {
			return url;
		}
		String encodedValue = null;
		if (sanitize) {
			encodedValue = encodeUrl(pValue);
		} else {
			encodedValue = pValue;
		}
		if (StringUtils.isNotEmpty(url)) {
			if (url.contains(Q)) {
				return url + AMP + pKey + EQ + encodedValue;
			} else {
				return url + Q + pKey + EQ + encodedValue;
			}
		} else {
			return Q + pKey + EQ + encodedValue;
		}
	}

	public static String encodeUrl(String url) {
		String charsetName =
			MobyletFactory.getInstance().getDialect().getCharsetName();
		String encodedUrl = null;
		try {
			encodedUrl = (url == null ? "" : URLEncoder.encode(url, charsetName));
		} catch (UnsupportedEncodingException e) {
			throw new MobyletRenderingException(
					"URLエンコード中に例外発生 " +
					"charset=[" + charsetName + "] " +
					"string=[" + url + "]", e);
		}
		return encodedUrl;
	}

	public static String addSession(String url, String sessionId) {
		if(url == null || sessionId == null) {
			return url;
		}
		String path = url;
		String query = "";
		String anchor = "";
		int question = url.indexOf(Q);
		if(question >= 0) {
			path = url.substring(0, question);
			query = url.substring(question);
		}
		int pound = path.indexOf(SP);
		if(pound >= 0) {
			anchor = path.substring(pound);
			path = path.substring(0, pound);
		}
		StringBuilder sb = new StringBuilder(path);
		if(sb.length() > 0) {
			sb.append(SC);
			sb.append("jsessionid");
			sb.append(EQ);
			sb.append(sessionId);
		}
		sb.append(anchor);
		sb.append(query);
		return sb.toString();
	}

}

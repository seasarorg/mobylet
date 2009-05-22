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


	public static String addParameter(String url, String pKey, String pValue) {
		if (StringUtils.isEmpty(pKey)) {
			return url;
		}
		String charsetName =
			MobyletFactory.getInstance().getDialect().getCharsetName();
		String encodedValue = null;
		try {
			encodedValue = (pValue == null ? "" : URLEncoder.encode(pValue, charsetName));
		} catch (UnsupportedEncodingException e) {
			throw new MobyletRenderingException(
					"URLエンコード中に例外発生 " +
					"charset=[" + charsetName + "] " +
					"string=[" + pValue + "]", e);
		}
		if (StringUtils.isNotEmpty(url)) {
			if (url.contains(Q)) {
				return AMP + pKey + EQ + encodedValue;
			} else {
				return Q + pKey + EQ + encodedValue;
			}
		} else {
			return Q + pKey + EQ + encodedValue;
		}
	}
}

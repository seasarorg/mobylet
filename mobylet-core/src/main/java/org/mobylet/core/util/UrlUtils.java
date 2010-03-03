package org.mobylet.core.util;

import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;

import org.mobylet.core.MobyletFactory;
import org.mobylet.core.http.MobyletFilter.NativeUrl;

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
		Charset charset =
			MobyletFactory.getInstance().getDialect().getCharset();
		String encodedUrl = null;
		encodedUrl = (url == null ? "" : UrlEncoder.encode(url, charset));
		return encodedUrl;
	}

	public static String encodeIAreaUrl(String url) {
		if (StringUtils.isEmpty(url)) {
			return url;
		}
		String encodedUrl = getAbsoluteUrl(url);
		int q = encodedUrl.indexOf(Q);
		if (q >= 0) {
			Charset charset =
				MobyletFactory.getInstance().getDialect().getCharset();
			String query = encodedUrl.substring(q+1);
			encodedUrl = encodedUrl.substring(0, q);
			String[] paramEntries = query.split(AMP);
			for (int i=0; i<paramEntries.length; i++) {
				encodedUrl =
					encodedUrl + AMP + "arg" + i + EQ + UrlEncoder.encode(paramEntries[i], charset);
			}
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

	public static String getCurrentUrl() {
		HttpServletRequest request = RequestUtils.get();
		if (request == null) {
			return null;
		}
		String url = null;
		NativeUrl nativeUrl = null;
		if ((nativeUrl =
			RequestUtils.getMobyletContext().get(NativeUrl.class)) != null) {
			url = nativeUrl.getUrl();
		} else {
			url = request.getRequestURL().toString();
		}
		if (request.isSecure() &&
				url.startsWith("http:")) {
			url = url.replace("http:", "https:");
		}
		return url;
	}

	public static String getAbsoluteUrl(String path) {
		if (StringUtils.isEmpty(path)) {
			return getCurrentUrl();
		}
		if (path.startsWith("http://") ||
				path.startsWith("https://")) {
			return path;
		}
		if (path.startsWith("/")) {
			String url = getCurrentUrl();
			int domainSlash = url.indexOf('/', 8);
			if (domainSlash < 0) {
				return url + path;
			} else {
				return url.substring(0, domainSlash) + path;
			}
		} else {
			String url = getCurrentUrl();
			int lastDir = url.lastIndexOf('/');
			return url.substring(0, lastDir+1) + path;
		}
	}

}

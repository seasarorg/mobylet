package org.mobylet.core.http;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.mobylet.core.MobyletFactory;
import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.util.InputStreamUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.core.util.UrlDecoder;

public class MobyletRequest extends HttpServletRequestWrapper {


	public static final String POST = "POST";

	public static final String KEY = MobyletRequest.class.getName();

	public static final String CONTENT_TYPE_URLENCODED =
		"application/x-www-form-urlencoded";


	protected HttpServletRequest request;

	protected Map<String, Object> parametersMap =
		Collections.synchronizedMap(new HashMap<String, Object>());

	protected boolean isParsedParameters = false;

	protected String queryString;


	@SuppressWarnings("unchecked")
	public MobyletRequest(HttpServletRequest request) {
		super(request);
		this.request = request;
		if (request != null &&
				request.getAttribute(KEY) != null) {
			parametersMap.putAll(request.getParameterMap());
		} else {
			setAttribute(KEY, new Object().hashCode());
		}
	}

	@Override
	public String getParameter(String name) {
		if (isParsable()) {
			parseParameters();
		}
		synchronized (parametersMap) {
			Object value = parametersMap.get(name);
			if (value == null) {
				return null;
			} else if (value instanceof String[]) {
				return ((String[])value)[0];
			} else if (value instanceof String){
				return (String)value;
			} else {
				return value.toString();
			}
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map getParameterMap() {
		if (isParsable()) {
			parseParameters();
		}
		return parametersMap;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Enumeration getParameterNames() {
		if (isParsable()) {
			parseParameters();
		}
		return new Enumerator(parametersMap.keySet());
	}

	@Override
	public String[] getParameterValues(String name) {
		if (isParsable()) {
			parseParameters();
		}
		synchronized (parametersMap) {
			Object value = parametersMap.get(name);
			if (value == null) {
				return null;
			} else if (value instanceof String[]) {
				return ((String[])value);
			} else if (value instanceof String){
				String[] values = new String[1];
				values[0] = (String)value;
				return values;
			} else {
				String[] values = new String[1];
				values[0] = value.toString();
				return values;
			}
		}
	}

	protected void parseParameters() {
		synchronized (parametersMap) {
			String queryString = getQueryString();
			if (StringUtils.isNotEmpty(queryString)) {
				mergeParametersString(queryString);
			}
			int contentLength = getContentLength();
			if (POST.equalsIgnoreCase(getMethod()) &&
					contentLength > 0) {
				String contentType = getContentType();
				if (StringUtils.isEmpty(contentType)) {
					return;
				}
				int index = 0;
				if ((index = contentType.indexOf(';')) > 0) {
					contentType = contentType.substring(0, index).trim();
				} else {
					contentType = contentType.trim();
				}
				if (contentType.equals(CONTENT_TYPE_URLENCODED)) {
					try {
						InputStream inputStream = getInputStream();
						if (inputStream != null) {
							mergeParametersString(
									new String(
											InputStreamUtils.getBytesUnClose(
													inputStream,
													contentLength))
									);
						}
					} catch (Exception e) {
						throw new MobyletRuntimeException("パラメータマージ処理時に例外発生", e);
					}
				}
			}
			Set<Entry<String, Object>> entrySet = parametersMap.entrySet();
			for (Entry<String, Object> entry : entrySet) {
				Object value = entry.getValue();
				if (value instanceof Set) {
					Set<?> set = Set.class.cast(value);
					String[] array = set.toArray(new String[set.size()]);
					parametersMap.put(entry.getKey(), array);
				}
			}
			isParsedParameters = true;
		}
	}

	@SuppressWarnings("unchecked")
	protected void mergeParametersString(String q) {
		if (StringUtils.isEmpty(q)) {
			return;
		}
		Charset orgCharset =
			MobyletFactory.getInstance().getDialect().getCharset();
		String[] keyValues = q.split("[&]");
		for (String keyValue : keyValues) {
			int index = 0;
			if ((index = keyValue.indexOf('=')) > 0) {
				String key = UrlDecoder.decode(
						keyValue.substring(0, index), orgCharset);
				String val = UrlDecoder.decode(
						keyValue.substring(index + 1), orgCharset);
				if (parametersMap.containsKey(key)) {
					Object valueSet = parametersMap.get(key);
					if (valueSet instanceof Set) {
						((Set<String>)valueSet).add(val);
					} else if (valueSet instanceof String[]) {
						String[] varList = (String[])valueSet;
						Set<String> tmpSet = new HashSet<String>();
						for (String var : varList) {
							tmpSet.add(var);
						}
						tmpSet.add(val);
						valueSet = tmpSet;
					}
					parametersMap.put(key, valueSet);
				} else {
					Set<String> valSet = new HashSet<String>();
					valSet.add(val);
					parametersMap.put(key, valSet);
				}
			}
		}
	}

	protected boolean isParsable() {
		String nowQuery = getQueryString();
		if (StringUtils.isNotEmpty(nowQuery) &&
				!nowQuery.equals(queryString)) {
			queryString = nowQuery;
			return true;
		}
		return !isParsedParameters;
	}

	public static class Enumerator<E> implements Enumeration<E> {

		protected Iterator<E> iterator;


		public Enumerator(Set<E> set) {
			this.iterator = set.iterator();
		}

		@Override
		public boolean hasMoreElements() {
			return iterator.hasNext();
		}

		@Override
		public E nextElement() {
			return iterator.next();
		}

	}

}

package org.mobylet.core.http;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.mobylet.core.MobyletFactory;
import org.mobylet.core.util.InputStreamUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.core.util.UrlDecoder;

public class MobyletRequest extends HttpServletRequestWrapper {


	public static final String POST = "POST";

	public static final String CONTENT_TYPE_URLENCODED =
		"application/x-www-form-urlencoded";


	protected HttpServletRequest request;

	protected Map<String, Object> parametersMap;

	protected boolean isParsedParameters = false;



	public MobyletRequest(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getParameter(String name) {
		if (!isParsedParameters) {
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
		if (!isParsedParameters) {
			parseParameters();
		}
		return parametersMap;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Enumeration getParameterNames() {
		if (!isParsedParameters) {
			parseParameters();
		}
		return new Enumerator(parametersMap.keySet());
	}

	@Override
	public String[] getParameterValues(String name) {
		if (!isParsedParameters) {
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
		if (parametersMap == null) {
			parametersMap = new HashMap<String, Object>();
		}
		synchronized (parametersMap) {
			String queryString = getQueryString();
			if (StringUtils.isNotEmpty(queryString)) {
				mergeParametersString(queryString);
				Logger.getLogger("REQUEST").log(Level.INFO, queryString);
			}
			if (POST.equalsIgnoreCase(getMethod()) &&
					getContentLength() > 0) {
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
						mergeParametersString(
								new String(
										InputStreamUtils.getAllBytes(getInputStream())
										)
								);
					} catch (IOException e) {
						//NOP
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
					}
				} else {
					Set<String> valSet = new HashSet<String>();
					valSet.add(val);
					parametersMap.put(key, valSet);
				}
			}
		}
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

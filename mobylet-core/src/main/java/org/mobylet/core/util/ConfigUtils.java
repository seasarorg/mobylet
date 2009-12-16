package org.mobylet.core.util;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;

import org.mobylet.core.config.enums.ConfigKeyword;
import org.mobylet.core.log.MobyletLogger;

public class ConfigUtils {

	public static final Pattern PAT_VARIABLE =
		Pattern.compile("[$]{1}[{]{1}[a-zA-Z0-9_.]+[}]{1}");


	public static String parseValue(String value) {
		if (StringUtils.isEmpty(value)) {
			return value;
		}
		FilterConfig filterConfig =
			SingletonUtils.get(FilterConfig.class);
		ServletContext servletContext =
			SingletonUtils.get(ServletContext.class);
		Matcher matcher = PAT_VARIABLE.matcher(value);
		StringBuffer buf = new StringBuffer();
		while (matcher.find()) {
			String matchedSeq = matcher.group();
			String word = matchedSeq.substring(2, matchedSeq.length()-1);
			String parsedVal = "";
			//MobyletFilter-getInitParameter
			if (filterConfig != null) {
				parsedVal = filterConfig.getInitParameter(word);
			}
			//ServletContext-getAttribute
			if (StringUtils.isEmpty(parsedVal) &&
					servletContext != null) {
				//KeywordCheck
				parsedVal = getKeywordValue(word, servletContext);
				if (StringUtils.isEmpty(parsedVal)) {
					//getAttribute
					Object obj = servletContext.getAttribute(word);
					if (obj != null) {
						parsedVal = obj.toString();
					}
				}
			}
			//SystemProperty
			if (StringUtils.isEmpty(parsedVal)) {
				parsedVal = System.getProperty(word);
			}
			//Escape
			if (StringUtils.isNotEmpty(parsedVal)) {
				parsedVal = StringUtils.escape(parsedVal);
			}
			if (parsedVal == null) {
				matcher.appendReplacement(buf, "\\" + matchedSeq);
				MobyletLogger logger = SingletonUtils.get(MobyletLogger.class);
				if (logger != null && logger.isLoggable())
					logger.log("[mobylet] InjectionConfig - 値が見つからない変数  = " + matchedSeq);
			} else {
				matcher.appendReplacement(buf, parsedVal);
			}
		}
		matcher.appendTail(buf);
		return buf.toString();
	}


	protected static String getKeywordValue(
			String value, ServletContext servletContext) {
		for (ConfigKeyword keyword : ConfigKeyword.values()) {
			if (value.equals(keyword.getKeyword())) {
				switch (keyword) {
				case CONTEXT_PATH:
					//Servlet API Ver 2.5
					try {
						Method method =
							ServletContext.class.getMethod(
									"getContextPath", new Class<?>[0]);
						Object obj = method.invoke(servletContext, new Object[0]);
						if (obj != null) {
							return obj.toString();
						}
					} catch (Exception e) {
						//NOP
					}
					break;
				case DOC_BASE:
					return servletContext.getRealPath("");
				}
			}
		}
		return null;
	}
}

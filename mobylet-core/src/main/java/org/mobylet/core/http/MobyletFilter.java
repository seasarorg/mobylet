/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.mobylet.core.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.dialect.MobyletDialect;
import org.mobylet.core.util.RequestUtils;

public class MobyletFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//request/response
		HttpServletRequest httpRequest = HttpServletRequest.class.cast(request);
		HttpServletResponse httpResponse = HttpServletResponse.class.cast(response);
		//requestScope
		RequestUtils.set(httpRequest);
		try {
			processFilter(chain, httpRequest, httpResponse);
		} finally {
			RequestUtils.remove();
		}
	}

	protected void processFilter(FilterChain chain,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException, IOException, ServletException {
		//Mobylet
		Mobylet m = MobyletFactory.getInstance();
		//Dialect
		MobyletDialect dialect = m.getDialect();
		//Charset
		String charsetName = dialect.getCharsetName();
		request.setCharacterEncoding(charsetName);
		//doChain
		MobyletResponse mResponse = new MobyletResponse(response, dialect);
		chain.doFilter(request, mResponse);
		mResponse.flush();
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		MobyletInitializer.initialize();
	}

}

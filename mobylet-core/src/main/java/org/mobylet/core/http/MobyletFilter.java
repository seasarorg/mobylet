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
import java.nio.charset.Charset;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mobylet.core.Carrier;
import org.mobylet.core.config.MobyletConfig;
import org.mobylet.core.config.xml.MobyletConfigXmlReader;
import org.mobylet.core.define.DefCharset;
import org.mobylet.core.detector.CarrierDetector;
import org.mobylet.core.dialect.MobyletDialect;
import org.mobylet.core.initializer.MobyletInitializer;
import org.mobylet.core.selector.DialectSelector;
import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.SessionUtils;
import org.mobylet.core.util.SingletonUtils;

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
		//SessionSubstitute
		SessionUtils.substitute();
		try {
			processFilter(chain, httpRequest, httpResponse);
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			RequestUtils.remove();
		}
	}

	protected void processFilter(FilterChain chain,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException, IOException, ServletException {
		//Config
		MobyletConfig config = SingletonUtils.get(MobyletConfig.class);
		//Carrier
		Carrier carrier =
			SingletonUtils.get(CarrierDetector.class).getCarrier(request);
		//Dialect
		MobyletDialect dialect =
			SingletonUtils.get(DialectSelector.class).getDialect(carrier);
		//DummyCharset
		String charsetName = dialect.getNativeCharsetName();
		request.setCharacterEncoding(charsetName);
		//WrapRequest&parseRequest
		if (carrier != Carrier.OTHER) {
			request = new MobyletRequest(request);
			MobyletRequest.class.cast(request).parseParameters();
		}
		//ThroughCarrier
		if (config.containsThroughCarrier(carrier)) {
			//doChain
			chain.doFilter(request, response);
		} else {
			//doChain
			MobyletResponse mResponse = new MobyletResponse(response, dialect);
			chain.doFilter(request, mResponse);
			mResponse.flush();
		}
	}


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		initSingletonContainer(filterConfig);
		initDefaultCharset(filterConfig);
		initInitializer(filterConfig);
	}


	protected void initSingletonContainer(FilterConfig filterConfig) {
		if (!SingletonUtils.isInitialized()) {
			SingletonUtils.initialize(null);
		}
		if (filterConfig != null) {
			SingletonUtils.put(filterConfig);
			SingletonUtils.put(filterConfig.getServletContext());
		}
	}

	protected void initInitializer(FilterConfig filterConfig) {
		String configDir = "";
		if (filterConfig != null) {
			configDir = filterConfig.getInitParameter("mobylet.config.dir");
			if (configDir == null) {
				configDir = "";
			}
		}
		MobyletConfigXmlReader configXml =
			new MobyletConfigXmlReader(configDir);
		MobyletConfig config = configXml.getConfig();
		//初期化
		for (MobyletInitializer initializer : config.getInitializers()) {
			initializer.initialize();
			SingletonUtils.put(initializer);
		}
	}

	protected void initDefaultCharset(FilterConfig filterConfig) {
		String defCharsetName = null;
		if (filterConfig != null &&
				(defCharsetName =
					filterConfig.getInitParameter("encoding")) != null) {
			Charset defCharset = Charset.forName(defCharsetName);
			if (defCharset != null) {
				SingletonUtils.put(defCharset);
			}
		}
		if (SingletonUtils.get(Charset.class) == null) {
			SingletonUtils.put(Charset.forName(DefCharset.WIN31J));
		}
	}
}

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
import java.util.List;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mobylet.core.Carrier;
import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.config.MobyletConfig;
import org.mobylet.core.define.DefPath;
import org.mobylet.core.define.DefProperties;
import org.mobylet.core.detector.CarrierDetector;
import org.mobylet.core.dialect.MobyletDialect;
import org.mobylet.core.initializer.MobyletInitializer;
import org.mobylet.core.initializer.impl.MobyletInitializerImpl;
import org.mobylet.core.selector.DialectSelector;
import org.mobylet.core.util.CSVSplitUtils;
import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.ResourceUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;

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
		//Carrier
		Carrier carrier =
			SingletonUtils.get(CarrierDetector.class).getCarrier(request);
		//Dialect
		MobyletDialect dialect =
			SingletonUtils.get(DialectSelector.class).getDialect(carrier);
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
		initSingletonContainer(filterConfig);
		initInitializer(filterConfig);
		initChainInitializer(filterConfig);
	}


	protected void initSingletonContainer(FilterConfig filterConfig) {
		if (!SingletonUtils.isInitialized()) {
			SingletonUtils.initialize(null);
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
		SingletonUtils.put(new MobyletConfig(configDir));
		Properties properties = new Properties();
		String configPath = configDir + DefPath.CONFIG_PATH;
		try {
			properties.load(
					ResourceUtils.getResourceFileOrInputStream(configPath));
		} catch (Exception e) {
			throw new MobyletRuntimeException(
					configPath+"の読み込みに失敗しました", e);
		}
		String className = properties.getProperty(DefProperties.KEY_INITIALIZER);
		MobyletInitializer initializer = null;
		if (StringUtils.isEmpty(className)) {
			initializer =
				SingletonUtils.get(MobyletInitializer.class);
			if (initializer == null) {
				initializer = new MobyletInitializerImpl();
			}
		} else {
			try {
				Class<?> initializerClass = Class.forName(className);
				Object initializerObj = initializerClass.newInstance();
				if (initializerObj instanceof MobyletInitializer) {
					initializer =
						MobyletInitializer.class.cast(initializerObj);
				} else {
					throw new MobyletRuntimeException(
							"Class["+className+"]はMobyletInitializerを実装していません", null);
				}
			} catch (ClassNotFoundException e) {
				throw new MobyletRuntimeException(
						"Class["+className+"]が見つかりません", e);
			} catch (InstantiationException e) {
				throw new MobyletRuntimeException(
						"Class["+className+"]のインスタンスを生成できません", e);
			} catch (IllegalAccessException e) {
				throw new MobyletRuntimeException(
						"Class["+className+"]にアクセスできません", e);
			}
		}
		//初期化
		if (initializer != null) {
			initializer.readProperties(properties);
			initializer.initialize();
			SingletonUtils.put(initializer);
		}
	}


	protected void initChainInitializer(FilterConfig filterConfig) {
		String strInitializers = null;
		if (filterConfig != null) {
			strInitializers = filterConfig.getInitParameter("mobylet.chain.initializers");
			if (StringUtils.isEmpty(strInitializers)) {
				return;
			}
		} else {
			return;
		}
		//チェイン初期化
		List<String> initializers = CSVSplitUtils.splitLine(strInitializers);
		for (String strInitializer : initializers) {
			try {
				Class<?> clazz = Class.forName(strInitializer);
				Object initializerObj = clazz.newInstance();
				if (initializerObj instanceof MobyletInitializer) {
					MobyletInitializer initializer =
						MobyletInitializer.class.cast(initializerObj);
					initializer.initialize();
				}
			} catch (Exception e) {
				throw new MobyletRuntimeException(
						"[ChainInitialize]初期化に失敗しました className = " + strInitializer, e);
			}
		}
	}

}

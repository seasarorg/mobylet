/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.mobylet.core.util;

import javax.servlet.http.HttpServletRequest;

import org.mobylet.core.holder.RequestHolder;
import org.mobylet.core.http.MobyletContext;
import org.mobylet.core.type.DispatchType;

public class RequestUtils {

	public static HttpServletRequest get() {
		return SingletonUtils.get(RequestHolder.class).get();
	}

	public static void set(HttpServletRequest request) {
		SingletonUtils.get(RequestHolder.class).set(request);
	}

	public static void remove() {
		SingletonUtils.get(RequestHolder.class).remove();
	}

	public static MobyletContext getMobyletContext() {
		HttpServletRequest request = get();
		if (request.getAttribute(MobyletContext.CONTEXT_KEY) == null) {
			request.setAttribute(
					MobyletContext.CONTEXT_KEY,
					new MobyletContext());
			return getMobyletContext();
		} else {
			return (MobyletContext)request.getAttribute(MobyletContext.CONTEXT_KEY);
		}
	}

	public static String getUserAgent() {
		return getUserAgent(get());
	}

	public static String getUserAgent(HttpServletRequest request) {
		return request.getHeader("User-Agent");
	}

	public static boolean isIncludeScope() {
		return RequestUtils.getMobyletContext().get(DispatchType.class) ==
			DispatchType.INCLUDE_OR_FORWARD;
	}

}

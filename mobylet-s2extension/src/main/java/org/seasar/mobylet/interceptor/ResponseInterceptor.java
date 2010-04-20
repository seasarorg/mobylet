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
package org.seasar.mobylet.interceptor;

import org.aopalliance.intercept.MethodInvocation;
import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.annotation.Response;
import org.seasar.framework.aop.interceptors.AbstractInterceptor;

public class ResponseInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 8359714762810431246L;

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Response annotation =
			invocation.getMethod().getAnnotation(Response.class);
		if (annotation != null) {
			Mobylet m = MobyletFactory.getInstance();
			m.setContentType(annotation.value());
		}
		return invocation.proceed();
	}

}

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

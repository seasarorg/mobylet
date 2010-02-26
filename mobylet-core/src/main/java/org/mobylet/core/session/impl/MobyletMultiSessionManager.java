package org.mobylet.core.session.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mobylet.core.config.MobyletSessionConfig;
import org.mobylet.core.config.MobyletSessionConfig.Parameters;
import org.mobylet.core.holder.SessionHolder;
import org.mobylet.core.session.InvokeType;
import org.mobylet.core.session.MobyletSessionManager;
import org.mobylet.core.util.SerializeUtils;
import org.mobylet.core.util.SingletonUtils;

public class MobyletMultiSessionManager implements MobyletSessionManager {

	protected MobyletSessionConfig config;
	
	protected SessionHolder holder;
	
	
	public MobyletMultiSessionManager() {
		config = SingletonUtils.get(MobyletSessionConfig.class);
		holder = SingletonUtils.get(SessionHolder.class);
	}
	
	@Override
	public void invoke(HttpServletRequest request, HttpServletResponse response) {
		Parameters parametersKey = config.getDistribution().getParameters();
		String key = request.getParameter(parametersKey.getSessionKey());
		String type = request.getParameter(parametersKey.getInvokeTypeKey());
		String objString = request.getParameter(parametersKey.getObjectDataKey());
		InvokeType invokeType = InvokeType.valueOf(type);
		switch (invokeType) {
		case GET:
			Object obj = holder.get(key, (Class<?>)SerializeUtils.deserialize(objString));
			//TODO ここから
		case SET:
		case REMOVE:
		case INVALIDATE:
		default:
			//NOP
		}
	}

	@Override
	public boolean isManaged(HttpServletRequest request) {
		//IPチェックが必要
		return request.getRequestURI().equals(
				SingletonUtils.get(MobyletSessionConfig.class)
				.getDistribution().getPath());
	}

}

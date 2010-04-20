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
package org.mobylet.core.session.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.config.MobyletConfig;
import org.mobylet.core.config.MobyletSessionConfig;
import org.mobylet.core.config.MobyletSessionConfig.Parameters;
import org.mobylet.core.config.enums.SecureGateway;
import org.mobylet.core.holder.SessionHolder;
import org.mobylet.core.session.InvokeType;
import org.mobylet.core.session.MobyletSessionManager;
import org.mobylet.core.util.SerializeUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;

public class MobyletMultiSessionManager implements MobyletSessionManager {

	protected MobyletConfig config;

	protected MobyletSessionConfig sessionConfig;

	protected SessionHolder holder;


	public MobyletMultiSessionManager() {
		config = SingletonUtils.get(MobyletConfig.class);
		sessionConfig = SingletonUtils.get(MobyletSessionConfig.class);
		holder = SingletonUtils.get(SessionHolder.class);
	}

	@Override
	public void invoke(HttpServletRequest request, HttpServletResponse response) {
		Parameters parametersKey = sessionConfig.getDistribution().getParameters();
		String key = request.getParameter(parametersKey.getSessionKey());
		String type = request.getParameter(parametersKey.getInvokeTypeKey());
		String paramObject = request.getParameter(parametersKey.getObjectDataKey());
		InvokeType invokeType = InvokeType.valueOf(type);
		if (invokeType != InvokeType.INVALIDATE &&
				StringUtils.isEmpty(paramObject)) {
			doEmptyResponse(response);
			return;
		}
		//GET
		if (invokeType == InvokeType.GET) {
			Object obj = holder.get(key, (Class<?>)SerializeUtils.deserialize(paramObject));
			if (obj == null) {
				doEmptyResponse(response);
				return;
			}
			byte[] objBytes = SerializeUtils.serialize2Bytes(obj);
			response.setContentType("application/octet-stream");
			response.setContentLength(objBytes.length);
			try {
				response.getOutputStream().write(SerializeUtils.serialize2Bytes(obj));
				response.flushBuffer();
			} catch (IOException e) {
				throw new MobyletRuntimeException("セッションインスタンスのレスポンス送信に失敗しました", e);
			}
		}
		//SET
		else if (invokeType == InvokeType.SET) {
			holder.set(key, SerializeUtils.deserialize(paramObject));
			response.setContentType("application/octet-stream");
			response.setContentLength(2);
			try {
				response.getOutputStream().write("OK".getBytes());
				response.flushBuffer();
			} catch (IOException e) {
				throw new MobyletRuntimeException("セッションインスタンスのレスポンス送信に失敗しました", e);
			}
		}
		//REMOVE
		else if (invokeType == InvokeType.REMOVE) {
			Object obj = holder.remove(key, (Class<?>)SerializeUtils.deserialize(paramObject));
			if (obj == null) {
				doEmptyResponse(response);
				return;
			}
			byte[] objBytes = SerializeUtils.serialize2Bytes(obj);
			response.setContentType("application/octet-stream");
			response.setContentLength(objBytes.length);
			try {
				response.getOutputStream().write(SerializeUtils.serialize2Bytes(obj));
				response.flushBuffer();
			} catch (IOException e) {
				throw new MobyletRuntimeException("セッションインスタンスのレスポンス送信に失敗しました", e);
			}
		}
		//INVALIDATE
		else if (invokeType == InvokeType.INVALIDATE) {
			holder.invalidate(key);
			response.setContentType("application/octet-stream");
			response.setContentLength(2);
			try {
				response.getOutputStream().write("OK".getBytes());
				response.flushBuffer();
			} catch (IOException e) {
				throw new MobyletRuntimeException("セッションインスタンスのレスポンス送信に失敗しました", e);
			}
		}
	}

	@Override
	public boolean isManaged(HttpServletRequest request) {
		if (config.getSecureGateway() == SecureGateway.NONE ||
				config.getSecureGateway() == SecureGateway.SECURE_CARRIER ||
				(sessionConfig.getDistribution() != null &&
						sessionConfig.getDistribution().getAllowIps() != null &&
						sessionConfig.getDistribution().isAllowIp(request.getRemoteAddr()))) {
			return request.getRequestURI().equals(
					SingletonUtils.get(MobyletSessionConfig.class)
					.getDistribution().getPath());
		}
		return false;
	}

	protected void doEmptyResponse(HttpServletResponse response) {
		response.setContentType("application/octet-stream");
		response.setStatus(200);
		response.setContentLength(0);
		try {
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

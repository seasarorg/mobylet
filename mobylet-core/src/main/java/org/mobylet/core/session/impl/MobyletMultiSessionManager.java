package org.mobylet.core.session.impl;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.config.MobyletSessionConfig;
import org.mobylet.core.config.MobyletSessionConfig.Parameters;
import org.mobylet.core.define.DefCharset;
import org.mobylet.core.holder.SessionHolder;
import org.mobylet.core.session.InvokeType;
import org.mobylet.core.session.MobyletSessionManager;
import org.mobylet.core.util.SerializeUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.core.util.UrlDecoder;

public class MobyletMultiSessionManager implements MobyletSessionManager {

	private static final Charset CHARSET = Charset.forName(DefCharset.UTF8);

	protected MobyletSessionConfig config;

	protected SessionHolder holder;


	public MobyletMultiSessionManager() {
		config = SingletonUtils.get(MobyletSessionConfig.class);
		holder = SingletonUtils.get(SessionHolder.class);
	}

	@Override
	public void invoke(HttpServletRequest request, HttpServletResponse response) {
		Parameters parametersKey = config.getDistribution().getParameters();
		String key = UrlDecoder.decode(
				request.getParameter(parametersKey.getSessionKey()), CHARSET);
		String type = request.getParameter(parametersKey.getInvokeTypeKey());
		String paramObject = request.getParameter(parametersKey.getObjectDataKey());
		String objString = null;
		if (StringUtils.isNotEmpty(paramObject)) {
			objString = UrlDecoder.decode(paramObject, CHARSET);
		}
		InvokeType invokeType = InvokeType.valueOf(type);
		if (invokeType != InvokeType.INVALIDATE &&
				StringUtils.isEmpty(objString)) {
			doEmptyResponse(response);
			return;
		}
		//GET
		if (invokeType == InvokeType.GET) {
			Object obj = holder.get(key, (Class<?>)SerializeUtils.deserialize(objString));
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
			holder.set(key, SerializeUtils.deserialize(objString));
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
			Object obj = holder.remove(key, (Class<?>)SerializeUtils.deserialize(objString));
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
		if (config.getDistribution().isAllowIp(request.getRemoteAddr())) {
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

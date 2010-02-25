package org.mobylet.core.session.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.util.List;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.config.MobyletSessionConfig;
import org.mobylet.core.config.MobyletSessionConfig.Host;
import org.mobylet.core.holder.SessionHolder;
import org.mobylet.core.session.InvokeType;
import org.mobylet.core.session.MobyletSessionAdapter;
import org.mobylet.core.util.Base64Utils;
import org.mobylet.core.util.HttpUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.core.util.UrlEncoder;

public class MobyletMultiSessionAdapter implements MobyletSessionAdapter {

	private static final Charset CHARSET = Charset.forName("UTF-8");

	protected MobyletSessionConfig config;


	public MobyletMultiSessionAdapter() {
		config = SingletonUtils.get(MobyletSessionConfig.class);
	}

	@Override
	public <T> T get(String key, Class<T> clazz) {
		SessionHolder holder = SingletonUtils.get(SessionHolder.class);
		return holder.get(key, clazz);
	}

	@Override
	public boolean invalidate(String key) {
		SessionHolder holder = SingletonUtils.get(SessionHolder.class);
		holder.invalidate(key);
		return true;
	}

	@Override
	public <T> T remove(String key, Class<T> clazz) {
		SessionHolder holder = SingletonUtils.get(SessionHolder.class);
		return holder.remove(key, clazz);
	}

	@Override
	public <T> boolean set(String key, T obj) {
		Host host = getHost(key);
		ByteArrayOutputStream baos = new ByteArrayOutputStream(8192);
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			HttpURLConnection connection = null;
			try {
				connection = HttpUtils.getHttpUrlConnection(
						config.getDistribution().getProtocol() + "://" +
						host.getHost() +
						config.getDistribution().getPath());
				connection.addRequestProperty(
						config.getDistribution().getParameters().getInvokeTypeKey(),
						InvokeType.SET.name());
				connection.addRequestProperty(
						config.getDistribution().getParameters().getObjectDataKey(),
						UrlEncoder.encode(
								Base64Utils.byteArrayToBase64(baos.toByteArray()), CHARSET));
				connection.connect();
				return (connection.getResponseCode() == 200);
			} catch (Exception e) {
				throw e;
			} finally {
				if (connection != null) {
					connection.disconnect();
				}
			}
		} catch (Exception e) {
			throw new MobyletRuntimeException("シリアライズに失敗しました", e);
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					//NOP
				}
			}
		}
	}

	protected Host getHost(String key) {
		if (StringUtils.isEmpty(key)) {
			return null;
		}
		List<Host> hosts = config.getDistribution().getReceiveHosts();
		return hosts.get(Math.abs(key.hashCode() % hosts.size()));
	}
}

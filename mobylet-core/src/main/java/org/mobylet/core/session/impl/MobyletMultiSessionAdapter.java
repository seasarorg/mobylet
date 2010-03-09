package org.mobylet.core.session.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.util.List;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.config.MobyletSessionConfig;
import org.mobylet.core.config.MobyletSessionConfig.Host;
import org.mobylet.core.define.DefCharset;
import org.mobylet.core.session.InvokeType;
import org.mobylet.core.session.MobyletSessionAdapter;
import org.mobylet.core.util.HttpUtils;
import org.mobylet.core.util.SerializeUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;
import org.mobylet.core.util.UrlEncoder;

public class MobyletMultiSessionAdapter implements MobyletSessionAdapter {

	private static final Charset CHARSET = Charset.forName(DefCharset.UTF8);

	@Override
	@SuppressWarnings("unchecked")
	public <T> T get(String key, Class<T> clazz) {
		if (StringUtils.isEmpty(key)) {
			return null;
		}
		HttpURLConnection connection = null;
		try {
			connection = getSessionConnection(key, InvokeType.GET, clazz);
			connection.connect();
			return (T)getObject(connection);
		} catch (Exception e) {
			throw new MobyletRuntimeException("セッション管理処理に失敗 [GET]", e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	@Override
	public boolean invalidate(String key) {
		if (StringUtils.isEmpty(key)) {
			return false;
		}
		HttpURLConnection connection = null;
		try {
			connection = getSessionConnection(key, InvokeType.INVALIDATE, null);
			connection.connect();
			return connection.getResponseCode() == 200;
		} catch (Exception e) {
			throw new MobyletRuntimeException("セッション管理処理に失敗 [INVALIDATE]", e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T remove(String key, Class<T> clazz) {
		if (StringUtils.isEmpty(key)) {
			return null;
		}
		HttpURLConnection connection = null;
		try {
			connection = getSessionConnection(key, InvokeType.REMOVE, clazz);
			connection.connect();
			return (T)getObject(connection);
		} catch (Exception e) {
			throw new MobyletRuntimeException("セッション管理処理に失敗 [REMOVE]", e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	@Override
	public boolean set(String key, Object obj) {
		if (StringUtils.isEmpty(key)) {
			return false;
		}
		HttpURLConnection connection = null;
		try {
			connection = getSessionConnection(key, InvokeType.SET, obj);
			connection.connect();
			return connection.getResponseCode() == 200;
		} catch (Exception e) {
			throw new MobyletRuntimeException("セッション管理処理に失敗 [SET]", e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	protected HttpURLConnection getSessionConnection(String key, InvokeType type, Object obj) {
		MobyletSessionConfig config = SingletonUtils.get(MobyletSessionConfig.class);
		String url = getBaseUrl(key);
		HttpURLConnection connection = HttpUtils.getHttpUrlConnection(url);
		connection.setDoOutput(true);
		StringBuilder buf = new StringBuilder();
		buf.append(config.getDistribution().getParameters().getSessionKey() + "=" +
				UrlEncoder.encode(key, CHARSET));
		buf.append("&" + config.getDistribution().getParameters().getInvokeTypeKey() + "=" +
				type.name());
		if (obj != null) {
			buf.append("&" + config.getDistribution().getParameters().getObjectDataKey() + "=" +
					UrlEncoder.encode(SerializeUtils.serialize2Base64String(obj), CHARSET));
		}
		PrintStream printStream = null;
		try {
			OutputStream outputStream = connection.getOutputStream();
			printStream = new PrintStream(outputStream);
			printStream.print(buf.toString());
		} catch (IOException e) {
			throw new MobyletRuntimeException("セッション格納用URLコネクション生成時に失敗 URL=" + url, e);
		} finally {
			if (printStream != null) {
				try {
					printStream.close();
				} catch (Exception e) {
					//NOP
				}
			}
		}
		return connection;
	}

	protected Object getObject(HttpURLConnection connection) {
		int length = connection.getContentLength();
		if (length == 0) {
			return null;
		}
		InputStream inputStream = null;
		ObjectInputStream ois = null;
		try {
			inputStream = connection.getInputStream();
			ois = new ObjectInputStream(inputStream);
			return ois.readObject();
		} catch (IOException e) {
			throw new MobyletRuntimeException("レスポンス情報からセッションオブジェクトを復元時にIO例外発生", e);
		} catch (ClassNotFoundException e) {
			throw new MobyletRuntimeException("セッションオブジェクトのキャストに失敗", e);
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					//NOP
				}
			}
		}
	}

	protected String getBaseUrl(String key) {
		MobyletSessionConfig config = SingletonUtils.get(MobyletSessionConfig.class);
		return
			config.getDistribution().getProtocol() + "://" +
			getHost(key).getHost() +
			config.getDistribution().getPath();
	}

	protected Host getHost(String key) {
		if (StringUtils.isEmpty(key)) {
			return null;
		}
		MobyletSessionConfig config = SingletonUtils.get(MobyletSessionConfig.class);
		List<Host> hosts = config.getDistribution().getReceiveHosts();
		return hosts.get(Math.abs(key.hashCode() % hosts.size()));
	}
}

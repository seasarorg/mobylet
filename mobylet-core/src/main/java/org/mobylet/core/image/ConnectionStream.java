package org.mobylet.core.image;

import java.io.InputStream;
import java.net.HttpURLConnection;

public class ConnectionStream {

	protected HttpURLConnection connection;

	protected InputStream inputStream;


	public ConnectionStream(HttpURLConnection connection, InputStream inputStream) {
		this.connection = connection;
		this.inputStream = inputStream;
	}

	public HttpURLConnection getConnection() {
		return connection;
	}

	public void setConnection(HttpURLConnection connection) {
		this.connection = connection;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}

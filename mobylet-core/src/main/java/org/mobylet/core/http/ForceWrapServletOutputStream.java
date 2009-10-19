package org.mobylet.core.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;

import org.mobylet.core.MobyletFactory;
import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.util.RequestUtils;

public class ForceWrapServletOutputStream extends ServletOutputStream {

	public static final String KEY_PROXY_CHARSET = "X_MOBYLET_PROXY_CHARSET";

	protected PrintWriter writer;

	protected ByteArrayOutputStream baos;

	protected String proxyCharset = "UTF-8";


	public ForceWrapServletOutputStream(MobyletResponse response) {
		try {
			writer = response.getWriter();
		} catch (IOException e) {
			throw new MobyletRuntimeException(
					"強制PrintWriter取得処理で例外発生", e);
		}
		Object tmpProxyCharset = null;
		if (RequestUtils.get() != null &&
				(tmpProxyCharset = RequestUtils.get().getAttribute(KEY_PROXY_CHARSET)) != null) {
			if (tmpProxyCharset != null) {
				proxyCharset = tmpProxyCharset.toString();
			}
		}
		this.baos = new ByteArrayOutputStream(8192);
	}

	@Override
	public void write(int b) throws IOException {
		baos.write(b);
	}

	@Override
	public void flush() throws IOException {
		if (baos.size() > 0) {
			writer.write(baos.toString(proxyCharset).toCharArray());
		}
		super.flush();
	}

	public int getLength() {
		if (baos.size() > 0) {
			try {
				return baos.toString(proxyCharset).getBytes(
						MobyletFactory.getInstance().getDialect().getCharset()).length;
			} catch (UnsupportedEncodingException e) {
				//NOP
				return 0;
			}
		}
		return 0;
	}

}

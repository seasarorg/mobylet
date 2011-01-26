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
package org.mobylet.core.http;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.mobylet.core.Carrier;
import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.config.MobyletConfig;
import org.mobylet.core.dialect.MobyletDialect;
import org.mobylet.core.image.ImageScaler;
import org.mobylet.core.type.ContentType;
import org.mobylet.core.util.ImageUtils;
import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;

public class MobyletResponse extends HttpServletResponseWrapper {

	public static final Pattern REG_JSESSIONID =
		Pattern.compile(";jsessionid=[^?]+");

	protected HttpServletResponse response;

	protected MobyletDialect dialect;

	protected PrintWriter printWriter;

	protected ServletOutputStream outputStream;

	protected String contentType;

	protected boolean hasContentType = false;


	public MobyletResponse(HttpServletResponse response, MobyletDialect dialect) {
		super(response);
		this.dialect = dialect;
		this.response = response;
	}

	@Override
	public void setContentType(String type) {
		if (!hasContentType) {
			contentType = (type == null ? contentType : type);
			if (StringUtils.isNotEmpty(contentType) &&
					RequestUtils.getMobyletContext().get(Ready.class) != null) {
				super.setContentType(contentType);
				hasContentType = true;
			} else {
				//Tiles対応
				Mobylet m = MobyletFactory.getInstance();
				if (m != null &&
						m.getContentType() == ContentType.XHTML) {
					contentType = dialect.getXContentTypeString();
				} else {
					contentType = dialect.getContentTypeString();
				}
				super.setContentType(contentType);
			}
		} else {
			super.setContentType(type);
		}
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (printWriter == null) {
			if (RequestUtils.isIncludeScope() &&
					!isRootResponse()) {
				Mobylet m = MobyletFactory.getInstance();
				RequestUtils.getMobyletContext().set(new Ready());
				if (m != null &&
						m.getContentType() == ContentType.XHTML) {
					setContentType(dialect.getXContentTypeString());
				} else {
					setContentType(dialect.getContentTypeString());
				}
				printWriter = super.getWriter();
			} else {
				printWriter = new MobyletPrintWriter(
						new OutputStreamWriter(getMobyletOutputStream(),
								dialect.getCharset()),
							dialect.getCarrier());
				Mobylet m = MobyletFactory.getInstance();
				RequestUtils.getMobyletContext().set(new Ready());
				if (m != null &&
						m.getContentType() == ContentType.XHTML) {
					MobyletConfig config = SingletonUtils.get(MobyletConfig.class);
					if (m.getCarrier() == Carrier.DOCOMO &&
							config.useCSSExpand()) {
						printWriter = new CSSExpandPrintWriter(printWriter);
					}
					setContentType(dialect.getXContentTypeString());
				} else {
					setContentType(dialect.getContentTypeString());
				}
			}
		}
		return printWriter;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (outputStream == null) {
			String imgContentType = null;
			if (dialect.getCarrier() != Carrier.OTHER &&
					(imgContentType =
						ImageUtils.getContentTypeStringByRequestURI()) != null) {
				RequestUtils.getMobyletContext().set(new Ready());
				setContentType(imgContentType);
				if (ImageUtils.isAutoScale()) {
					outputStream = new ProxyImageOutputStream();
				} else {
					outputStream = super.getOutputStream();
				}
			} else {
				RequestUtils.getMobyletContext().set(new Ready());
				setContentType(contentType);
				outputStream =
					new MobyletBufferedServletOutputStream(this, super.getOutputStream());
			}
		}
		return outputStream;
	}

	@Override
	public String encodeURL(String url) {
		MobyletConfig config = SingletonUtils.get(MobyletConfig.class);
		switch (config.getJSession()) {
		case DEFAULT:
		case USE_QUERY:
			return super.encodeURL(url);
		default:
			String encodeURL = super.encodeURL(url);
			if (encodeURL.indexOf(';') >= 0) {
				return REG_JSESSIONID.matcher(encodeURL).replaceFirst("");
			} else {
				return encodeURL;
			}
		}
	}

	@Override
	public String encodeRedirectURL(String url) {
		MobyletConfig config = SingletonUtils.get(MobyletConfig.class);
		switch (config.getJSession()) {
		case DEFAULT:
		case USE_QUERY:
			return super.encodeRedirectURL(url);
		default:
			String encodeURL = super.encodeRedirectURL(url);
			if (encodeURL.indexOf(';') >= 0) {
				return REG_JSESSIONID.matcher(encodeURL).replaceFirst("");
			} else {
				return encodeURL;
			}
		}
	}

	@Override
	public void addHeader(String name, String value) {
		MobyletConfig config = SingletonUtils.get(MobyletConfig.class);
		switch (config.getJSession()) {
		case DEFAULT:
		case USE_COOKIE:
			super.addHeader(name, value);
			return;
		default:
			if (StringUtils.isNotEmpty(name) &&
					name.equalsIgnoreCase("Set-Cookie")) {
				//NOP
			} else {
				super.addHeader(name, value);
			}
			return;
		}
	}

	public void flushByMobylet() throws IOException {
		if (printWriter != null) {
			if (printWriter instanceof CSSExpandPrintWriter) {
				CSSExpandPrintWriter.class.cast(printWriter).flushByMobylet();
			}
			printWriter.flush();
		} else if (outputStream != null) {
			if (outputStream instanceof ProxyImageOutputStream) {
				MobyletServletOutputStream outStream =
					new MobyletServletOutputStream(super.getOutputStream());
				SingletonUtils.get(ImageScaler.class).scale(
						ProxyImageOutputStream.class.cast(outputStream)
							.getWrittenBytesInputStream(),
						outStream,
						ImageUtils.getImageCodec(),
						ImageUtils.getScaledWidth(),
						ImageUtils.getScaleType());
				super.setContentLength(outStream.getLength());
			}
			outputStream.flush();
		}
	}

	@Override
	public void setContentLength(int len) {
		if (outputStream != null
				&& outputStream instanceof ProxyImageOutputStream) {
			//NOP （flushByMobylet以外からの呼び出し無効化）
		} else {
			super.setContentLength(len);
		}
	}

	@Override
	public void sendRedirect(String location) throws IOException {
		RequestUtils.getMobyletContext().set(new Ready());
		Mobylet m = SingletonUtils.get(Mobylet.class);
		if (m != null &&
				m.getContentType() == ContentType.XHTML) {
			setContentType(dialect.getXContentTypeString());
		} else {
			setContentType(dialect.getContentTypeString());
		}
		super.sendRedirect(location);
	}

	@Override
	public void sendError(int sc, String msg) throws IOException {
		RequestUtils.getMobyletContext().set(new Ready());
		Mobylet m = SingletonUtils.get(Mobylet.class);
		if (m != null &&
				m.getContentType() == ContentType.XHTML) {
			setContentType(dialect.getXContentTypeString());
		} else {
			setContentType(dialect.getContentTypeString());
		}
		super.sendError(sc, msg);
	}

	protected boolean isRootResponse() {
		return this.equals(
				RequestUtils.getMobyletContext().get(MobyletResponse.class));
	}

	protected ServletOutputStream getMobyletOutputStream() throws IOException {
		return getOutputStream();
	}

	public static class Ready {
		//NOP
	}
}

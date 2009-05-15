package org.mobylet.core.http;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mobylet.core.Carrier;
import org.mobylet.core.detector.CarrierDetector;
import org.mobylet.core.detector.impl.MobyletCarrierDetector;
import org.mobylet.core.dialect.MobyletDialect;
import org.mobylet.core.emoji.impl.MobyletEmojiStockerFamily;
import org.mobylet.core.emoji.impl.MobyletEmojiStockerReader;
import org.mobylet.core.selector.DialectSelector;
import org.mobylet.core.selector.impl.MobyletDialectSelector;
import org.mobylet.core.util.SingletonUtils;

public class MobyletFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//request/response
		HttpServletRequest httpRequest = HttpServletRequest.class.cast(request);
		HttpServletResponse httpResponse = HttpServletResponse.class.cast(response);
		//Carrier
		Carrier carrier = Carrier.OTHER;
		CarrierDetector carrierDetector = SingletonUtils.get(CarrierDetector.class);
		if (carrierDetector != null) {
			carrier = carrierDetector.getCarrier(httpRequest);
		}
		//Dialect
		DialectSelector selector = SingletonUtils.get(DialectSelector.class);
		MobyletDialect dialect = selector.getDialect(carrier);
		if (dialect == null) {
			dialect = selector.getDefaultDialect();
		}
		//Charset
		String charsetName = dialect.getCharsetName();
		httpRequest.setCharacterEncoding(charsetName);
		//doChain
		MobyletResponse mResponse = new MobyletResponse(httpResponse, dialect);
		chain.doFilter(request, mResponse);
		mResponse.flush();
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		SingletonUtils.put(new MobyletCarrierDetector());
		SingletonUtils.put(new MobyletDialectSelector());
		SingletonUtils.put(new MobyletEmojiStockerFamily());
		SingletonUtils.put(new MobyletEmojiStockerReader());
	}

}

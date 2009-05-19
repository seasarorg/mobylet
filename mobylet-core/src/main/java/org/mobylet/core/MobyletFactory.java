package org.mobylet.core;

import org.mobylet.core.http.MobyletContext;
import org.mobylet.core.util.RequestUtils;

public class MobyletFactory {


	public static Mobylet getInstance() {
		MobyletContext context = RequestUtils.getMobyletContext();
		Mobylet m = null;
		if ((m = context.get(Mobylet.class)) != null) {
			return m;
		} else {
			context.set(new Mobylet());
			return getInstance();
		}
	}
}

package org.mobylet.core.holder.impl;

import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.config.MobyletConfig;
import org.mobylet.core.holder.MobyletHolder;
import org.mobylet.core.http.MobyletContext;
import org.mobylet.core.impl.MobyletImpl;
import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.SingletonUtils;

public class MobyletHolderImpl implements MobyletHolder {

	@Override
	public Mobylet get() {
		MobyletContext context = RequestUtils.getMobyletContext();
		Mobylet m = null;
		if ((m = context.get(Mobylet.class)) != null) {
			return m;
		} else {
			MobyletConfig config = SingletonUtils.get(MobyletConfig.class);
			Class<? extends Mobylet> clazz = config.getMobyletClass();
			if (clazz == null) {
				context.set(new MobyletImpl());
			} else {
				try {
					context.set(clazz.newInstance());
				} catch (Exception e) {
					throw new MobyletRuntimeException(
							"Mobyletインスタンス生成中に例外発生", e);
				}
			}
			return get();
		}
	}

}

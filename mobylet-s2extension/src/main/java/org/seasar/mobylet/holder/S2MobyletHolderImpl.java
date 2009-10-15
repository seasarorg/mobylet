package org.seasar.mobylet.holder;

import org.mobylet.core.Mobylet;
import org.mobylet.core.holder.impl.MobyletHolderImpl;
import org.seasar.framework.container.SingletonS2Container;

public class S2MobyletHolderImpl extends MobyletHolderImpl {

	protected boolean hasDiconSetting = true;

	@Override
	public Mobylet get() {
		if (hasDiconSetting) {
			Mobylet m = null;
			try {
				m = SingletonS2Container.getComponent(Mobylet.class);
			} catch (Throwable t) {
				//NOP
			}
			if (m == null) {
				hasDiconSetting = false;
				return get();
			} else {
				return m;
			}
		} else {
			return super.get();
		}
	}
}

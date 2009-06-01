package org.seasar.mobylet.holder;

import org.mobylet.core.holder.SingletonHolder;
import org.seasar.framework.container.SingletonS2Container;

public class S2MobyletSingletonHolder implements SingletonHolder {

	@Override
	public <T> T get(Class<T> clazz) {
		try {
			return SingletonS2Container.getComponent(clazz);
		} catch (Throwable t) {
			return null;
		}
	}

	@Override
	public void remove(Class<?> clazz) {
		//NOP
	}

	@Override
	public void set(Class<?> clazz, Object object) {
		//NOP
	}

}

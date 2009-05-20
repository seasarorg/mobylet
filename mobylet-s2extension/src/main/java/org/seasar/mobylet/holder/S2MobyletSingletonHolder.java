package org.seasar.mobylet.holder;

import org.mobylet.core.holder.SingletonHolder;
import org.seasar.framework.container.SingletonS2Container;

public class S2MobyletSingletonHolder implements SingletonHolder {

	@Override
	public <T> T get(Class<T> clazz) {
		return SingletonS2Container.getComponent(clazz);
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

package org.seasar.mobylet.holder;

import org.mobylet.core.holder.impl.MobyletSingletonHolder;
import org.seasar.framework.container.SingletonS2Container;

public class S2MobyletSingletonHolder extends MobyletSingletonHolder {

	@Override
	public <T> T get(Class<T> clazz) {
		T obj = null;
		try {
			obj = SingletonS2Container.getComponent(clazz);
		} catch (Throwable t) {
			//NOP
		}
		if (obj == null) {
			obj = super.get(clazz);
		}
		return obj;
	}

	@Override
	public void remove(Class<?> clazz) {
		super.remove(clazz);
	}

	@Override
	public void set(Class<?> clazz, Object object) {
		super.set(clazz, object);
	}

}

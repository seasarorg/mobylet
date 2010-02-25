package org.mobylet.core.session.impl;

import org.mobylet.core.holder.SessionHolder;
import org.mobylet.core.session.MobyletSessionAdapter;
import org.mobylet.core.util.SingletonUtils;

public class MobyletSingleSessionAdapter implements MobyletSessionAdapter {

	@Override
	public <T> T get(String key, Class<T> clazz) {
		SessionHolder holder = SingletonUtils.get(SessionHolder.class);
		return holder.get(key, clazz);
	}

	@Override
	public boolean invalidate(String key) {
		SessionHolder holder = SingletonUtils.get(SessionHolder.class);
		holder.invalidate(key);
		return true;
	}

	@Override
	public <T> T remove(String key, Class<T> clazz) {
		SessionHolder holder = SingletonUtils.get(SessionHolder.class);
		return holder.remove(key, clazz);
	}

	@Override
	public <T> boolean set(String key, T obj) {
		SessionHolder holder = SingletonUtils.get(SessionHolder.class);
		holder.set(key, obj);
		return true;
	}

}

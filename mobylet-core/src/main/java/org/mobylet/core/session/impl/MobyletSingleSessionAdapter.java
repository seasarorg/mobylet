package org.mobylet.core.session.impl;

import org.mobylet.core.holder.SessionHolder;
import org.mobylet.core.session.MobyletSessionAdapter;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;

public class MobyletSingleSessionAdapter implements MobyletSessionAdapter {

	@Override
	public <T> T get(String key, Class<T> clazz) {
		if (StringUtils.isEmpty(key)) {
			return null;
		}
		SessionHolder holder = SingletonUtils.get(SessionHolder.class);
		return holder.get(key, clazz);
	}

	@Override
	public boolean invalidate(String key) {
		if (StringUtils.isEmpty(key)) {
			return false;
		}
		SessionHolder holder = SingletonUtils.get(SessionHolder.class);
		holder.invalidate(key);
		return true;
	}

	@Override
	public <T> T remove(String key, Class<T> clazz) {
		if (StringUtils.isEmpty(key)) {
			return null;
		}
		SessionHolder holder = SingletonUtils.get(SessionHolder.class);
		return holder.remove(key, clazz);
	}

	@Override
	public <T> boolean set(String key, T obj) {
		if (StringUtils.isEmpty(key)) {
			return false;
		}
		SessionHolder holder = SingletonUtils.get(SessionHolder.class);
		holder.set(key, obj);
		return true;
	}

}

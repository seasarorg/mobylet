package org.mobylet.core.holder.impl;

import java.util.HashMap;

import org.mobylet.core.holder.SessionHolder;

public class MobyletSessionHolder implements SessionHolder {

	protected HashMap<String, Object> holder = new HashMap<String, Object>();


	@Override
	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		return (T)holder.get(key);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T remove(String key) {
		return (T)holder.remove(key);
	}

	@Override
	public <T> void set(String key, T obj) {
		if (key != null) {
			holder.put(key, obj);
		}
	}

}

package org.mobylet.core.session;


public interface MobyletSessionAdapter {

	<T> T get(String key, Class<T> clazz);

	<T> boolean set(String key, T obj);

	<T> T remove(String key, Class<T> clazz);

	boolean invalidate(String key);

}

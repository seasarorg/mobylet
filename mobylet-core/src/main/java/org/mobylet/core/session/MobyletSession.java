package org.mobylet.core.session;

public interface MobyletSession {

	<T> T get(Class<T> clazz);

	<T> T get(String key);

	<T> void set(T obj);

	<T> void set(String key, T obj);

	<T> T remove(Class<T> clazz);

	<T> T remove(String key);

	boolean exist();

	void invalidate();

	void substitute();

}

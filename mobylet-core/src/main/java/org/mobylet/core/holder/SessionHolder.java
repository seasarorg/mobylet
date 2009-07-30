package org.mobylet.core.holder;

public interface SessionHolder {

	<T> T get(String key);

	<T> void set(String key, T obj);

	<T> T remove(String key);

}

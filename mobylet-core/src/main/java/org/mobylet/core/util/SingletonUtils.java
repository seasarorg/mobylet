package org.mobylet.core.util;

import java.util.HashMap;
import java.util.Map;

public class SingletonUtils {

	private static Map<Class<?>, Object> MAP_SINGLETON = new HashMap<Class<?>, Object>();


	@SuppressWarnings("unchecked")
	public static <S extends Object> S get(Class<S> clazz) {
		return (S)MAP_SINGLETON.get(clazz);
	}

	public static void put(Object obj) {
		if (obj == null) {
			return;
		}
		Class<?>[] interfaces = obj.getClass().getInterfaces();
		if (interfaces != null) {
			for (Class<?> clazz : interfaces) {
				MAP_SINGLETON.put(clazz, obj);
			}
		}
		MAP_SINGLETON.put(obj.getClass(), obj);
	}
}

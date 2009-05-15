package org.mobylet.core.util;

import java.util.HashMap;
import java.util.Map;

public class SingletonUtils {

	private static Map<String, Object> singletonMap = new HashMap<String, Object>();


	@SuppressWarnings("unchecked")
	public static <S extends Object> S get(Class<S> clazz) {
		return (S)singletonMap.get(clazz.getName());
	}

	public static void put(Object obj) {
		if (obj == null) {
			return;
		}
		Class<?>[] interfaces = obj.getClass().getInterfaces();
		if (interfaces != null) {
			for (Class<?> clazz : interfaces) {
				singletonMap.put(clazz.getName(), obj);
			}
		}
		singletonMap.put(obj.getClass().getName(), obj);
	}
}

/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.mobylet.core.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
		Set<Class<?>> interfaces = getSuperInterfaces(obj.getClass());
		if (interfaces != null && interfaces.size() > 0) {
			for (Class<?> clazz : interfaces) {
				MAP_SINGLETON.put(clazz, obj);
			}
		}
		MAP_SINGLETON.put(obj.getClass(), obj);
	}

	private static Set<Class<?>> getSuperInterfaces(Class<?> clazz) {
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		Class<?>[] interfaces = clazz.getInterfaces();
		if (interfaces != null) {
			for (Class<?> i : interfaces) {
				classSet.add(i);
			}
		}
		Class<?> superClass = null;
		if ((superClass = clazz.getSuperclass()) != null) {
			Set<Class<?>> tmpSet = getSuperInterfaces(superClass);
			classSet.addAll(tmpSet);
		}
		return classSet;
	}
}

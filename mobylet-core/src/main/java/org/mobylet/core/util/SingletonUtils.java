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

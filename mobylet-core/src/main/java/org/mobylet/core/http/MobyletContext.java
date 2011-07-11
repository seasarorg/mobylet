/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
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
package org.mobylet.core.http;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class MobyletContext {

	public static final String CONTEXT_KEY = MobyletContext.class.getName();

	protected Map<Class<?>, Object> context;


	public MobyletContext() {
		context = new HashMap<Class<?>, Object>();
	}

	@SuppressWarnings("unchecked")
	public <I> I get(Class<I> clazz) {
		return (I)context.get(clazz);
	}

	public void set(Object obj) {
		if (obj != null) {
			Set<Class<?>> interfaces = getInterfaces(obj.getClass());
			if (interfaces != null && interfaces.size() > 0) {
				for (Class<?> clazz : interfaces) {
					context.put(clazz, obj);
				}
			}
			context.put(obj.getClass(), obj);
		}
	}

	public void remove(Class<?> clazz) {
		if (clazz != null) {
			Set<Class<?>> interfaces = getInterfaces(clazz);
			if (interfaces != null && interfaces.size() > 0) {
				for (Class<?> pClazz : interfaces) {
					context.remove(pClazz);
				}
			}
		}
		context.remove(clazz);
	}

	private static Set<Class<?>> getInterfaces(Class<?> clazz) {
		Set<Class<?>> classSet = new HashSet<Class<?>>();
		Class<?>[] interfaces = clazz.getInterfaces();
		if (interfaces != null) {
			for (Class<?> i : interfaces) {
				classSet.add(i);
			}
		}
		Class<?> superClass = null;
		if ((superClass = clazz.getSuperclass()) != null) {
			Set<Class<?>> tmpSet = getInterfaces(superClass);
			classSet.addAll(tmpSet);
			if (!Object.class.equals(superClass)) {
				classSet.add(superClass);
			}
		}
		return classSet;
	}

}

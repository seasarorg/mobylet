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

import java.util.HashSet;
import java.util.Set;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.holder.SingletonHolder;
import org.mobylet.core.holder.impl.MobyletSingletonHolder;

public class SingletonUtils {

	private static SingletonHolder singletonHolder;

	public static void initialize(Class<? extends SingletonHolder> holderClass) {
		if (singletonHolder == null && holderClass == null) {
			singletonHolder = new MobyletSingletonHolder();
		} else if (singletonHolder == null && holderClass != null) {
			try {
				singletonHolder = holderClass.newInstance();
			} catch (InstantiationException e) {
				throw new MobyletRuntimeException(
						"SingletonHolder["+holderClass.getName()+
						"]のインスタンス化に失敗しました", e);
			} catch (IllegalAccessException e) {
				throw new MobyletRuntimeException(
						"SingletonHolder["+holderClass.getName()+
						"]のインスタンス化に失敗しました", e);
			}
		} else {
			throw new MobyletRuntimeException(
					"SingletonHolderは既に構築済みです", null);
		}
	}

	public static boolean isInitialized() {
		return singletonHolder != null;
	}

	public static <S extends Object> S get(Class<S> clazz) {
		return singletonHolder.get(clazz);
	}

	public static void put(Object obj) {
		if (obj == null) {
			return;
		}
		Set<Class<?>> interfaces = getInterfaces(obj.getClass());
		if (interfaces != null && interfaces.size() > 0) {
			for (Class<?> clazz : interfaces) {
				singletonHolder.set(clazz, obj);
			}
		}
		singletonHolder.set(obj.getClass(), obj);
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

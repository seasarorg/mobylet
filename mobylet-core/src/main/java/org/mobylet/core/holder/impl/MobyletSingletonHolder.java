/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
package org.mobylet.core.holder.impl;

import java.util.HashMap;
import java.util.Map;

import org.mobylet.core.holder.SingletonHolder;

public class MobyletSingletonHolder implements SingletonHolder {

	protected Map<Class<?>, Object> singletonHolder;


	public MobyletSingletonHolder() {
		singletonHolder = new HashMap<Class<?>, Object>();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> clazz) {
		return (T)singletonHolder.get(clazz);
	}

	@Override
	public void set(Class<?> clazz, Object object) {
		if (clazz != null && object != null) {
			singletonHolder.put(clazz, object);
		}
	}

	@Override
	public void remove(Class<?> clazz) {
		singletonHolder.remove(clazz);
	}

}

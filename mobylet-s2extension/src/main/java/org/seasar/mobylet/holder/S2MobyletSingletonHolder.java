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
package org.seasar.mobylet.holder;

import org.mobylet.core.holder.impl.MobyletSingletonHolder;
import org.seasar.framework.container.SingletonS2Container;

public class S2MobyletSingletonHolder extends MobyletSingletonHolder {

	@Override
	public <T> T get(Class<T> clazz) {
		T obj = null;
		try {
			obj = SingletonS2Container.getComponent(clazz);
		} catch (Throwable t) {
			//NOP
		}
		if (obj == null) {
			obj = super.get(clazz);
		}
		return obj;
	}

	@Override
	public void remove(Class<?> clazz) {
		super.remove(clazz);
	}

	@Override
	public void set(Class<?> clazz, Object object) {
		super.set(clazz, object);
	}

}

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
package org.mobylet.core.session.impl;

import org.mobylet.core.holder.SessionHolder;
import org.mobylet.core.session.MobyletSessionAdapter;
import org.mobylet.core.util.SingletonUtils;
import org.mobylet.core.util.StringUtils;

public class MobyletSingleSessionAdapter implements MobyletSessionAdapter {

	@Override
	public <T> T get(String key, Class<T> clazz) {
		if (StringUtils.isEmpty(key)) {
			return null;
		}
		SessionHolder holder = SingletonUtils.get(SessionHolder.class);
		return holder.get(key, clazz);
	}

	@Override
	public boolean invalidate(String key) {
		if (StringUtils.isEmpty(key)) {
			return false;
		}
		SessionHolder holder = SingletonUtils.get(SessionHolder.class);
		holder.invalidate(key);
		return true;
	}

	@Override
	public <T> T remove(String key, Class<T> clazz) {
		if (StringUtils.isEmpty(key)) {
			return null;
		}
		SessionHolder holder = SingletonUtils.get(SessionHolder.class);
		return holder.remove(key, clazz);
	}

	@Override
	public <T> boolean set(String key, T obj) {
		if (StringUtils.isEmpty(key)) {
			return false;
		}
		SessionHolder holder = SingletonUtils.get(SessionHolder.class);
		holder.set(key, obj);
		return true;
	}

}

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
package org.mobylet.core.util;

import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletFactory;
import org.mobylet.core.config.MobyletConfig;
import org.mobylet.core.config.enums.SessionKey;
import org.mobylet.core.session.MobyletSessionAdapter;

public class SessionUtils {

	public static <T> T get(Class<T> clazz) {
		MobyletSessionAdapter adatper = SingletonUtils.get(MobyletSessionAdapter.class);
		return adatper.get(getKey(), clazz);
	}

	public static void invalidate() {
		MobyletSessionAdapter adatper = SingletonUtils.get(MobyletSessionAdapter.class);
		adatper.invalidate(getKey());
	}

	public static <T> T remove(Class<T> clazz) {
		MobyletSessionAdapter adatper = SingletonUtils.get(MobyletSessionAdapter.class);
		return adatper.remove(getKey(), clazz);
	}

	public static <T> void set(T obj) {
		MobyletSessionAdapter adatper = SingletonUtils.get(MobyletSessionAdapter.class);
		adatper.set(getKey(), obj);
	}

	public static String getKey() {
		MobyletConfig config = SingletonUtils.get(MobyletConfig.class);
		Mobylet m = MobyletFactory.getInstance();
		if (m != null) {
			if (config.getSessionKey() == SessionKey.GUID) {
				return m.getGuid();
			} else {
				return m.getUid();
			}
		} else {
			return null;
		}
	}

}

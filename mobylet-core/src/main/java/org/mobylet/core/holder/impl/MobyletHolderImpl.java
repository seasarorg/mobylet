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

import org.mobylet.core.Mobylet;
import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.config.MobyletConfig;
import org.mobylet.core.holder.MobyletHolder;
import org.mobylet.core.http.MobyletContext;
import org.mobylet.core.impl.MobyletImpl;
import org.mobylet.core.util.RequestUtils;
import org.mobylet.core.util.SingletonUtils;

public class MobyletHolderImpl implements MobyletHolder {

	@Override
	public Mobylet get() {
		MobyletContext context = RequestUtils.getMobyletContext();
		Mobylet m = null;
		if ((m = context.get(Mobylet.class)) != null) {
			return m;
		} else {
			MobyletConfig config = SingletonUtils.get(MobyletConfig.class);
			Class<? extends Mobylet> clazz = config.getMobyletClass();
			if (clazz == null) {
				context.set(new MobyletImpl());
			} else {
				try {
					context.set(clazz.newInstance());
				} catch (Exception e) {
					throw new MobyletRuntimeException(
							"Mobyletインスタンス生成中に例外発生", e);
				}
			}
			return get();
		}
	}

}

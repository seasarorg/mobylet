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
package org.mobylet.view.designer;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.util.SingletonUtils;

public abstract class SingletonDesigner {

	public static <D extends SingletonDesigner> D getDesigner(Class<D> clazz) {
		D designer = null;
		try {
			designer = SingletonUtils.get(clazz);
		} catch (Exception e) {
			//NOP
		}
		if (designer == null) {
			try {
				designer = clazz.newInstance();
			} catch (Exception e) {
				throw new MobyletRuntimeException(
						"Designerの初期化に失敗 designer = " + clazz.getName(), e);
			}
			SingletonUtils.put(designer);
			return getDesigner(clazz);
		}
		return designer;
	}

}

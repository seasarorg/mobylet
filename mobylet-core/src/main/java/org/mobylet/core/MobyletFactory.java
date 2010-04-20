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
package org.mobylet.core;

import org.mobylet.core.holder.MobyletHolder;
import org.mobylet.core.util.SingletonUtils;

/**
 *
 * <p><code>Mobylet</code>インスタンスを取得するためのファクトリ.</p>
 * <p>
 * <code>Mobylet</code>インスタンスを生成し、
 * <code>MobyletContext</code>にキャッシュする。<br />
 * 同一RequestScope内で2度目以降の呼び出しの場合は、
 * キャッシュ済みの<code>Mobylet</code>インスタンスを返却する。
 * </p>
 *
 * @author stakeuchi
 *
 */
public class MobyletFactory {

	/**
	 * <p><code>Mobylet</code>インスタンスを取得します.</p>
	 * <p>
	 * 同一RequestScope内で初回呼び出し時は<code>Mobylet</code>インスタンスを生成し、
	 * 2度目以降の呼び出しの場合は</code>MobyletContext</code>にキャッシュした
	 * <code>Mobylet</code>インスタンスを返却します。
	 * </p>
	 *
	 * @return	<code>Mobylet</code>インスタンス
	 */
	public static Mobylet getInstance() {
		MobyletHolder holder = SingletonUtils.get(MobyletHolder.class);
		return holder.get();
	}
}

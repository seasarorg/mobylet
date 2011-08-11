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
package org.mobylet.core.session;

import java.util.concurrent.ConcurrentHashMap;

public class MobyletSession {

	protected String uid;

	protected Long touchTime;

	protected ConcurrentHashMap<Class<? extends Object>, Object> objectMap;

	protected MobyletSessionTouchListener listener;


	public MobyletSession(String uid) {
		this.uid = uid;
		objectMap = new ConcurrentHashMap<Class<? extends Object>, Object>();
		touch();
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Long getTouchTime() {
		return touchTime;
	}

	public void setTouchListener(MobyletSessionTouchListener listener) {
		this.listener = listener;
	}

	public <T> void put(T object) {
		if (object == null) {
			return;
		}
		touch();
		objectMap.put(object.getClass(), object);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Class<T> key) {
		if (key == null) {
			return null;
		}
		touch();
		return (T)objectMap.get(key);
	}

	@SuppressWarnings("unchecked")
	public <T> T remove(Class<T> key) {
		if (key == null) {
			return null;
		}
		touch();
		return (T)objectMap.remove(key);
	}

	public void touch() {
		touchTime = System.currentTimeMillis();
		if (listener != null) {
			listener.actionTouch(this);
		}
	}

}

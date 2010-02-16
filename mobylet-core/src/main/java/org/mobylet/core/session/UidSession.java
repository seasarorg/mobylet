package org.mobylet.core.session;

import java.util.HashMap;

public class UidSession {

	protected String uid;

	protected Long touchTime;

	protected HashMap<Class<? extends Object>, Object> objectMap;

	protected UidSessionTouchListener listener;
	

	public UidSession(String uid) {
		this.uid = uid;
		objectMap = new HashMap<Class<? extends Object>, Object>();
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

	public void setTouchListener(UidSessionTouchListener listener) {
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

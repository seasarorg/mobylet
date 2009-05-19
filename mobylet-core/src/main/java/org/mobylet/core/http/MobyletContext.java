package org.mobylet.core.http;

import java.util.HashMap;
import java.util.Map;


public class MobyletContext {

	public static final String CONTEXT_KEY = MobyletContext.class.getName();

	protected Map<Class<?>, Object> context;


	public MobyletContext() {
		context = new HashMap<Class<?>, Object>();
	}

	@SuppressWarnings("unchecked")
	public <I> I get(Class<I> clazz) {
		return (I)context.get(clazz);
	}

	public void set(Object obj) {
		if (obj != null) {
			context.put(obj.getClass(), obj);
		}
	}

	public void remove(Class<?> clazz) {
		context.remove(clazz);
	}

}

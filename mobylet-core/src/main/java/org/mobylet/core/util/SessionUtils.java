package org.mobylet.core.util;

import org.mobylet.core.session.MobyletSession;

public class SessionUtils {

	public static boolean exist() {
		MobyletSession session = SingletonUtils.get(MobyletSession.class);
		return session.exist();
	}

	public static <T> T get(Class<T> clazz) {
		MobyletSession session = SingletonUtils.get(MobyletSession.class);
		return session.get(clazz);
	}

	public static <T> T get(String key) {
		MobyletSession session = SingletonUtils.get(MobyletSession.class);
		return session.get(key);
	}

	public static void invalidate() {
		MobyletSession session = SingletonUtils.get(MobyletSession.class);
		session.invalidate();
	}

	public static <T> T remove(Class<T> clazz) {
		MobyletSession session = SingletonUtils.get(MobyletSession.class);
		return session.remove(clazz);
	}

	public static <T> T remove(String key) {
		MobyletSession session = SingletonUtils.get(MobyletSession.class);
		return session.remove(key);
	}

	public static <T> void set(T obj) {
		MobyletSession session = SingletonUtils.get(MobyletSession.class);
		session.set(obj);
	}

	public static <T> void set(String key, T obj) {
		MobyletSession session = SingletonUtils.get(MobyletSession.class);
		session.set(key, obj);
	}

	public static void substitute() {
		MobyletSession session = SingletonUtils.get(MobyletSession.class);
		session.substitute();
	}

}

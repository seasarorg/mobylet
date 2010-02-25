package org.mobylet.core.util;

import java.lang.reflect.Field;

import org.mobylet.core.MobyletRuntimeException;

public class FieldUtils {

	public static void set(Field field, Object target, Object value) {
		try {
			field.set(target, value);
		} catch (Exception e) {
			throw new MobyletRuntimeException(
					"フィールド(" + field.getName() + ")に値をセット出来ません", e);
		}
	}

    public static String getString(Field field, Object target) {
		try {
			return (String) field.get(target);
		} catch (Exception e) {
			throw new MobyletRuntimeException(
					"フィールド(" + field.getName() + ")から値を取得できません", e);
		}
	}

}

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
package org.mobylet.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.mobylet.core.MobyletRuntimeException;

public class SerializeUtils {

	public static byte[] serialize2Bytes(Object obj) {
		if (obj == null) {
			return null;
		}
		ByteArrayOutputStream baos =
			new ByteArrayOutputStream(8192);
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(baos);
			synchronized (obj) {
				oos.writeObject(obj);
			}
			return baos.toByteArray();
		} catch (Exception e) {
			throw new MobyletRuntimeException("オブジェクトのシリアライズに失敗", e);
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					//NOP
				}
			}
		}
	}

	public static String serialize2Base64String(Object obj) {
		return Base64Utils.encode(serialize2Bytes(obj));
	}

	public static Object deserialize(String objString) {
		if (StringUtils.isEmpty(objString)) {
			return null;
		}
		ByteArrayInputStream bais =
			new ByteArrayInputStream(Base64Utils.decode(objString));
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			throw new MobyletRuntimeException("オブジェクトのデシリアライズに失敗", e);
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					//NOP
				}
			}
		}
	}

}

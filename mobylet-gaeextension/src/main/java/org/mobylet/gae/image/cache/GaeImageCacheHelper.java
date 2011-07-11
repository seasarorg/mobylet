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
package org.mobylet.gae.image.cache;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheManager;

import org.mobylet.core.MobyletRuntimeException;
import org.mobylet.core.image.impl.MobyletClassicImageCacheHelper;
import org.mobylet.core.util.InputStreamUtils;
import org.mobylet.core.util.StringUtils;

public class GaeImageCacheHelper extends MobyletClassicImageCacheHelper {

	protected URI dummyUri;

	protected Cache cache;

	protected Logger logger = Logger.getLogger("GAE-ImageCache");


	public GaeImageCacheHelper() {
		try {
			cache = CacheManager.getInstance().getCacheFactory()
				.createCache(Collections.emptyMap());
		} catch (CacheException e) {
			throw new MobyletRuntimeException(
					"キャッシュが使用出来ません" , e);
		}
		cache.clear();
	}

	@Override
	public URI getCacheBase() {
		if (dummyUri == null) {
			try {
				dummyUri = new URI("http://localhost/");
			} catch (URISyntaxException e) {
				//NOP
			}
		}
		return dummyUri;
	}

	@Override
	public boolean enableCache() {
		return true;
	}

	@Override
	public boolean existsCache(String key) {
		if (StringUtils.isEmpty(key)) {
			return false;
		}
		String conditionKey = key.substring(0, key.lastIndexOf('-'));
		return cache.get(conditionKey) != null;
	}

	@Override
	public InputStream get(String key) {
		if (StringUtils.isEmpty(key)) {
			return null;
		}
		String conditionKey = key.substring(0, key.lastIndexOf('-'));
		byte[] imageData = (byte[])cache.get(conditionKey);
		return new ByteArrayInputStream(imageData);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void put(String key, InputStream imgStream) {
		logger.log(Level.INFO, "Call Put = " + key);
		if (StringUtils.isEmpty(key)) {
			return;
		}
		int index = key.lastIndexOf('-');
		String conditionKey = key.substring(0, index);
		//String dateString = key.substring(index+1);
		//existsCache?
		if (!existsCache(key)) {
			logger.log(Level.INFO, "Write Cache = " + key);
			cache.put(conditionKey, InputStreamUtils.getAllBytes(imgStream));
		}
	}
}

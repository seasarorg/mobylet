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
import org.mobylet.core.image.impl.MobyletImageCacheHelper;
import org.mobylet.core.util.InputStreamUtils;
import org.mobylet.core.util.StringUtils;

public class GaeImageCacheHelper extends MobyletImageCacheHelper {

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
	@SuppressWarnings("unchecked")
	public boolean existsCache(String key) {
		if (StringUtils.isEmpty(key)) {
			return false;
		}
		String conditionKey = key.substring(0, key.lastIndexOf('-'));
		return cache.get(conditionKey) != null;
	}

	@Override
	@SuppressWarnings("unchecked")
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

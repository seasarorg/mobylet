package org.mobylet.core.image;

import java.io.InputStream;
import java.net.URI;


public interface ImageCacheHelper {

	public URI getCacheBase();

	public boolean enableCache();

	public String getCacheKey(String imgPath);

	public boolean existsCache(String key);

	public InputStream get(String key);

	public void put(String key, InputStream imgStream);

}

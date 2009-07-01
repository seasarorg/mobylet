package org.mobylet.gae.image.cache;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.jdo.PersistenceManager;

import org.mobylet.core.image.impl.MobyletImageCacheHelper;
import org.mobylet.core.util.InputStreamUtils;
import org.mobylet.core.util.StringUtils;

public class GaeImageCacheHelper extends MobyletImageCacheHelper {

	protected URI dummyUri;


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
		String contidionKey = key.substring(0, key.lastIndexOf('-'));
		PersistenceManager pm =
			PMF.get().getPersistenceManager();
		try {
			String query =
				"select from " + ImageCache.class.getName() +
				" where key == '" + contidionKey +"'";
			List<ImageCache> caches =
				(List<ImageCache>)pm.newQuery(query).execute();
			return !caches.isEmpty();
		} finally {
			pm.close();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public InputStream get(String key) {
		if (StringUtils.isEmpty(key)) {
			return null;
		}
		String contidionKey = key.substring(0, key.lastIndexOf('-'));
		PersistenceManager pm =
			PMF.get().getPersistenceManager();
		try {
			String query =
				"select from " + ImageCache.class.getName() +
				" where key == '" + contidionKey +"'";
			List<ImageCache> caches =
				(List<ImageCache>)pm.newQuery(query).execute();
			if (!caches.isEmpty()) {
				return new ByteArrayInputStream(caches.get(0).getImageData());
			}
		} finally {
			pm.close();
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void put(String key, InputStream imgStream) {
		if (StringUtils.isEmpty(key)) {
			return;
		}
		int index = key.lastIndexOf('-');
		String contidionKey = key.substring(0, index);
		String dateString = key.substring(index+1);
		//existsCache?
		PersistenceManager pm =
			PMF.get().getPersistenceManager();
		try {
			String query =
				"select from " + ImageCache.class.getName() +
				" where key == '" + contidionKey +"'";
			List<ImageCache> caches =
				(List<ImageCache>)pm.newQuery(query).execute();
			if (!caches.isEmpty()) {
				if (dateString.equals(caches.get(0).getDateString())) {
					return;
				}
			}
			//Cache
			ImageCache cache = new ImageCache();
			cache.setKey(key);
			cache.setDateString(dateString);
			cache.setImageData(InputStreamUtils.getAllBytes(imgStream));
			//Persistent
			pm.makePersistent(cache);
		} finally {
			pm.close();
		}
	}
}

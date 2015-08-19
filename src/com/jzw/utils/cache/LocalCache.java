package com.jzw.utils.cache;

import java.lang.ref.SoftReference;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class LocalCache implements Cache {

	private long timeout = TimeUnit.MINUTES.toNanos(3L);
	
	private ConcurrentHashMap<String, SoftReference<CacheItem>> map =
			new ConcurrentHashMap<String, SoftReference<CacheItem>>();

	private LocalCache(){};
	public static LocalCache create(long timeout){
		LocalCache cache = new LocalCache();
		cache.timeout = timeout;
		return cache;
	}
	static class CacheItem {
		public CacheItem(Object o) {
			timestamp = System.nanoTime();
			value = o;
		}
		private long timestamp;
		private Object value;
	}

	@Override
	public void put(String key, Object o) {
		SoftReference<CacheItem> item = 
				new SoftReference<LocalCache.CacheItem>(new CacheItem(o));
		map.put(key, item);
	}

	@Override
	public Object get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putIfAbsent(String key, Object o, MissCallback callback) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object remove(String key) {
		// TODO Auto-generated method stub
		return null;
	}

}

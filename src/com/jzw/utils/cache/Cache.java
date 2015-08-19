package com.jzw.utils.cache;

public interface Cache {
	void put(String key, Object o);
	Object get(String key);
	void putIfAbsent(String key, Object o, MissCallback callback);
	Object remove(String key);
}

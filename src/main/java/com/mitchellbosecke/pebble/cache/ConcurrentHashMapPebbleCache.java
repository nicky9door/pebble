package com.mitchellbosecke.pebble.cache;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapPebbleCache<K, V> implements PebbleCache<K, V> {
	private final ConcurrentHashMap<K, V> cache = new ConcurrentHashMap<>();

	@Override
	public V get(K key, Callable<V> generator) throws Exception {
		if(!cache.containsKey(key)){
			V value = generator.call();
			cache.putIfAbsent(key, value);
		}
		return cache.get(key);
	}
}

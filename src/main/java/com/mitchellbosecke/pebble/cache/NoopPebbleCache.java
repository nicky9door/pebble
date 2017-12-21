package com.mitchellbosecke.pebble.cache;

import java.util.concurrent.Callable;

public class NoopPebbleCache<K, V> implements PebbleCache<K, V> {

	@Override
	public V get(K key, Callable<V> generator) throws Exception {
		return generator.call();
	}
}

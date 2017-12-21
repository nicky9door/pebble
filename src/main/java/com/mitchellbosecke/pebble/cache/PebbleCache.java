package com.mitchellbosecke.pebble.cache;

import java.util.concurrent.Callable;

public interface PebbleCache<K, V> {
	V get(K key, Callable<V> generator) throws Exception;
}

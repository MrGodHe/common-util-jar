/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-05-26 20:10 创建
 *
 */
package cn.pay.ebank.common.cache;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author zhyang@ebank.pay.cn
 */

public abstract class KeyValueCache<K, V> extends Cache<K, V> {
	protected volatile ConcurrentMap<K, V> cache = new ConcurrentHashMap<K, V> ();

	@Override
	protected Object getCache () {
		return cache;
	}

	@Override
	protected Collection<V> values () {
		return cache.values ();
	}

	@Override
	public void addToCache (K key, V value) {
		if (key == null) {
			return;
		}
		cache.put (formatKey (key), value);
	}

	@Override
	public V getValue (K key) {
		if (key == null) {
			return null;
		}
		return cache.get (formatKey (key));
	}

	@Override
	public void clearCache () {
		cache.clear ();
	}

	@Override
	public void update (K key, V value) {
		if (key == null) {
			return;
		}
		addToCache (key, value);
	}

	@Override
	public void remove (K key) {
		if (key == null) {
			return;
		}
	}

	@Override
	public void update (V value, Object... parms) {
		if (value == null) {
			return;
		}
		addToCache (value, parms);
	}
}

/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-05-26 20:11 创建
 *
 */
package cn.pay.ebank.common.cache;

/**
 * @author zhyang@ebank.pay.cn
 */

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 值列表缓存基类
 *
 * @param <V>
 */
public abstract class ValueCache<V> extends Cache<V, V> {
	protected volatile ConcurrentMap<V, Boolean> cache = new ConcurrentHashMap<V, Boolean> ();

	@Override
	protected Object getCache () {
		return cache;
	}

	@Override
	protected Collection<V> values () {
		return cache.keySet ();
	}

	@Override
	public void addToCache (V key, V value) {
		if (key == null) {
			return;
		}
		cache.put (key, true);
	}

	@Override
	public void addToCache (V value, Object... parms) {
		addToCache (value, value);
	}

	@Override
	public V getValue (V key) {
		if (key == null) {
			return null;
		}
		return cache.containsKey (key) ? null : key;
	}

	@Override
	public void update (V key, V value) {
		if (key == null) {
			return;
		}
		cache.put (key, true);
	}

	@Override
	public void remove (V key) {
		if (key == null) {
			return;
		}
		cache.remove (key);
	}

	@Override
	public void update (V value, Object... parms) {
		if (value == null) {
			return;
		}
		cache.put (value, true);
	}

	@Override
	public void clearCache () {
		cache.clear ();
	}
}

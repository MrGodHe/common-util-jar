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

/**
 * LRU 缓存基础类
 *
 * @param <K>
 * @param <V>
 */
public abstract class LRUCacheBase<K, V> extends Cache<K, V> {

	public static final int DEFAULT_CACHE_SIZE = 1000;

	protected volatile LRUCache<K, V> cache = new LRUCache<K, V> (1000);

	public LRUCacheBase () {
		setCapacity (DEFAULT_CACHE_SIZE);
	}

	/**
	 * 缓存大小
	 *
	 * @param maxCapacity
	 */
	public LRUCacheBase (int maxCapacity) {
		setCapacity (maxCapacity);
	}

	@Override
	protected Object getCache () {
		return cache;
	}
	/**
	 * 设置缓存大小
	 *
	 * @param maxCapacity
	 */
	public void setCapacity (int maxCapacity) {
		if (maxCapacity <= 0) {
			maxCapacity = DEFAULT_CACHE_SIZE;
		}
		if (cache == null) {
			cache = new LRUCache<K, V> (maxCapacity);
		} else {
			LRUCache<K, V> cacheTmp = new LRUCache<K, V> (maxCapacity);
			synchronized (cache) {
				cacheTmp.putAll (cache);
				clearCache ();
				cache = cacheTmp;
			}
		}
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
		return cache.get (formatKey (key));
	}

	@Override
	public void update (K key, V value) {
		if (key == null) {
			return;
		}
		cache.put (key, value);
	}

	@Override
	public void update (V value, Object... parms) {
		if (value == null) {
			return;
		}
		addToCache (value, parms);
	}

	@Override
	public void remove (K key) {
		if (key == null) {
			return;
		}
		cache.remove (key);
	}

	@Override
	public void addToCache (V value, Object... parms) {

	}

	@Override
	public void clearCache () {
		cache.clear ();
	}
}

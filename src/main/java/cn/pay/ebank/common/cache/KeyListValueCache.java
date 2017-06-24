/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-05-26 20:09 创建
 *
 */
package cn.pay.ebank.common.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import cn.pay.ebank.common.utils.CollectionUtils;

/**
 * @author zhyang@ebank.pay.cn
 */

public abstract class KeyListValueCache<K, V> extends Cache<K, V> {

	protected volatile ConcurrentMap<K, List<V>> cache = new ConcurrentHashMap<K, List<V>> ();

	@Override
	protected Object getCache () {
		return cache;
	}

	@Override
	protected Collection<V> values () {
		Collection<List<V>> valuesList = cache.values ();
		List<V> values = new LinkedList<V> ();
		for (List<V> list : valuesList) {
			if (list != null) {
				values.addAll (list);
			}
		}
		return values;
	}

	@Override
	public void addToCache (K key, V value) {
		if (key == null) {
			return;
		}

		//格式化键
		key = formatKey (key);

		List<V> valueList = cache.get (key);
		if (valueList == null) {
			valueList = new ArrayList<V> ();
			cache.put (key, valueList);
		}
		valueList.add (value);
	}

	public List<V> getValues (K key) {
		if (key == null) {
			return null;
		}
		List<V> values = cache.get (formatKey (key));
		return values;
	}

	@Override
	public void clearCache () {
		cache.clear ();
	}


	/**
	 * 返回列表中的第一个元素
	 *
	 * @param key
	 * @return
	 */
	@Override
	public V getValue (K key) {
		List<V> values = getValues (key);
		if (CollectionUtils.isNotEmpty (values)) {
			return values.get (0);
		}
		return null;
	}

	@Override
	public void remove (K key) {
		cache.remove (key);
	}


	/**
	 * 更新列表中的值
	 *
	 * @param values
	 * @param value
	 */
	protected abstract void update (List<V> values, V value);

	@Override
	public void update (K key, V value) {
		List<V> values = cache.get (formatKey (key));
		if (CollectionUtils.isNotEmpty (values)) {
			update (values, value);
		} else {
			addToCache (key, value);
		}
	}
}

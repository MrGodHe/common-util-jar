/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-05-26 20:07 创建
 *
 */
package cn.pay.ebank.common.cache;

import java.util.List;

/**
 * @author zhyang@ebank.pay.cn
 */
public interface ICache {
	/**
	 * 默认缓存ID
	 */
	public static final int DEFAULT_CACHE_INDEX = -1;

	/**
	 * 组建缓存Key，拼接关键字
	 *
	 * @param keyFields
	 * @return
	 */
	public String buildCacheKey (Object... keyFields);


	/**
	 * 缓存名称
	 *
	 * @return
	 */
	public String getCacheName ();

	/**
	 * 获取缓存ID
	 *
	 * @return
	 */
	public int getCacheIndex ();

	/**
	 * 获取缓存中所有数据
	 *
	 * @return
	 */
	public <V> List<V> getAllValues ();
}

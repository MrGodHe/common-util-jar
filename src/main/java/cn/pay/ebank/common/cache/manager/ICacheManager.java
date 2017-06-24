/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-05-27 09:46 创建
 *
 */
package cn.pay.ebank.common.cache.manager;

/**
 * @author zhyang@ebank.pay.cn
 */
public interface ICacheManager<T> {
	/**
	 * 默认加载顺序
	 */
	public static final int PRORITY_LOAD_PRIORITY = 100;

	/**
	 * 优先加载顺序
	 */
	public static final int DEFAULT_LOAD_PRIORITY = 500;

	/**
	 * 延后加载顺序
	 */
	public static final int LAZY_LOAD_PRIORITY = 1000;

	/**
	 * 更新全部缓存
	 */
	public void updateCache (Object... params);

	/**
	 * 根据传入的Vo，过滤数据，并更新这些数据
	 * <p/>
	 * 如果vo==null，更新所有缓存
	 *
	 * @param vo 过滤条件
	 * @see ICacheManager#updateCache(Object... params)
	 */
	public void updateCache (T vo, Object... params);

	/**
	 * 缓存名称
	 *
	 * @return
	 */
	public String getCacheName ();

	/**
	 * 加载顺序
	 *
	 * @return
	 */
	public int loadPriority ();

	/**
	 * 缓存索引
	 * @return
	 */
	public long getCacheIndex();
}

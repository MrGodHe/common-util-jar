/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-05-27 09:47 创建
 *
 */
package cn.pay.ebank.common.cache.manager;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhyang@ebank.pay.cn
 */
public abstract class CacheManagerBase<T> implements ICacheManager<T> {
	/**
	 * 日志
	 */
	protected final Logger logger = LoggerFactory.getLogger (getClass ());

	@Override
	public void updateCache (Object... params) {
		logger.info ("开始更新[" + getCacheName () + "]缓存...");
		//加载数据
		List<T> datas = loadDatas (params);
		//更新缓存
		updateAllDatas (datas, params);
		logger.info ("已经更新[" + getCacheName () + "]完毕,更新[" + (datas == null ? 0 : datas.size ()) + "]条记录！\n\n");

	}

	@Override
	public void updateCache (T vo, Object... params) {
		if (vo == null) {
			updateCache (params);
			return;
		} else {
			logger.info ("开始更新[" + getCacheName () + "]缓存...");
			//加载数据
			logger.info ("过滤数据，vo=" + vo);

			List<T> datas = filterDatas (vo, params);
			//更新缓存
			updateDatas (datas, params);

			logger.info ("已经更新[" + getCacheName () + "]完毕,更新[" + (datas == null ? 0 : datas.size ()) + "]条记录！\n\n");
		}
	}

	/**
	 * 加载数据
	 *
	 * @return
	 */
	protected abstract List<T> loadDatas (Object... params);

	/**
	 * 先清除缓存，然后再更新缓存
	 *
	 * @param datas
	 */
	protected abstract void updateAllDatas (List<T> datas, Object... params);

	/**
	 * 直接更新缓存，用于部分更新<b/>默认更新全部
	 *
	 * @param datas
	 */
	protected void updateDatas (List<T> datas, Object... params){
		updateAllDatas (datas,params);
	}

	/**
	 * 通过VO更新缓存数据
	 *
	 * @param vo
	 * @return
	 */
	protected abstract List<T> filterDatas (T vo, Object... params);

	@Override
	public int loadPriority () {
		return DEFAULT_LOAD_PRIORITY;
	}


	@Override
	public long getCacheIndex () {
		return 0xFFFFFFFFFFFFFFFFL;
	}
}

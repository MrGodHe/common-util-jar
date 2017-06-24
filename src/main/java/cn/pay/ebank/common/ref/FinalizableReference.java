/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-04-21 22:41 创建
 *
 */
package cn.pay.ebank.common.ref;

/**
 * 带有回调清除功能的引用类型。
 *
 * @author zhyang@ebank.pay.cn
 */
public interface FinalizableReference <T> {

	/**
	 * 清除动作的回调方法。在该实现类包装的引用对象被垃圾回收器回收时调用该方法。
	 */
	void finalizeReferent ();
}

/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-04-21 22:36 创建
 *
 */
package cn.pay.ebank.common.utils;

/**
 * 一个判定器， {@link #decide(Object)} 方法用作判定是否符合条件。
 *
 * @param <T> 需要参与判定的对象的类型
 * @author zhyang@ebank.pay.cn
 */
public interface Decider<T> {
	/**
	 * 判定 t 是否符合条件。
	 *
	 * @param t 用做判定的对象。
	 * @return 如果 t 符合判定条件返回 true ，否则返回 false 。
	 */
	boolean decide (T t);
}

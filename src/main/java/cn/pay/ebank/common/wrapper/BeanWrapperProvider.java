/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-04-21 22:51 创建
 *
 */
package cn.pay.ebank.common.wrapper;

/**
 * bean包装器提供者，用于创建bean包装器实例。
 *
 * @author zhyang@ebank.pay.cn
 */
public interface BeanWrapperProvider {

	/**
	 * 创建一个包装器。
	 *
	 * @param bean 需要被包装的 bean 实例。
	 * @return 包装 bean 的包装器。
	 */
	BeanWrapper newBeanWrapper (Object bean);
}

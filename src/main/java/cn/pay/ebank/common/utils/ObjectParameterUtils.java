/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-04-21 22:01 创建
 *
 */
package cn.pay.ebank.common.utils;

/**
 * 对象参数工具。
 * @author zhyang@ebank.pay.cn
 */
public abstract  class ObjectParameterUtils {

	/**
	 * 将对象参数转换为{@link Class}参数形式。
	 *
	 * @param parameters 对象参数。
	 * @return 对象参数对应的{@link Class}参数，如果 parameters 为 null 则返回 null 。
	 */
	public static Class<?>[] processParameterToParameterType(Object... parameters) {
		if (parameters == null) {
			return null;
		}
		Class<?>[] parameter = new Class[parameters.length];
		for (int i = 0; i < parameters.length; i++) {
			parameter[i] = parameters[i].getClass();
		}
		return parameter;
	}
}

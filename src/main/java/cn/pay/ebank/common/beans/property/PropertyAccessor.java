/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-04-21 22:19 创建
 *
 */
package cn.pay.ebank.common.beans.property;

/**
 * 属性访问器，通过该访问器能更简单方便的访问 JavaBean 的属性。
 *
 * @author zhyang@ebank.pay.cn
 */
public interface PropertyAccessor {
	/**
	 * 得到 访问的属性的类型。
	 *
	 * @return 访问的属性的类型。
	 */
	Class<?> getType ();

	/**
	 * 得到 访问的属性在 bean 中的名称。
	 *
	 * @return 访问的属性在 bean 中的名称。
	 */
	Object getName ();

	/**
	 * 读取该属性的值。
	 *
	 * @return 该属性在 bean实例 中的值。
	 * @throws Exception 读取发生异常时。
	 */
	Object get () throws Exception;

	/**
	 * 为该属性写入值 value 。
	 *
	 * @param value 需要写入的值。
	 * @throws Exception 写入发生异常时。
	 */
	void set (Object value) throws Exception;

	/**
	 * 得到 访问属性的目标 bean 对象。
	 *
	 * @return 访问属性的目标 bean 对象。
	 */
	Object getTarget ();

	/**
	 * 创建一个访问属性类型的实例。
	 *
	 * @return 访问属性类型的实例。
	 * @throws NoSuchMethodException 如果没有指定的构造方法。
	 * @throws cn.pay.ebank.common.utils.InvocationTargetRunTimeException 如果构造方法内部抛出异常。
	 * @throws cn.pay.ebank.common.exception.CommonException 发生其他异常。
	 */
	Object propertyNewInstance () throws NoSuchMethodException;

}

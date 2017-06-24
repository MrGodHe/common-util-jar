/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * LiXiang 2015年1月22日 下午3:22:58 创建
 */
package cn.pay.ebank.common.beans.conversion;


import cn.pay.ebank.common.exception.NoSuchObjectException;

/**
 * 如果没有找到可用的类型转换器抛出该异常。
 * 
 * @author Agreal·Lee (e-mail:lixiang@ebank.pay.cn)
 * 
 */
public class ConverterNotFoundException extends NoSuchObjectException {
	
	private static final long serialVersionUID = 5811678321082505224L;
	
	private Class<?> targetType;
	
	/**
	 * 构造一个 ConverterNotFoundException 。
	 * @param sourceType 没有找到的类型转换器的源类型。
	 * @param sourceType 没有找到的类型转换器的目标类型。
	 */
	public ConverterNotFoundException(Class<?> sourceType, Class<?> targetType) {
		super(sourceType);
		this.targetType = targetType;
	}
	
	/**
	 * 构造一个 ConverterNotFoundException 。
	 * @param message 详细消息。
	 * @param sourceType 没有找到的类型转换器的源类型。
	 * @param sourceType 没有找到的类型转换器的目标类型。
	 */
	public ConverterNotFoundException(String message, Class<?> sourceType, Class<?> targetType) {
		super(sourceType, message);
		this.targetType = targetType;
	}
	
	public Class<?> getSourceType() {
		return (Class<?>) this.name;
	}
	
	public Class<?> getTargetType() {
		return this.targetType;
	}
	
	@Override
	public String toString() {
		String message = getLocalizedMessage();
		return getClass().getName() + " [sourceType=" + getSourceType() + ", targetType=" + getTargetType() + "]" + (message == null ? "" : message);
	}
	
}

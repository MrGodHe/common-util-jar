/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-04-21 22:14 创建
 *
 */
package cn.pay.ebank.common.utils;

import java.lang.reflect.InvocationTargetException;

import cn.pay.ebank.common.exception.CommonException;

/**
 * @author zhyang@ebank.pay.cn
 */
public class InvocationTargetRunTimeException extends CommonException {


	private InvocationTargetException invocationTargetException;


	/**
	 * 构造以 null 作为目标异常的 InvocationTargetRunTimeException。
	 */
	public InvocationTargetRunTimeException() {
		super();
	}

	/**
	 * 构造带目标异常的 InvocationTargetRunTimeException。
	 *
	 * @param target 目标异常
	 */
	public InvocationTargetRunTimeException(Throwable target) {
		super(target);
		if (target instanceof InvocationTargetException) {
			this.invocationTargetException = (InvocationTargetException) target;
		}
	}

	/**
	 * 构造带目标异常和详细消息的 InvocationTargetRunTimeException。
	 *
	 * @param target 目标异常
	 * @param s 详细消息
	 */
	public InvocationTargetRunTimeException(Throwable target, String s) {
		super(s, target);
	}

	/**
	 * 获得抛出的目标异常。
	 *
	 * @return 抛出的目标异常（此异常的原因）。
	 */
	public Throwable getTargetException() {
		return getCause();
	}

	/**
	 * 获得抛出的目标异常。
	 *
	 * @return 抛出的目标异常（此异常的原因）。
	 */
	@Override
	public Throwable getCause() {
		if (this.invocationTargetException != null) {
			return this.invocationTargetException.getTargetException();
		} else {
			return super.getCause();
		}
	}
}

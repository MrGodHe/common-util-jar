/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-04-21 22:59 创建
 *
 */
package cn.pay.ebank.common.exception;

/**
 * @author zhyang@ebank.pay.cn
 */
public class SystemException extends CommonException {

	private static final long serialVersionUID = 7769982372460790248L;

	/**
	 * 构造一个 SystemException 。
	 */
	public SystemException () {
		super ();
	}

	/**
	 * 构造一个 SystemException 。
	 *
	 * @param message 详细消息。
	 * @param cause   原因/相关的异常。
	 */
	public SystemException (String message, Throwable cause) {
		super (message, cause);
	}

	/**
	 * 构造一个 SystemException 。
	 *
	 * @param message 详细消息。
	 */
	public SystemException (String message) {
		super (message);
	}

	/**
	 * 构造一个 SystemException 。
	 *
	 * @param cause 原因/相关的异常。
	 */
	public SystemException (Throwable cause) {
		super (cause);
	}
}

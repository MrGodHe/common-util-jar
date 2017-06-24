/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-04-21 11:34 创建
 *
 */
package cn.pay.ebank.common.exception;

/**
 * 基础异常
 *
 * @author zhyang@ebank.pay.cn
 */
public class CommonException extends RuntimeException {
	public CommonException () {
		super ();
	}

	public CommonException (String message) {
		super (message);
	}

	public CommonException (String message, Throwable cause) {
		super (message, cause);
	}

	public CommonException (Throwable cause) {
		super (cause);
	}
}

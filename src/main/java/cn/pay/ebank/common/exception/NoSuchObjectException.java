/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-04-21 23:01 创建
 *
 */
package cn.pay.ebank.common.exception;

/**
 * @author zhyang@ebank.pay.cn
 */
public class NoSuchObjectException extends ResourceNotFoundException {

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = -7508422277727094661L;

	public NoSuchObjectException (Object name, String message, Throwable cause) {
		super (name, message, cause);
	}

	public NoSuchObjectException (Object name, String message) {
		super (name, message);
	}

	public NoSuchObjectException (Object name) {
		super (name);
	}

	public NoSuchObjectException (String message, Throwable cause) {
		super (message, cause);
	}

	public NoSuchObjectException (String message) {
		super (message);
	}
}

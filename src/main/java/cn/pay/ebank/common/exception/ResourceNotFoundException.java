/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-04-21 23:00 创建
 *
 */
package cn.pay.ebank.common.exception;

/**
 * 所需资源未找到的异常的基异常。
 *
 * @author zhyang@ebank.pay.cn
 */
public class ResourceNotFoundException extends SystemException {

	private static final long serialVersionUID = -941298082874015076L;

	/**
	 * 资源名
	 */
	protected final Object name;

	/**
	 * 基础构造方法，仅供子类使用。
	 */
	protected ResourceNotFoundException () {
		this (null, null, null);
	}

	/**
	 * 构造一个 ResourceNotFoundException 。
	 *
	 * @param name    资源名。
	 * @param message 详细消息。
	 */
	public ResourceNotFoundException (Object name, String message) {
		this (name, message, null);
	}

	/**
	 * 构造一个 ResourceNotFoundException 。
	 *
	 * @param name 资源名。
	 */
	public ResourceNotFoundException (Object name) {
		this (name, null, null);
	}

	/**
	 * 构造一个 ResourceNotFoundException 。
	 *
	 * @param name    资源名。
	 * @param message 详细消息。
	 * @param cause   原因/相关的异常。
	 */
	public ResourceNotFoundException (Object name, String message, Throwable cause) {
		super (message, cause);
		this.name = name;
	}

	/**
	 * 构造一个 ResourceNotFoundException 。
	 *
	 * @param message 详细消息。
	 * @param cause   原因/相关的异常。
	 */
	public ResourceNotFoundException (String message, Throwable cause) {
		this (null, message, cause);
	}

	/**
	 * 构造一个 ResourceNotFoundException 。
	 *
	 * @param message 详细消息。
	 */
	public ResourceNotFoundException (String message) {
		this (null, message, null);
	}

	/**
	 * 构造一个 ResourceNotFoundException 。
	 *
	 * @param cause 原因/相关的异常。
	 */
	public ResourceNotFoundException (Throwable cause) {
		this (null, null, cause);
	}

	@Override
	public synchronized Throwable fillInStackTrace () {
		return this;
	}

	/**
	 * 得到资源名。
	 *
	 * @return 资源名。
	 */
	public Object getName () {
		return this.name;
	}

	@Override
	public String toString () {
		String message = getLocalizedMessage ();
		return getClass ().getName () + " [name=" + this.name + "]" + (message == null ? "" : message);
	}
}

/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-04-21 22:06 创建
 *
 */
package cn.pay.ebank.common.utils;

import cn.pay.ebank.common.exception.CommonException;

/**
 * @author zhyang@ebank.pay.cn
 */
public class FileOperateException extends CommonException {
	public FileOperateException () {
		super ();
	}

	public FileOperateException (String message) {
		super (message);
	}

	public FileOperateException (String message, Throwable cause) {
		super (message, cause);
	}

	public FileOperateException (Throwable cause) {
		super (cause);
	}
}

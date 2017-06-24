/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-04-21 22:57 创建
 *
 */
package cn.pay.ebank.common.beans;

import cn.pay.ebank.common.exception.CommonException;

/**
 * @author zhyang@ebank.pay.cn
 */
public class InstantiationRuntimeException extends CommonException {
	public InstantiationRuntimeException () {
		super ();
	}

	public InstantiationRuntimeException (String message) {
		super (message);
	}

	public InstantiationRuntimeException (String message, Throwable cause) {
		super (message, cause);
	}

	public InstantiationRuntimeException (Throwable cause) {
		super (cause);
	}
}

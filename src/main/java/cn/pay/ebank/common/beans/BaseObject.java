/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-04-21 14:11 创建
 *
 */
package cn.pay.ebank.common.beans;

import java.io.Serializable;

import cn.pay.ebank.common.utils.ToStringBuilder;

/**
 * @author zhyang
 * @version 1.0
 * @filename BaseObject.java
 * @Date 2013-9-23
 */
public abstract class BaseObject implements Serializable {
	
	public String toString () {
		return ToStringBuilder.toString (this);
	}
}

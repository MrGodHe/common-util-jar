/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-05-13 20:03 创建
 *
 */
package cn.pay.ebank.common.facade.order;

import cn.pay.ebank.common.utils.ToStringBuilder;

/**
 * @author zhyang@ebank.pay.cn
 */
public abstract class OrderBase implements Order {
	
	/**
	 * 统一流水id
	 */
	private String gid;

	{
		setGid (GidGenerater.newGid ());
	}

	public String getGid() {
		return gid;
	}
	
	/**
	 *
	 * @param gid 为了保证gid全局唯一,请使用
	 */
	public void setGid(String gid) {
		this.gid = gid;
	}
	
	public String toString() {
		return ToStringBuilder.toString(this);
	}
}

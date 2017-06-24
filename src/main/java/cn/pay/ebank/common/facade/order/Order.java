/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-05-13 20:01 创建
 *
 */
package cn.pay.ebank.common.facade.order;

import java.io.Serializable;

/**
 * 接口入参对象接口
 */
public interface Order extends Serializable {
	
	/**
	 * 校验入参
	 */
	public void check();

	/**
	 * 全局唯一的ID
	 * @return
	 */
	public String getGid();
}

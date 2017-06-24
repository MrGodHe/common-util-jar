/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-05-13 19:19 创建
 *
 */
package cn.pay.ebank.common.facade.result;

import cn.pay.ebank.common.beans.enums.Status;

import java.io.Serializable;

/**
 * @author zhyang@ebank.pay.cn
 */
public interface Result extends Serializable{
	/**
	 * 得到结果状态。
	 * @return 结果状态。
	 */
	Status getStatus();

	/**
	 * 得到信息码。
	 * @return 信息码。
	 */
	String getCode();

	/**
	 * 得到描述。
	 * @return 描述。
	 */
	String getDescription();
}

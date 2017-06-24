/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhouyang@yiji.com 2016-04-12 14:16 创建
 *
 */
package cn.pay.ebank.common.beans;

import java.util.Map;

/**
 * @author zhouyang@yiji.com
 */
public interface IExtendFieldObject {
	/**
	 * 获取所有的扩展字段
	 *
	 * @return
	 */
	public Map<String, Object> getExtendFields ();
}

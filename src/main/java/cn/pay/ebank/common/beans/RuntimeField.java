/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhouyang@yiji.com 2016-04-12 14:19 创建
 *
 */
package cn.pay.ebank.common.beans;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhouyang@yiji.com
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RuntimeField {
	/**
	 * 字段别名
	 */
	String alias () default "";
}

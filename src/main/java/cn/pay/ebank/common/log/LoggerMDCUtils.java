/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhouyang@yiji.com 2016-04-12 09:46 创建
 *
 */
package cn.pay.ebank.common.log;

import org.slf4j.*;
import org.slf4j.LoggerFactory;

/**
 * @author zhouyang@yiji.com
 */
public class LoggerMDCUtils {
	private static org.slf4j.Logger logger = LoggerFactory.getLogger (LoggerMDCUtils.class);
	/**
	 * 使用logback mdc 特性，打印交易号或者其他可以作为用户请求凭证的信息，便于日志分析
	 */
	public static final String LOG_KEY_GID = "gid";
	public static final String LOG_KEY_IDENTIFY = "identify";

	public static void clear() {
		MDC.clear();
	}

	public static void setLogKey(String logKey) {
		clear();
		MDC.put(LOG_KEY_GID, logKey);
		MDC.put(LOG_KEY_IDENTIFY, "[" + logKey + "] ");
	}
}

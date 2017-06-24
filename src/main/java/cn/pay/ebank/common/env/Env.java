/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-05-13 17:10 创建
 *
 */
package cn.pay.ebank.common.env;

/**
 * @author zhyang@ebank.pay.cn
 */

import cn.pay.ebank.common.exception.CommonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import java.util.ArrayList;

/**
 * 环境变量帮助类
 * @author Bohr.Qiu <qzhanbo@ebank.pay.cn>
 */
public enum Env {
	online,
	pre,
	net,
	test,
	dev,
	local,
	stable,
	sdev,
	stest,
	snet,
	pnet,
	unknown;
	public static final String ENV_KEY = "spring.profiles.active";
	private static Env currEnv = null;
	private static String currEnvStr = null;
	private static final Logger logger = LoggerFactory.getLogger (Env.class.getName ());
	private static String port = null;
	private static final String ERROR_PORT = "-1";
	private static final String httpProtocol = "http";

	/**
	 * 获取当前系统环境变量
	 *
	 * @return 环境标识
	 */
	public static String getEnv() {
		if (currEnvStr == null) {
			currEnvStr = System.getProperty(ENV_KEY);
			if (currEnvStr == null) {
				currEnvStr = System.getenv(ENV_KEY);
			}
			if (currEnvStr != null) {
				try {
					currEnv = Env.valueOf(currEnvStr);
				} catch (IllegalArgumentException e) {
					//ignore
				}
			} else {
				throw new CommonException("需要配置系统变量或者环境变量spring.profiles.active");
			}
		}
		return currEnvStr;
	}

	/**
	 * 判断是否是线上环境 如果没有配置系统变量或者环境变量 spring.profiles.active ,此方法会抛出异常.
	 * @return 是否
	 */
	public static boolean isOnline() {
		return is(online);
	}

	/**
	 * 判断是否是本地开发环境
	 */
	public static boolean isLocal() {
		return is(local);
	}

	/**
	 * 判断是否是开发测试环境
	 */
	public static boolean isDev() {
		return is(dev);
	}

	/**
	 * 判断是否是测试环境
	 */
	public static boolean isTest() {
		return is(test);
	}

	/**
	 * 判断是否是预发布环境
	 */
	public static boolean isPre() {
		return is(pre);
	}

	/**
	 * 判断是否是联调环境
	 */
	public static boolean isNet() {
		return is(net);
	}

	/**
	 * 判断是否是稳定联调环境
	 */
	public static boolean isStable() {
		return is(stable);
	}

	/**
	 * 判读是否是某环境
	 *
	 * @param env 环境
	 */
	public static boolean is(Env env) {
		if (env == null) {
			throw new CommonException("env不能为null");
		}
		if (currEnv == null) {
			getEnv();
		}
		return env == currEnv;
	}

	public static String getPort() {
		if (port == null) {
			try {
				//for tomcat 7
				initPortByMBean(new ObjectName ("Catalina", "type", "Service"));
				//for tomcat embed 7
				initPortByMBean(new ObjectName("Tomcat", "type", "Service"));

			} catch (Exception e) {
				logger.warn("获取端口失败:{}", e.getMessage());
			}
			if (port == null) {
				port = ERROR_PORT;
			}
		}
		return port;
	}

	public static boolean isErrorPort(String port) {
		return ERROR_PORT.equals(port);
	}

	private static void initPortByMBean(ObjectName name) {
		if (port == null) {
			ArrayList<MBeanServer> mBeanServers = MBeanServerFactory.findMBeanServer (null);
			if (!mBeanServers.isEmpty()) {
				MBeanServer mBeanServer = mBeanServers.get(0);
				try {
					ObjectName[] objNames = (ObjectName[]) mBeanServer.getAttribute(name, "connectorNames");
					for (ObjectName on : objNames) {
						String protocol = (String) mBeanServer.getAttribute(on, "protocol");
						//HTTP/1.1  or  org.apache.coyote.http11.Http11NioProtocol
						//如果是ajp协议，不会出现http这个字，所有检测协议里是否包含http就可以活动正确的端口
						if (protocol.toLowerCase().contains(httpProtocol)) {
							Integer p = (Integer) mBeanServer.getAttribute(on, "localPort");
							if (p != null) {
								port = String.valueOf(p);
								logger.info("获取服务端口成功:{}", port);
							}
						}
					}
				} catch (Exception e) {
					logger.warn("通过{}获取服务端口失败", name.toString());
				}
			}
		}
	}
}
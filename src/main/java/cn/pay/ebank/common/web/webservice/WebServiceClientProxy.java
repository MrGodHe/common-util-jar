/**
 * www.yiji.com Inc.
 * Copyright (c) 2012 All Rights Reserved.
 */
package cn.pay.ebank.common.web.webservice;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

/**
 * 
 * 生成 cxf client proxy工具类 ，此工具类会缓存住生成的proxy对象
 * @Filename WebServiceClientProxy.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 */
public class WebServiceClientProxy {
	
	/*
	 * 缓存住生成的client proxy
	 */
	private static Map<String, Object> proxyCacheMap = new ConcurrentHashMap<String, Object>();
	
	/**
	 * 生成webservice client proxy
	 * @param clazz
	 * @param address
	 * @return
	 */
	public static Object connect(Class<?> clazz, String address) {
		String key = clazz.getName() + ":" + address;
		if (proxyCacheMap.containsKey(key)) {
			return proxyCacheMap.get(key);
		} else {
			// JaxWsProxyFactoryBean 不是threadsafe的，在生成client对象时，不能重用JaxWsProxyFactoryBean，但是生成的client proxy 是threadsafe的
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.setServiceClass(clazz);
			factory.setAddress(address);
			Object obj = factory.create();
			proxyCacheMap.put(key, obj);
			return proxyCacheMap.get(key);
		}
	}
}

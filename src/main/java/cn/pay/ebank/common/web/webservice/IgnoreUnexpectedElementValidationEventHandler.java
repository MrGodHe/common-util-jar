/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * qzhanbo@ebank.pay.cn 2014-05-25 00:23 创建
 *
 */
package cn.pay.ebank.common.web.webservice;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

import cn.pay.ebank.common.env.Env;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <p>
 * 处理cxf `unexpected element`异常
 * <p/>
 * 当cxf传输的数据对象结构变化时,比如请求对象减少了字段,响应对象增加了字段,在jaxb unmarsh时会抛出异常,导致接口访问失败.报:
 * 
 * <pre class="code">
 * javax.xml.bind.UnmarshalException: unexpected element (uri:"", local:"name"). Expected elements are <{}name>
 * </pre>
 *
 * 上面这个异常在cxf客户端请求中多了一个`name`属性,或者cxf服务端响应中多了一个`name`属性时抛出.
 * <h3>USAGE</h3>
 * 在spring配置文件中配置:
 * 
 * <pre class="code">
 * {@code
 *
 *   <cxf:bus>
 *      <cxf:properties>
 *          <entry key="jaxb-validation-event-handler">
 *              <bean class="com.yjf.common.webservice.IgnoreUnexpectedElementValidationEventHandler"/>
 *          </entry>
 *      </cxf:properties>
 *   </cxf:bus>
 *  }
 * </pre>
 *
 * <h3><b>注意:</b></h3>
 * 1.此实现只针对线上环境启用,线下环境早点抛出异常是好事,提醒我们应该升级包<br/>
 * 2.如果没有配置系统变量或者环境变量 spring.profiles.active,会抛出环境没有配置的异常.
 *
 *
 * @see <a
 * href="http://bohr.me/2014/05/15/2014-05-reading-notes.html#cxf_unexpected_element">详细分析:烦人的cxf
 * unexpected element异常</a>
 * @author qzhanbo@ebank.pay.cn
 */
public class IgnoreUnexpectedElementValidationEventHandler implements ValidationEventHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(IgnoreUnexpectedElementValidationEventHandler.class);
	
	private static boolean isOnline = Env.isOnline ();
	
	@Override
	public boolean handleEvent(ValidationEvent event) {
		String msg = event.getMessage();
		if (isOnline && msg != null && msg.startsWith("unexpected element")) {
			logger.warn("{}", msg);
			return true;
		}
		return false;
	}
}

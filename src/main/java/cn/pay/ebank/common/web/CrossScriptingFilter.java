/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-05-13 17:58 创建
 *
 */
package cn.pay.ebank.common.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 跨站攻击防御filter
 *
 * <h3>Usage Examples</h3>
 *
 * 在web.xml中加入配置
 *
 * <pre class="code">
 * {@code
 *		<filter>
 *			<filter-name>XSS</filter-name>
 *			<filter-class>cn.pay.ebank.common.web.CrossScriptingFilter</filter-class>
 *			<!--
 *			<init-param>
 *				<param-name>excludeUrl</param-name>
 *				<param-value>xxxx</param-value>
 *				<param-name>excludeUrlPath</param-name>
 *				<param-value>xxxx,xxxx,xxxx</param-value>
 *				<param-name>escapeJavaScript</param-name>
 *				<param-value>false</param-value>
 *			    <param-name>escapeHtml</param-name>
 *				<param-value>false</param-value>
 *			</init-param>
 *			-->
 *		</filter>
 *		<filter-mapping>
 *			<filter-name>XSS</filter-name>
 *			<url-pattern>/*.do</url-pattern>
 *		</filter-mapping>
 *
 * }
 * </pre>
 * <p>
 *
 * 参数说明：<br/>
 * 1.excludeUrl 不执行xss过滤路的径正则表达式<br/>
 * 2.excludeUrlPath 不执行xss过滤的路径，用逗号隔开<br/>
 * 3.escapeJavaScript 过滤javascript <br/>
 * 4.escapeHtml，过滤html 会把'"><转换成html实体<br/>
 *
 *
 * @author zhyang@ebank.pay.cn
 */
public class CrossScriptingFilter  implements Filter {
	protected static final Logger logger = LoggerFactory.getLogger (CrossScriptingFilter.class);
	public static final String EXCLUDE_URL = "excludeUrl";
	public static final String EXCLUDE_URL_PATH = "excludeUrlPath";
	public static final String AUTO_CHECK = "autoCheck";
	public static final String ESCAPE_JAVASCRIPT = "escapeJavaScript";
	public static final String ESCAPE_HTML = "escapeHtml";

	private static final String ALLOW_METHOD = "GET,HEAD,POST";

	private XssConfig xssConfig;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("防跨域站点攻击filter启动");
		xssConfig = new XssConfig();
		xssConfig.setExcludeUrlPath(filterConfig.getInitParameter(EXCLUDE_URL_PATH));
		xssConfig.setExcludeUrl(filterConfig.getInitParameter(EXCLUDE_URL));
		xssConfig.setEscapeJavaScript(Boolean.valueOf(filterConfig.getInitParameter(ESCAPE_JAVASCRIPT)));
		xssConfig.setEscapeHtml(Boolean.valueOf(filterConfig.getInitParameter(ESCAPE_HTML)));
		xssConfig.setAutoCheck(Boolean.valueOf(filterConfig.getInitParameter(AUTO_CHECK)));
		logger.info("XSS过滤器配置:{}", xssConfig);

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//防重复执行
		if (request.getAttribute(CrossScriptingFilter.class.getName()) != null) {
			chain.doFilter(request, response);
			return;
		}
		request.setAttribute(CrossScriptingFilter.class.getName(), Boolean.TRUE);

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = ((HttpServletResponse) response);

		//设置所有的响应Allow
		httpServletResponse.setHeader("Allow", ALLOW_METHOD);

		//判断是否为排除uri
		if (xssConfig.isExcluded((httpServletRequest).getRequestURI())) {
			chain.doFilter(request, response);
			return;
		}
		//检查是否为合法的method方法
		String method = httpServletRequest.getMethod();
		if (method != null && !ALLOW_METHOD.contains(method)) {
			//下面这行语句在tomcat下面完全无效果，allow被他替换了。
			//			httpServletResponse.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			PrintWriter pw = httpServletResponse.getWriter();
			httpServletResponse.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			pw.print("Method Not Allowed");
			pw.flush();
			return;
		}

		chain.doFilter(new XSSRequestWrapper(httpServletRequest, xssConfig), response);
	}

	@Override
	public void destroy() {
	}

}

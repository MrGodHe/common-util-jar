/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-05-13 17:57 创建
 *
 */
package cn.pay.ebank.common.web;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 客户端环境信息收集filter
 * <p/>
 * 也可以使用 cn.pay.ebank.common.web.ClientEnvInfo#getClientEnvInfo(javax.servlet.http.
 * HttpServletRequest)处理特定请求
 *
 *
 * @author zhyang@ebank.pay.cn
 */
public class ClientInfoFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		ClientEnvInfo.getClientEnvInfo(httpServletRequest, false);
		chain.doFilter(httpServletRequest, response);
	}

	@Override
	public void destroy() {

	}

}

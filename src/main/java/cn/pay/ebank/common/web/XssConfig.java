/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-05-13 17:59 创建
 *
 */
package cn.pay.ebank.common.web;

import cn.pay.ebank.common.utils.StringUtils;

import java.io.Serializable;
import java.util.regex.Pattern;

/**
 * @author zhyang@ebank.pay.cn
 */
public class XssConfig implements Serializable {
	private boolean autoCheck;
	private boolean escapeJavaScript;
	private boolean escapeHtml;
	private String excludeUrl;
	private String excludeUrlPath;
	private String[] excludePaths = null;

	/**
	 * 不过滤路径正则表达式
	 */
	private Pattern EXCLUDE_PATTERN = null;

	public boolean isAutoCheck() {
		return autoCheck;
	}

	public void setAutoCheck(boolean autoCheck) {
		this.autoCheck = autoCheck;
	}

	public boolean isEscapeHtml() {
		return escapeHtml;
	}

	public void setEscapeHtml(boolean escapeHtml) {
		this.escapeHtml = escapeHtml;
	}

	public boolean isEscapeJavaScript() {
		return escapeJavaScript;
	}

	public void setEscapeJavaScript(boolean escapeJavaScript) {
		this.escapeJavaScript = escapeJavaScript;
	}

	public String getExcludeUrl() {
		return excludeUrl;
	}

	public void setExcludeUrl(String excludeUrl) {
		if (excludeUrl != null) {
			EXCLUDE_PATTERN = Pattern.compile(excludeUrl, Pattern.CASE_INSENSITIVE);
		}
		this.excludeUrl = excludeUrl;
	}

	public String getExcludeUrlPath() {
		return excludeUrlPath;
	}

	public void setExcludeUrlPath(String excludeUrlPath) {
		this.excludeUrlPath = excludeUrlPath;
		if (StringUtils.isNotBlank (excludeUrlPath)) {
			excludePaths = excludeUrlPath.split(",");
		}
	}

	/**
	 * 请求uri是否不过滤
	 */
	public boolean isExcluded(String requestURI) {
		if (EXCLUDE_PATTERN != null) {
			if (EXCLUDE_PATTERN.matcher(requestURI).matches()) {
				return true;
			}
		}
		if (excludePaths != null) {
			for (String path : excludePaths) {
				if (requestURI.startsWith(path)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("XssConfig{");
		sb.append("autoCheck=").append(autoCheck);
		sb.append(", escapeHtml=").append(escapeHtml);
		sb.append(", escapeJavaScript=").append(escapeJavaScript);
		sb.append(", 排除过滤路径正则表达式='").append(excludeUrl).append('\'');
		sb.append(", 排除过滤路径='").append(excludeUrlPath).append('\'');
		sb.append('}');
		return sb.toString();
	}
}

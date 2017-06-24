/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-05-13 18:00 创建
 *
 */
package cn.pay.ebank.common.web;

import cn.pay.ebank.common.exception.CommonException;
import cn.pay.ebank.common.web.escape.Entities;
import com.google.common.collect.Lists;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.owasp.esapi.Encoder;
import org.owasp.esapi.reference.DefaultEncoder;
/**
 * @author zhyang@ebank.pay.cn
 */
public class XSSRequestWrapper  extends HttpServletRequestWrapper {

	private static Pattern patterns = Pattern.compile("<script>(.*?)</script>" + "|src[\r\n]*=[\r\n]*\\\'(.*?)\\\'"
					+ "|src[\r\n]*=[\r\n]*\\\"(.*?)\\\"" + "|</script>" + "|<script(.*?)>" + "|eval\\((.*?)\\)"
					+ "|expression\\((.*?)\\)" + "|javascript:" + "|vbscript:" + "|onload(.*?)=",
			Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

	private static Pattern patterns2 = Pattern.compile("(<.*>.*</.*>)|(<.*/?>)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);

	private static Pattern comment = Pattern.compile("/\\*.*\\*/", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
	private static Encoder encoder = new DefaultEncoder(Lists.newArrayList ("HTMLEntityCodec", "PercentCodec"));
	/**
	 * 对象是否被检测
	 */
	private boolean checked = false;

	private static boolean escapeJavaScript = false;
	private static boolean escapeHtml = false;

	private XssConfig xssConfig;

	public XSSRequestWrapper(HttpServletRequest request, XssConfig xssConfig) {
		super(request);
		this.xssConfig = xssConfig;
		escapeJavaScript = xssConfig.isEscapeJavaScript();
		escapeHtml = xssConfig.isEscapeHtml();
	}

	public void autoCheck() {
		if (xssConfig.isAutoCheck() && !checked) {
			checkXSS();
		}
	}

	/**
	 * 检查是否被xss
	 */
	public void checkXSS() {
		Set<Map.Entry<String, String[]>> set = super.getParameterMap().entrySet();
		for (Map.Entry<String, String[]> entry : set) {
			String[] values = entry.getValue();
			if (values != null) {
				for (String value : values) {
					try {
						value = canonicalize(value);
					} catch (Exception e) {
						throw new CommonException (String.format("参数:[%s] 值:[%s]发现XSS注入", entry.getKey(), value));
					}
					if (patterns.matcher(value).find() || patterns2.matcher(value).find()) {
						throw new CommonException (String.format("参数:[%s] 值:[%s]发现XSS注入", entry.getKey(), value));
					}
				}
			}

		}
		checked = true;
	}

	private static String canonicalize(String value) {
		value = encoder.canonicalize(value, true, false);
		return value;
	}

	@Override
	public String[] getParameterValues(String parameter) {
		return stripXSS(super.getParameterValues(parameter));
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		Map<String, String[]> parameterMap = super.getParameterMap();
		if (parameterMap == null) {
			return null;
		}
		Map<String, String[]> newParameterMap = new HashMap<String, String[]> ();
		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			newParameterMap.put(entry.getKey(), stripXSS(entry.getValue()));
		}
		return Collections.unmodifiableMap (newParameterMap);
	}

	/**
	 * 对参数value进行xss 过滤
	 */
	@Override
	public String getParameter(String parameter) {
		return stripXSS(super.getParameter(parameter));
	}

	/**
	 * 对参数value进行xss 过滤
	 */
	@Override
	public String getHeader(String name) {
		return stripXSS(super.getHeader(name));
	}

	/**
	 *
	 * 防止编码攻击方法，检查传入的参数 也可以当做工具类使用
	 * <img%20src%3D%26%23x6a;%26%23x61;%26%23x76;%26
	 * %23x61;%26%23x73;%26%23x63;%
	 * 26%23x72;%26%23x69;%26%23x70;%26%23x74;%26%23x3a
	 * ;alert%26%23x28;27111%26%23x29;>
	 */
	public static String stripXSS(String value) {
		if (value != null) {
			// 防止编码攻击,获得解码后的字符串
			value = canonicalize(value);

			// 避免 null
			value = value.replaceAll("\0", "");
			//避免注释
			value = comment.matcher(value).replaceAll("");

			// Remove all sections that match a pattern
			value = patterns.matcher(value).replaceAll("");
			if (escapeJavaScript) {
				value = StringEscapeUtils.escapeJavaScript (value);
			}
			if (escapeHtml) {
				value = Entities.BASIC.escape(value);
			}
		}
		return value;
	}

	public static String[] stripXSS(String[] values) {
		if (values == null) {
			return null;
		}
		String[] encodedValues = new String[values.length];
		for (int i = 0; i < values.length; i++) {
			encodedValues[i] = stripXSS(values[i]);
		}
		return encodedValues;
	}

}

/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-05-26 20:08 创建
 *
 */
package cn.pay.ebank.common.cache;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhyang@ebank.pay.cn
 */
public enum FilterStringType {
	/** 字符相等*/
	EQUALS("equals", "相等"),

	/** 字符相等(不区分大小写)*/
	EQUALS_IGNORE_CASE("equalsIgnoreCase", "模糊相等"),

	/** 正则匹配 */
	MATCHES("matches", "正则匹配"),

	/** 包含 */
	CONTAINS("contains", "包含"),

	/** 开始以 */
	START_WITH("startsWith", "开始以"),

	/** 结束以 */
	END_WITH("endsWith", "结束以"),

	;

	/** 枚举值 */
	private final String	code;

	/** 枚举描述 */
	private final String	message;

	/**
	 * 构造一个<code>FilterStringType</code>枚举对象
	 *
	 * @param code
	 * @param message
	 */
	private FilterStringType(String code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return Returns the code.
	 */
	public String code() {
		return code;
	}

	/**
	 * @return Returns the message.
	 */
	public String message() {
		return message;
	}

	/**
	 * 通过枚举<code>code</code>获得枚举
	 *
	 * @param code
	 * @return FilterStringType
	 */
	public static FilterStringType getByCode(String code) {
		for (FilterStringType _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}

	/**
	 * 获取全部枚举
	 *
	 * @return List<FilterStringType>
	 */
	public static List<FilterStringType> getAllEnum() {
		List<FilterStringType> list = new ArrayList<FilterStringType> ();
		for (FilterStringType _enum : values()) {
			list.add(_enum);
		}
		return list;
	}

	/**
	 * 获取全部枚举值
	 *
	 * @return List<String>
	 */
	public static List<String> getAllEnumCode() {
		List<String> list = new ArrayList<String>();
		for (FilterStringType _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}
}

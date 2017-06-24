/**
 * www.hf.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package cn.pay.ebank.common.beans.enums;

import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *                       
 * @Filename CardTypeEnum.java
 *
 * @Description 银行卡类别
 *
 * @Version 1.0
 *
 */
public enum CardTypeEnum {
	
	/** 信用卡 */
	CREDIT("CREDIT", "贷记卡"),

	/** 借记卡 */
	DEBIT("DEBIT", "借记卡"),

	/** 准贷记卡 */
	SEMI_CREDIT("SEMI_CREDIT", "准贷记卡"),

	/** 预付费卡 */
	PREPAID("PREPAID", "预付费卡"),

	/** 借贷一体卡 */
	DEBIT_CREDIT("DEBIT_CREDIT", "借贷一体"),

	/** 所有卡种 */
	ALL("ALL", "所有卡种");
	
	/** 枚举值 */
	private final String	code;
	
	/** 枚举描述 */
	private final String	message;
	
	/**
	 * 构造一个<code>PortNoResultEnum</code>枚举对象
	 *
	 * @param code
	 * @param message
	 */
	private CardTypeEnum(String code, String message) {
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
	 * @return PortNoResultEnum
	 */
	public static CardTypeEnum getByCode(String code) {
		for (CardTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<PortNoResultEnum>
	 */
	public List<CardTypeEnum> getAllEnum() {
		List<CardTypeEnum> list = new ArrayList<CardTypeEnum>();
		for (CardTypeEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}
	
	/**
	 * 获取全部枚举值
	 * 
	 * @return List<String>
	 */
	public List<String> getAllEnumCode() {
		List<String> list = new ArrayList<String>();
		for (CardTypeEnum _enum : values()) {
			list.add(_enum.code());
		}
		return list;
	}

	/**
	 * 判断给定的枚举，是否在列表中
	 *
	 * @param value 检查的值
	 * @param values 列表
	 * @return
	 */
	public boolean isInList(CardTypeEnum value, CardTypeEnum... values) {
		for (CardTypeEnum e : values) {
			if (value == e) {
				return true;
			}
		}
		return false;
	}
	public static Map<String, String> mapping() {
		Map<String, String> map = Maps.newLinkedHashMap();
		for (CardTypeEnum type : values()) {
			map.put(type.getCode(), type.getMessage());
		}
		return map;
	}
}

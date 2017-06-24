/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-04-21 14:11 创建
 *
 */
package cn.pay.ebank.common.beans.enums;

/**
 * @author zhouyang@ebank.pay.cn
 */
public enum PersonalCorporateTypeEnum {
	/** 对私 */
	PERSONAL("PERSONAL", "对私"),

	/** 对公 */
	CORPORATE("CORPORATE", "对公"),

	/** 对公对私 */
	ALL("ALL" , "对公对私");

	/**
	 * 枚举值
	 */
	private final String code;
	
	/**
	 * 枚举描述
	 */
	private final String message;
	
	/**
	 * 构造一个<code>PersonalCorporateTypeEnum</code>枚举对象
	 *
	 * @param code
	 * @param message
	 */
	private PersonalCorporateTypeEnum (String code, String message) {
		this.code = code;
		this.message = message;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String getCode () {
		return code;
	}
	
	/**
	 * @return Returns the message.
	 */
	public String getMessage () {
		return message;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String code () {
		return code;
	}
	
	/**
	 * @return Returns the message.
	 */
	public String message () {
		return message;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 *
	 * @param code
	 *
	 * @return PersonalCorporateTypeEnum.java
	 */
	public static PersonalCorporateTypeEnum getByCode (String code) {
		for (PersonalCorporateTypeEnum _enum : values ()) {
			if (_enum.getCode ().equalsIgnoreCase (code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 *
	 * @return List<LocalCacheEnum>
	 */
	public java.util.List<PersonalCorporateTypeEnum> getAllEnum () {
		java.util.List<PersonalCorporateTypeEnum> list = new java.util.ArrayList<PersonalCorporateTypeEnum> ();
		for (PersonalCorporateTypeEnum _enum : values ()) {
			list.add (_enum);
		}
		return list;
	}
	
	/**
	 * 获取全部枚举值
	 *
	 * @return List<String>
	 */
	public java.util.List<String> getAllEnumCode () {
		java.util.List<String> list = new java.util.ArrayList<String> ();
		for (PersonalCorporateTypeEnum _enum : values ()) {
			list.add (_enum.code ());
		}
		return list;
	}
	
	/**
	 * 判断给定的枚举，是否在列表中
	 *
	 * @param values 列表
	 *
	 * @return
	 */
	public boolean isInList (PersonalCorporateTypeEnum... values) {
		for (PersonalCorporateTypeEnum e : values) {
			if (this == e) {
				return true;
			}
		}
		return false;
	}
}

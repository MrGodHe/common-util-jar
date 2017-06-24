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
 * @Filename CertTypeEnum.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author peigen
 * 
 * @Email peigen@ebank.pay.cn
 * 
 * @History <li>Author: peigen</li> <li>Date: 2012-7-16</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 * 
 */
public enum CertTypeEnum {
	
	/** 身份证 */
	IDENTITY_CARD("ID", "身份证"),
	/** 军官证 */
	ARMY_IDENTITY_CARD("ARMYID", "军官证"),
	/** 护照 */
	PASSPORT("PASSPORT", "护照"),
	/** 香港居民往来内地通行证 */
	XIANGGONG_HOME_RETURN_PERMIT("HK_HRP", "香港居民往来内地通行证"),
	/** 澳门居民往来内地通行证 */
	AOMEN_HOME_RETURN_PERMIT("AOMEN_HRP", "澳门居民往来内地通行证"),
	/** 台湾居民来往大陆通行证 */
	TAIWAN_HOME_RETURN_PERMIT("TW_HRPT", "台湾居民来往大陆通行证"),
	/** 武装警察身份证件 */
	OFFICERS_CARD("OC", "武装警察身份证件"),
	/** 军人身份证件 */
	SOLDIERS_CARD("SC", "军人身份证件"),
	/** 户口簿 */
	RESIDENCE_BOOKLET("RB", "户口簿"),
	/** 其它证件 */
	OTHER("OTHER", "其它证件"),
	/** 营业执照 */
	BUSINESS_LICENSE("BL", "营业执照"),
	/** 税务登记证 */
	TAX_REGISTRATION_CERTIFICATE("TRC", "税务登记证"),
	/** 组织机构代码证 */
	ON_BEHALF_OF_THE_ORGANIZATION_LICENSE("OL", "组织机构代码证");
	
	/** 枚举值 */
	private final String code;
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 构造一个<code>CertTypeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private CertTypeEnum(String code, String message) {
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
	 * @return CertTypeEnum
	 */
	public static CertTypeEnum getByCode(String code) {
		for (CertTypeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<CertTypeEnum>
	 */
	public List<CertTypeEnum> getAllEnum() {
		List<CertTypeEnum> list = new ArrayList<CertTypeEnum>();
		for (CertTypeEnum _enum : values()) {
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
		for (CertTypeEnum _enum : values()) {
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
	public boolean isInList(CertTypeEnum value, CertTypeEnum... values) {
		for (CertTypeEnum e : values) {
			if (value == e) {
				return true;
			}
		}
		return false;
	}

	public static Map<String, String> mapping() {
		Map<String, String> map = Maps.newLinkedHashMap();
		for (CertTypeEnum type : values()) {
			map.put(type.getCode(), type.getMessage());
		}
		return map;
	}
}

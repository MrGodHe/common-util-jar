/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-05-13 19:20 创建
 *
 */
package cn.pay.ebank.common.facade.result;


import cn.pay.ebank.common.beans.enums.Status;
import cn.pay.ebank.common.utils.ToStringBuilder;

/**
 * @author zhyang@ebank.pay.cn
 */
public class StandardResultInfo implements Result {
	/** 结果状态 */
	protected Status status;
	
	/** 信息码 */
	protected String code;
	
	/** 描述 */
	protected String description;

	public StandardResultInfo () {
	}

	/**
	 * 构建一个 StandardResultInfo 。
	 * @param status 结果状态。
	 */
	public StandardResultInfo(Status status) {
		this(status, null, null);
	}
	
	/**
	 * 构建一个 StandardResultInfo 。
	 * @param status 结果状态。
	 * @param code 信息码。
	 */
	public StandardResultInfo(Status status, String code) {
		this(status, code, null);
	}
	
	/**
	 * 构建一个 StandardResultInfo 。
	 * @param status 结果状态。
	 * @param code 信息码。
	 * @param description 描述。
	 */
	public StandardResultInfo(Status status, String code, String description) {
		this.status = status;
		this.code = code;
		this.description = description;
	}
	
	@Override
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Override
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	@Override
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.toString (this);
	}
}

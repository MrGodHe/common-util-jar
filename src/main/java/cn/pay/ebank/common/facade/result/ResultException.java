/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-05-13 20:49 创建
 *
 */
package cn.pay.ebank.common.facade.result;

import cn.pay.ebank.common.beans.enums.Status;
import cn.pay.ebank.common.exception.CommonException;

/**
 * @author zhyang@ebank.pay.cn
 */
public class ResultException extends CommonException {
	
	/** 信息码 */
	protected String code;
	
	/** 描述 */
	protected String description;
	
	/**
	 * 构建一个 ResultException 。
	 */
	public ResultException() {
	}
	
	/**
	 * 构建一个 ResultException 。
	 * @param code 信息码。
	 */
	public ResultException(String code) {
		this(code, null, null, null);
	}
	
	/**
	 * 构建一个 ResultException 。
	 * @param code 信息码。
	 * @param description 描述。
	 */
	public ResultException(String code, String description) {
		this(code, description, null, null);
	}
	
	/**
	 * 构建一个 ResultException 。
	 * @param code 信息码。
	 * @param t 关联的异常。
	 */
	public ResultException(String code, Throwable t) {
		this(code, null, null, t);
	}
	
	/**
	 * 构建一个 ResultException 。
	 * @param code 信息码。
	 * @param message 异常消息。
	 * @param t 关联的异常。
	 */
	public ResultException(String code, String message, Throwable t) {
		this(code, null, message, t);
	}
	
	/**
	 * 构建一个 ResultException 。
	 * @param code 信息码。
	 * @param description 描述。
	 * @param message 异常消息。
	 * @param t 关联的异常。
	 */
	public ResultException(String code, String description, String message, Throwable t) {
		super(message, t);
		this.code = code;
		this.description = description;
	}
	
	/**
	 * 得到信息码。
	 * @return 信息码。
	 */
	public String getCode() {
		return this.code;
	}
	
	/**
	 * 设置信息码。
	 * @param code 信息码。
	 */
	public void setCode(String code) {
		this.code = code;
	}
	
	/**
	 * 得到描述。
	 * @return 描述。
	 */
	public String getDescription() {
		return this.description;
	}
	
	/**
	 * 设置描述。
	 * @param description 描述。
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 转换该异常为一个返回结果对象。
	 * <p>
	 * 返回的结果对象中的信息码和描述与该异常中的一致，并且状态为失败。
	 * @return 当前异常转换的返回结果对象。
	 */
	public Result toResultInfo() {
		return new StandardResultInfo(Status.FAIL, this.code,
			this.description == null ? getMessage() : this.description);
	}
	
	@Override
	public synchronized Throwable fillInStackTrace() {
		return super.fillInStackTrace();
	}
}

/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-05-26 18:46 创建
 *
 */
package cn.pay.ebank.common.web;

import cn.pay.ebank.common.beans.ParameterHolderSupport;

/**
 * @author zhyang@ebank.pay.cn
 */
public class OperationContext extends ParameterHolderSupport {

	private static final long serialVersionUID = -8867561119092096196L;

	/** 操作员ID */
	private String operationId;

	/** 操作员姓名 */
	private String operationName;

	/** 操作员IP */
	private String operationIp;

	/** 操作类型 */
	private String operationType;


	public String getOperationId () {
		return operationId;
	}

	public void setOperationId (String operationId) {
		this.operationId = operationId;
	}

	public String getOperationName () {
		return operationName;
	}

	public void setOperationName (String operationName) {
		this.operationName = operationName;
	}

	public String getOperationIp () {
		return operationIp;
	}

	public void setOperationIp (String operationIp) {
		this.operationIp = operationIp;
	}

	public String getOperationType () {
		return operationType;
	}

	public void setOperationType (String operationType) {
		this.operationType = operationType;
	}
}

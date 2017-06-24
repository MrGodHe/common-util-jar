package cn.pay.ebank.common.web;

import cn.pay.ebank.common.exception.CommonException;

/**
 * 重复提交exception
 * 
 *
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author
 * 
 *
 * <li>Content: create</li>
 * 
 */
public class DuplicateSubmitException extends CommonException {
	
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = 1L;
	
	private String requestUri;
	
	private String referer;
	
	public DuplicateSubmitException() {
		super();
	}
	
	public DuplicateSubmitException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public DuplicateSubmitException(String message) {
		super(message);
	}
	
	public DuplicateSubmitException(Throwable cause) {
		super(cause);
	}
	
	/**
	 * 获得请求uri
	 * @return
	 */
	public String getRequestUri() {
		return requestUri;
	}
	
	public void setRequestUri(String requestUri) {
		this.requestUri = requestUri;
	}
	
	/**
	 * 获得来源网页
	 * @return
	 */
	public String getReferer() {
		return referer;
	}
	
	public void setReferer(String referer) {
		this.referer = referer;
	}
	
	@Override
	public String getMessage() {
		StringBuilder builder = new StringBuilder();
		builder.append("DuplicateSubmitException [requestUri=").append(requestUri).append(", referer=").append(referer).append(", message=")
			.append(super.getMessage()).append("]");
		return builder.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DuplicateSubmitException [requestUri=").append(requestUri).append(", referer=").append(referer).append("]");
		return builder.toString();
	}
	
}

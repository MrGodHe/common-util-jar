/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-04-23 18:25 创建
 *
 */
package cn.pay.ebank.common.utils.asserttools;

/**
 * @author zhyang@ebank.pay.cn
 */
public class ObjectAssertTool extends AssertToolBase<ObjectAssertTool, Object> {
	private ObjectAssertTool (Object object, String objectName) {
		super (object, objectName);
	}

	/**
	 * 断言对象
	 * <p/>
	 * assertThat(name,"Name");
	 *
	 * @param object  需要断言的对象
	 * @param name 对象名称
	 *
	 * @return
	 */
	public static ObjectAssertTool assertThat (Object object, String name) {
		return new ObjectAssertTool (object, name);
	}


	/**
	 * 断言对象
	 * <p/>
	 * assertThat(str);
	 *
	 * @param str 需要断言的对象
	 *
	 * @return
	 */
	public static ObjectAssertTool assertThat (String str) {
		return assertThat (str, "");
	}

}

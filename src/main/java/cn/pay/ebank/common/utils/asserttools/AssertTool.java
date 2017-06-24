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

import java.util.Collection;
import java.util.Map;

/**
 * @author zhyang@ebank.pay.cn
 */
public class AssertTool extends AssertToolBase<AssertTool, Object> {
	private AssertTool (Object object, String objectName) {
		super (object, objectName);
	}

	/**
	 * 断言对象
	 * <p/>
	 * assertThat(name,"Name");
	 *
	 * @param object 需要断言的对象
	 * @param name   对象描述
	 *
	 * @return
	 */
	public static ObjectAssertTool assertThat (Object object, String name) {
		return ObjectAssertTool.assertThat (object, name);
	}


	/**
	 * 断言对象
	 * <p/>
	 * assertThat(str);
	 *
	 * @param obj 需要断言的对象
	 *
	 * @return
	 */
	public static ObjectAssertTool assertThat (Object obj) {
		return ObjectAssertTool.assertThat (obj, "");
	}

	/**
	 * 断言字符串
	 * <p/>
	 * assertThat(name,"Name");
	 *
	 * @param str  需要断言的字符串
	 * @param name 字符串描述
	 *
	 * @return
	 */
	public static StringAssertTool assertThat (String str, String name) {
		return StringAssertTool.assertThat (str, name);
	}


	/**
	 * 断言对象
	 * <p/>
	 * assertThat(str);
	 *
	 * @param str 需要断言的字符串
	 *
	 * @return
	 */
	public static StringAssertTool assertThat (String str) {
		return StringAssertTool.assertThat (str, "");
	}

	/**
	 * 断言数字
	 * <p/>
	 * assertThat(name,"Name");
	 *
	 * @param num  需要断言的数字
	 * @param name 数字描述
	 *
	 * @return
	 */
	public static NumberAssertTool assertThat (Number num, String name) {
		return NumberAssertTool.assertThat (num, name);
	}


	/**
	 * 断言数字
	 * <p/>
	 * assertThat(str);
	 *
	 * @param num 需要断言的数字
	 *
	 * @return
	 */
	public static NumberAssertTool assertThat (Number num) {
		return NumberAssertTool.assertThat (num, "");
	}


	/**
	 * 断言数组
	 * <p/>
	 * assertThat(name,"Name");
	 *
	 * @param arr  需要断言的数组
	 * @param name 数组描述
	 *
	 * @return
	 */
	public static ArrayAssertTool assertThat (Object[] arr, String name) {
		return ArrayAssertTool.assertThat (arr, name);
	}


	/**
	 * 断言数组
	 * <p/>
	 * assertThat(str);
	 *
	 * @param arr 需要断言的数组
	 *
	 * @return
	 */
	public static ArrayAssertTool assertThat (Object[] arr) {
		return ArrayAssertTool.assertThat (arr, "");
	}

	/**
	 * 断言集合
	 * <p/>
	 * assertThat(name,"Name");
	 *
	 * @param col  需要断言的集合
	 * @param name 数组描述
	 *
	 * @return
	 */
	public static CollectionAssertTool assertThat (Collection<?> col, String name) {
		return CollectionAssertTool.assertThat (col, name);
	}


	/**
	 * 断言集合
	 * <p/>
	 * assertThat(str);
	 *
	 * @param col 需要断言的集合
	 *
	 * @return
	 */
	public static CollectionAssertTool assertThat (Collection<?> col) {
		return CollectionAssertTool.assertThat (col, "");
	}

	/**
	 * 断言Map
	 * <p/>
	 * assertThat(name,"Name");
	 *
	 * @param map  需要断言的Map
	 * @param name 数组描述
	 *
	 * @return
	 */
	public static MapAssertTool assertThat (Map<?, ?> map, String name) {
		return MapAssertTool.assertThat (map, name);
	}


	/**
	 * 断言类p
	 * <p/>
	 * assertThat(str);
	 *
	 * @param clazz 需要断言的类
	 *
	 * @return
	 */
	public static ClassAssertTool assertThat (Class<?> clazz) {
		return ClassAssertTool.assertThat (clazz, "");
	}

	/**
	 * 断言类
	 * <p/>
	 * assertThat(name,"Name");
	 *
	 * @param clazz 需要断言的类
	 * @param name  类描述
	 *
	 * @return
	 */
	public static ClassAssertTool assertThat (Class<?> clazz, String name) {
		return ClassAssertTool.assertThat (clazz, name);
	}


	/**
	 * 断言Map
	 * <p/>
	 * assertThat(str);
	 *
	 * @param map 需要断言的Map
	 *
	 * @return
	 */
	public static MapAssertTool assertThat (Map<?, ?> map) {
		return MapAssertTool.assertThat (map, "");
	}


}

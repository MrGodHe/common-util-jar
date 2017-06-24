package cn.pay.ebank.common.utils.asserttools;


import cn.pay.ebank.common.utils.ArrayUtils;
import cn.pay.ebank.common.utils.StringUtils;

/**
 * Created by zhyang on 2015/2/16 0016.
 */
public class StringAssertTool extends AssertToolBase<StringAssertTool, String> {

	private StringAssertTool (String object, String name) {
		super (object, name);
	}

	/**
	 * 断言对象
	 * <p/>
	 * assertThat(name,"Name");
	 *
	 * @param str  需要断言的字符串
	 * @param name 对象名称
	 *
	 * @return
	 */
	public static StringAssertTool assertThat (String str, String name) {
		return new StringAssertTool (str, name);
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
	public static StringAssertTool assertThat (String str) {
		return assertThat (str, "");
	}

	public StringAssertTool hasLength () {
		if (!StringUtils.hasLength (object)) {
			throw new IllegalArgumentException (objectName + " 应不为空且应有值!");
		}
		return this;
	}

	public StringAssertTool hasText () {
		if (!StringUtils.hasText (object)) {
			throw new IllegalArgumentException (objectName + " 不为空且一定有值!");
		}
		return this;
	}


	/**
	 * Assert that the given text does not contain the given substring.
	 */
	public StringAssertTool doesNotContain (String substring) {
		if (StringUtils.hasLength (object) && StringUtils.hasLength (substring) &&
				object.contains (substring)) {
			throw new IllegalArgumentException (objectName + " 应包含[" + substring + "]");
		}
		return this;

	}


	/**
	 * 比较两字符串是否相等，忽略大小写
	 *
	 * @param others
	 *
	 * @return
	 */
	public StringAssertTool isEqualToIgnoringCase (String others) {
		if (!StringUtils.equalsIgnoreCase (object, others)) {
			throw new IllegalArgumentException (objectName + " 应该等于(忽略大小写)[" + others + "]");
		}
		return this;
	}

	/**
	 * 判断给定对象知否开始以给定字符串
	 * <p/>
	 * startStr 为空时，不做断言
	 *
	 * @param startStr 开始字符串
	 *
	 * @return
	 */
	public StringAssertTool startsWith (String startStr) {
		if (StringUtils.isEmpty (startStr)) {
			return this;
		}

		if (object == null) {
			throw new IllegalArgumentException (objectName + " 不能为Null");
		} else {
			if (!object.startsWith (startStr)) {
				throw new IllegalArgumentException (objectName + " 应该开始以[" + startStr + "]开始");
			}
		}
		return this;
	}

	/**
	 * 判断给定对象知否结束以给定字符串
	 * <p/>
	 * 只对String类型进行判断
	 * <br/>
	 * endStr 为空时，不做断言
	 *
	 * @param endStr
	 *
	 * @return
	 */
	public StringAssertTool endsWith (String endStr) {
		if (StringUtils.isEmpty (endStr)) {
			return this;
		}
		if (object == null) {
			throw new IllegalArgumentException (objectName + " 不能为Null");
		} else {
			if (!object.endsWith (endStr)) {
				throw new IllegalArgumentException (objectName + " 应该开始以[" + endStr + "]结束");
			}
		}
		return this;
	}


	/**
	 * 断言字符窜的长度
	 * <p/>
	 *
	 * @param length
	 *
	 * @return
	 */
	public StringAssertTool hasLength (int length) {
		return hasSize (length);
	}

	/**
	 * 断言字符窜的长度
	 * <p/>
	 *
	 * @param size
	 *
	 * @return
	 */
	public StringAssertTool hasSize (int size) {
		if (StringUtils.length (object) != size) {
			throw new IllegalArgumentException (objectName + " 的长度应该为[" + size + "]");
		}
		return this;
	}

	/**
	 * 断言字符串为空
	 *
	 * @return
	 */
	public StringAssertTool isNullOrEmpty () {
		if (!StringUtils.isEmpty (object)) {
			throw new IllegalArgumentException (objectName + " 的长度应该为空字符串");
		}
		return this;
	}
	public StringAssertTool isEmpty () {
		if (!StringUtils.isEmpty (object)) {
			throw new IllegalArgumentException (objectName + " 的长度应该为空字符串");
		}
		return this;
	}

	/**
	 * 断言字符串不为空
	 *
	 * @return
	 */
	public StringAssertTool isNotEmpty () {
		if (StringUtils.isEmpty (object)) {
			throw new IllegalArgumentException (objectName + " 的长度不应该为空字符串");
		}
		return this;
	}


	/**
	 * 判断包含给定字符串
	 * <p/>
	 * subStr 为空时，不做断言
	 *
	 * @param subStr 开始字符串
	 *
	 * @return
	 */
	public StringAssertTool contains (String subStr) {
		if (StringUtils.isEmpty (subStr)) {
			return this;
		}

		if (object == null) {
			throw new IllegalArgumentException (objectName + " 不能为Null");
		} else {
			if (!object.contains (subStr)) {
				throw new IllegalArgumentException (objectName + " 应该包含[" + subStr + "]");
			}
		}
		return this;
	}

	/**
	 * 判断匹配给定格式
	 * <p/>
	 * matchesStr 为空时，不做断言
	 *
	 * @param matchesStr 断言格式
	 *
	 * @return
	 */
	public StringAssertTool matches (String matchesStr) {
		if (StringUtils.isEmpty (matchesStr)) {
			return this;
		}
		if (object == null) {
			throw new IllegalArgumentException (objectName + " 不能为Null");
		} else {
			if (!object.matches (matchesStr)) {
				throw new IllegalArgumentException (objectName + " 应该包含[" + matchesStr + "]");
			}
		}
		return this;
	}

	/**
	 * 断言不匹配给定格式
	 * <p/>
	 * matchesStr 为空时，不做断言
	 *
	 * @param matchesStr 断言格式
	 *
	 * @return
	 */
	public StringAssertTool doesNotMatch (String matchesStr) {
		if (StringUtils.isNotEmpty (matchesStr) && object != null && object.matches (matchesStr)) {
			throw new IllegalArgumentException (objectName + " 不应该包含[" + matchesStr + "]");
		}
		return this;
	}

	public StringAssertTool inList (String... strList) {
		if (ArrayUtils.isEmpty (strList)) {
			throw new IllegalArgumentException ("集合不能为空!");
		}
		if (!StringUtils.inArray (object, strList)) {
			throw new IllegalArgumentException (objectName + " 应该包含在" + ArrayUtils.toString (strList) + "中");
		}
		return this;
	}

	public StringAssertTool inListIgnoreCase (String... strList) {
		if (ArrayUtils.isEmpty (strList)) {
			throw new IllegalArgumentException ("集合不能为空!");
		}
		if (!StringUtils.inArrayIgnoreCase (object, strList)) {
			throw new IllegalArgumentException (objectName + " 应该包含在" + ArrayUtils.toString (strList) + "中");
		}
		return this;
	}

	public StringAssertTool maxLength (int length) {
		if (StringUtils.length (object) > length) {
			throw new IllegalArgumentException (objectName + " 的长度应该小于等于[" + length + "]");
		}
		return this;
	}

	public StringAssertTool minLength (int length) {
		if (StringUtils.length (object) < length) {
			throw new IllegalArgumentException (objectName + " 的长度应该大于等于[" + length + "]");
		}
		return this;
	}

	public StringAssertTool lengthBetween (int minLength, int maxLength) {
		int length = StringUtils.length (object);
		if (length < minLength || length > maxLength) {
			throw new IllegalArgumentException (objectName + " 的长度应该介于[" + minLength + "," + maxLength + "]之间");
		}
		return this;
	}
}

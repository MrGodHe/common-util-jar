package cn.pay.ebank.common.utils.asserttools;

/**
 * Created by zhyang on 2015/2/16 0016.
 */
public class NumberAssertTool extends AssertToolBase<NumberAssertTool, Number> {

	private NumberAssertTool (Number object, String name) {
		super (object, name);
	}

	/**
	 * 断言对象
	 * <p/>
	 * assertThat(name,"Name");
	 *
	 * @param number 需要断言的字符串
	 * @param name   对象名称
	 * @return
	 */
	public static NumberAssertTool assertThat (Number number, String name) {
		return new NumberAssertTool (number, name);
	}


	/**
	 * 断言对象
	 * <p/>
	 * assertThat(str);
	 *
	 * @param number 需要断言的对象
	 * @return
	 */
	public static NumberAssertTool assertThat (Number number) {
		return assertThat (number, "");
	}


	public NumberAssertTool isGreaterThan (Number number) {
		if (object != null && number != null &&
				object.doubleValue () <= number.doubleValue ()) {
			throw new IllegalArgumentException (objectName + " 应大于[" + number + "]");
		}
		return this;
	}

	/**
	 * 断言 大于等于
	 *
	 * @param number
	 * @return
	 */
	public NumberAssertTool isGreaterThanOrEqualTo (Number number) {
		if (object != null && number != null &&
				object.doubleValue () < number.doubleValue ()) {
			throw new IllegalArgumentException (objectName + " 应大于或等于[" + number + "]");
		}
		return this;
	}

	public NumberAssertTool isLessThan (Number number) {
		if (object != null && number != null &&
				object.doubleValue () >= number.doubleValue ()) {
			throw new IllegalArgumentException (objectName + " 应小于[" + number + "]");
		}
		return this;
	}

	public NumberAssertTool isLessThanOrEqualTo (Number number) {
		if (object != null && number != null &&
				object.doubleValue () > number.doubleValue ()) {
			throw new IllegalArgumentException (objectName + " 应小于或等于[" + number + "]");
		}
		return this;
	}

	/**
	 * 断言0
	 *
	 * @return
	 */
	public NumberAssertTool isZero () {
		if (object != null && object.doubleValue () != 0) {
			throw new IllegalArgumentException (objectName + " 应为[0]");
		}
		return this;
	}

	/**
	 * 断言正数
	 *
	 * @return
	 */
	public NumberAssertTool isPositive () {
		if (object != null && object.doubleValue () <= 0) {
			throw new IllegalArgumentException (objectName + " 应为正数");
		}
		return this;
	}

	/**
	 * 断言非正数
	 *
	 * @return
	 */
	public NumberAssertTool isNotPositive () {
		if (object != null && object.doubleValue () > 0) {
			throw new IllegalArgumentException (objectName + " 应为非正数");
		}
		return this;
	}

	/**
	 * 断言负数
	 *
	 * @return
	 */
	public NumberAssertTool isNegative () {
		if (object != null && object.doubleValue () >= 0) {
			throw new IllegalArgumentException (objectName + " 应为正数");
		}
		return this;
	}

	/**
	 * 断言非负数
	 *
	 * @return
	 */
	public NumberAssertTool isNotNegative () {
		if (object != null && object.doubleValue () < 0) {
			throw new IllegalArgumentException (objectName + " 应为非负数");
		}
		return this;
	}

}

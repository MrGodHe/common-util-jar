package cn.pay.ebank.common.utils.asserttools;


import cn.pay.ebank.common.utils.ArrayUtils;

/**
 * Created by zhyang on 2015/2/16 0016.
 */
public class ArrayAssertTool extends AssertToolBase<ArrayAssertTool, Object[]> {

	private ArrayAssertTool (Object[] arrays, String name) {
		super (arrays, name);
	}

	/**
	 * 断言对象
	 * <p/>
	 * assertThat(name,"Name");
	 *
	 * @param arrays 需要断言的字符串
	 * @param name   对象名称
	 * @return
	 */
	public static ArrayAssertTool assertThat (Object[] arrays, String name) {
		return new ArrayAssertTool (arrays, name);
	}


	/**
	 * 断言对象
	 * <p/>
	 * assertThat(str);
	 *
	 * @param arrays 需要断言的对象
	 * @return
	 */
	public static ArrayAssertTool assertThat (Object[] arrays) {
		return assertThat (arrays, "");
	}

	public ArrayAssertTool isEmpty () {
		if (ArrayUtils.isNotEmpty (object)) {
			throw new IllegalArgumentException (objectName + " 应为空!");
		}
		return this;
	}

	public ArrayAssertTool notEmpty () {
		if (ArrayUtils.isEmpty (object)) {
			throw new IllegalArgumentException (objectName + " 不应为空!");
		}
		return this;
	}

	public ArrayAssertTool contains (Object item) {
		if (ArrayUtils.isEmpty (object)) {
			throw new IllegalArgumentException (objectName + " 不应为空!");
		} else {
			for (Object obj : object) {
				if (obj != null && obj.equals (item)) {
					return this;
				}
			}
		}
		throw new IllegalArgumentException (objectName + " 应包含[" + item + "]!");
	}

	public ArrayAssertTool hasSize (int size) {
		if (ArrayUtils.isEmpty (object)) {
			throw new IllegalArgumentException (objectName + " 不应为空!");
		} else {
			if (object.length != size) {
				throw new IllegalArgumentException (objectName + " 长度应为[" + size + "]!");
			}
		}
		return this;
	}

}

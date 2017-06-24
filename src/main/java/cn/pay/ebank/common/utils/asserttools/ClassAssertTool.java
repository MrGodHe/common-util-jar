package cn.pay.ebank.common.utils.asserttools;


import java.lang.annotation.Annotation;

/**
 * Created by zhyang on 2015/2/16 0016.
 */
public class ClassAssertTool extends AssertToolBase<ClassAssertTool, Class<?>> {

	private ClassAssertTool (Class<?> clazz, String name) {
		super (clazz, name);
	}

	/**
	 * 断言对象
	 * <p/>
	 * assertThat(name,"Name");
	 *
	 * @param clazz 需要断言的字符串
	 * @param name  对象名称
	 *
	 * @return
	 */
	public static ClassAssertTool assertThat (Class<?> clazz, String name) {
		return new ClassAssertTool (clazz, name);
	}


	/**
	 * 断言对象
	 * <p/>
	 * assertThat(str);
	 *
	 * @param clazz 需要断言的对象
	 *
	 * @return
	 */
	public static ClassAssertTool assertThat (Class<?> clazz) {
		return assertThat (clazz, clazz.getName ());
	}

	/**
	 * 断言类为注解类
	 *
	 * @return
	 */
	public ClassAssertTool isAnnotation () {
		if (!object.isAnnotation ()) {
			throw new IllegalArgumentException (objectName + " 应该是注解类!");
		}
		return this;
	}

	/**
	 * 断言类不是注解类
	 *
	 * @return
	 */
	public ClassAssertTool isNotAnnotation () {
		if (object.isAnnotation ()) {
			throw new IllegalArgumentException (objectName + " 不应该是注解类!");
		}
		return this;
	}

	/**
	 * 断言类包含注解类
	 *
	 * @return
	 */
	public <A extends Annotation> ClassAssertTool hasAnnotation (Class<A> clazz) {
		if (object.getAnnotation (clazz) == null) {
			throw new IllegalArgumentException (objectName + " 不应该是包含注解类[" + clazz.getName () + "]!");
		}
		return this;
	}

	/**
	 * 断言类不是接口
	 *
	 * @return
	 */
	public ClassAssertTool isNotInterface () {
		if (object.isInterface ()) {
			throw new IllegalArgumentException (objectName + " 不应该是接口类!");
		}
		return this;
	}

	public ClassAssertTool isAssignableFrom (Class<?> clazz) {
		if (!object.isAssignableFrom (clazz)) {
			throw new IllegalArgumentException (objectName + " 应该是的" + clazz.getName () + "父类!");
		}
		return this;
	}

}

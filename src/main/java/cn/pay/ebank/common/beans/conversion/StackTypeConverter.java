package cn.pay.ebank.common.beans.conversion;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import cn.pay.ebank.common.collections.LinkedStack;
import cn.pay.ebank.common.collections.Stack;
import cn.pay.ebank.common.utils.CollectionUtils;
import cn.pay.ebank.common.utils.InvocationTargetRunTimeException;
import cn.pay.ebank.common.utils.ReflectionUtils;


/**
 * {@link Stack}的类型转换器。
 *
 * @author Agreal·Lee (e-mail:lixiang@ebank.pay.cn)
 */
public class StackTypeConverter extends AbstractTypeConverter<Stack<?>> {
	
	@SuppressWarnings("unchecked")
	public Class<Stack<?>> getTargetType () {
		Class<?> stackClass = Stack.class;
		return (Class<Stack<?>>) stackClass;
	}
	
	public List<Class<?>> getSupportedSourceTypes () {
		return Arrays.asList (Collection.class, Object[].class);
	}
	
	public Stack<?> convert (Object value, Class<? extends Stack<?>> toType) {
		return stackValue (value, toType);
	}
	
	@SuppressWarnings("unchecked")
	public static <E extends Stack<?>> E stackValue (Object value, Class<? extends E> stackClassType) {
		Stack<Object> stack = null;
		if ((Class<?>) stackClassType == Stack.class) {
			// 使用 LinkedStack 作为实现
			stack = new LinkedStack<Object> ();
		} else { // 如果是具体的类则使用该类的类型
			try {
				stack = (Stack<Object>) ReflectionUtils.createObject (stackClassType);
			} catch (InvocationTargetRunTimeException e) {
				throw new TypeConversionException (e.getTargetException ());
			} catch (Exception e) {
				throw new TypeConversionException (e);
			}
		}
		CollectionUtils.push (stack, value);
		return (E) stack;
	}
}

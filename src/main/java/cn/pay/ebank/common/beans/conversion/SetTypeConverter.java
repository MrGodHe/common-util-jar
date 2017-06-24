package cn.pay.ebank.common.beans.conversion;

import java.util.*;

import cn.pay.ebank.common.utils.CollectionUtils;
import cn.pay.ebank.common.utils.InvocationTargetRunTimeException;
import cn.pay.ebank.common.utils.ReflectionUtils;


/**
 * {@link java.util.Set}的类型转换器。
 *
 * @author Agreal·Lee (e-mail:lixiang@ebank.pay.cn)
 */
public class SetTypeConverter extends AbstractTypeConverter<Set<?>> {
	
	@SuppressWarnings("unchecked")
	public Class<Set<?>> getTargetType () {
		Class<?> setClass = Set.class;
		return (Class<Set<?>>) setClass;
	}
	
	public List<Class<?>> getSupportedSourceTypes () {
		return Arrays.asList (Collection.class, Object[].class);
	}
	
	public Set<?> convert (Object value, Class<? extends Set<?>> toType) {
		return setValue (value, toType);
	}
	
	@SuppressWarnings("unchecked")
	public static <E extends Set<?>> E setValue (Object value, Class<? extends E> setClassType) {
		Set<Object> set = null;
		if ((Class<?>) setClassType == Set.class) {
			// 使用 HashSet 作为实现
			set = new HashSet<Object> ();
		} else { // 如果是具体的类则使用该类的类型
			try {
				set = (Set<Object>) ReflectionUtils.createObject (setClassType);
			} catch (InvocationTargetRunTimeException e) {
				throw new TypeConversionException (e.getTargetException ());
			} catch (Exception e) {
				throw new TypeConversionException (e);
			}
		}
		CollectionUtils.add (set, value);
		return (E) set;
	}
}

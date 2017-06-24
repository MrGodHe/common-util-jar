package cn.pay.ebank.common.beans.conversion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import cn.pay.ebank.common.utils.CollectionUtils;
import cn.pay.ebank.common.utils.InvocationTargetRunTimeException;
import cn.pay.ebank.common.utils.ReflectionUtils;


/**
 * {@link java.util.List}的类型转换器。
 * 
 * @author Agreal·Lee (e-mail:lixiang@ebank.pay.cn)
 * 
 */
public class ListTypeConverter extends AbstractTypeConverter<List<?>> {
	
	@SuppressWarnings("unchecked")
	public Class<List<?>> getTargetType() {
		Class<?> listClass = List.class;
		return (Class<List<?>>) listClass;
	}
	
	public List<Class<?>> getSupportedSourceTypes() {
		return Arrays.asList(Collection.class, Object[].class);
	}
	
	public List<?> convert(Object value, Class<? extends List<?>> toType) {
		return listValue(value, toType);
	}
	
	@SuppressWarnings("unchecked")
	public static <E extends List<?>> E listValue(Object value, Class<? extends E> listClassType) {
		List<Object> list = null;
		if ((Class<?>) listClassType == List.class) {
			// 使用 ArrayList 作为实现
			list = new ArrayList<Object>();
		} else { // 如果是具体的类则使用该类的类型
			try {
				list = (List<Object>) ReflectionUtils.createObject (listClassType);
			} catch (InvocationTargetRunTimeException e) {
				throw new TypeConversionException(e.getTargetException());
			} catch (Exception e) {
				throw new TypeConversionException(e);
			}
		}
		CollectionUtils.add (list, value);
		return (E) list;
	}
}

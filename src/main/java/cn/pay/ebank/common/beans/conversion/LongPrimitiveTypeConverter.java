package cn.pay.ebank.common.beans.conversion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.pay.ebank.common.utils.CollectionUtils;
import cn.pay.ebank.common.utils.PrimitiveUtils;

/**
 * long 的类型转换器。
 * 
 * @author Agreal·Lee (e-mail:lixiang@ebank.pay.cn)
 * 
 */
public class LongPrimitiveTypeConverter extends AbstractTypeConverter<Long> {
	
	public Class<Long> getTargetType() {
		return Long.TYPE;
	}
	
	public List<Class<?>> getSupportedSourceTypes() {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		CollectionUtils.add(classes, PrimitiveUtils.getAllPrimitiveClasses());
		CollectionUtils.add(classes, PrimitiveUtils.getAllWrapperClasses());
		classes.add(Object[].class);
		classes.add(Collection.class);
		classes.add(CharSequence.class);
		classes.add(CharSequence[].class);
		return classes;
	}
	
	public Long convert(Object value, Class<? extends Long> toType) {
		try {
			return longValue(value);
		} catch (Exception e) {
			throw new TypeConversionException(e);
		}
	}
	
	public static long longValue(Object value) throws NumberFormatException {
		if (value == null)
			return 0L;
		Class<?> c = value.getClass();
		if (c.getSuperclass() == Number.class) {
			return ((Number) value).longValue();
		}
		if (c == Boolean.class) {
			return ((Boolean) value).booleanValue() ? 1 : 0;
		}
		if (c == Character.class) {
			return ((Character) value).charValue();
		}
		return Long.parseLong(StringTypeConverter.stringValue(value, true));
	}
}

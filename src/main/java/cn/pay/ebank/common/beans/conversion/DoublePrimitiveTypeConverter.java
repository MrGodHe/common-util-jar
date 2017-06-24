package cn.pay.ebank.common.beans.conversion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.pay.ebank.common.utils.CollectionUtils;
import cn.pay.ebank.common.utils.PrimitiveUtils;

/**
 * double 的类型转换器。
 * 
 * @author Agreal·Lee (e-mail:lixiang@ebank.pay.cn)
 * 
 */
public class DoublePrimitiveTypeConverter extends AbstractTypeConverter<Double> {
	
	public Class<Double> getTargetType() {
		return Double.TYPE;
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
	
	public Double convert(Object value, Class<? extends Double> toType) {
		try {
			return doubleValue(value);
		} catch (Exception e) {
			throw new TypeConversionException(e);
		}
	}
	
	public static double doubleValue(Object value) throws NumberFormatException {
		if (value == null)
			return 0.0;
		Class<?> c = value.getClass();
		if (c.getSuperclass() == Number.class) {
			return ((Number) value).doubleValue();
		}
		if (c == Boolean.class) {
			return ((Boolean) value).booleanValue() ? 1 : 0;
		}
		if (c == Character.class) {
			return ((Character) value).charValue();
		}
		String s = StringTypeConverter.stringValue(value, true);
		return (s.length() == 0) ? 0.0 : Double.parseDouble(s);
	}
}

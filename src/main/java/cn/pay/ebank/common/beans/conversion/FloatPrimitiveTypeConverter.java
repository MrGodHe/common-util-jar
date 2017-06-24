package cn.pay.ebank.common.beans.conversion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.pay.ebank.common.utils.CollectionUtils;
import cn.pay.ebank.common.utils.PrimitiveUtils;

/**
 * float 的类型转换器。
 * 
 * @author Agreal·Lee (e-mail:lixiang@ebank.pay.cn)
 * 
 */
public class FloatPrimitiveTypeConverter extends AbstractTypeConverter<Float> {
	
	public Class<Float> getTargetType() {
		return Float.TYPE;
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
	
	public Float convert(Object value, Class<? extends Float> toType) {
		try {
			return floatcharValue(value);
		} catch (NumberFormatException e) {
			throw new TypeConversionException(e);
		}
	}
	
	public static float floatcharValue(Object value) throws NumberFormatException {
		return (float) DoublePrimitiveTypeConverter.doubleValue(value);
	}
}

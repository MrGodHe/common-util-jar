package cn.pay.ebank.common.beans.conversion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.pay.ebank.common.utils.CollectionUtils;
import cn.pay.ebank.common.utils.PrimitiveUtils;

/**
 * {@link Double}的类型转换器。
 * 
 * @author Agreal·Lee (e-mail:lixiang@ebank.pay.cn)
 * 
 */
public class DoubleTypeConverter extends AbstractTypeConverter<Double> {
	
	public Class<Double> getTargetType() {
		return Double.class;
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
			if (value == null) {
				return null;
			}
			return Double.valueOf(DoublePrimitiveTypeConverter.doubleValue(value));
		} catch (Exception e) {
			throw new TypeConversionException(e);
		}
	}
}

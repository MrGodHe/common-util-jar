package cn.pay.ebank.common.beans.conversion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.pay.ebank.common.utils.CollectionUtils;
import cn.pay.ebank.common.utils.PrimitiveUtils;

/**
 * {@link Long}的类型转换器。
 * 
 * @author Agreal·Lee (e-mail:lixiang@ebank.pay.cn)
 * 
 */
public class LongTypeConverter extends AbstractTypeConverter<Long> {
	
	public Class<Long> getTargetType() {
		return Long.class;
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
			if (value == null) {
				return null;
			}
			return Long.valueOf(LongPrimitiveTypeConverter.longValue(value));
		} catch (Exception e) {
			throw new TypeConversionException(e);
		}
	}
}

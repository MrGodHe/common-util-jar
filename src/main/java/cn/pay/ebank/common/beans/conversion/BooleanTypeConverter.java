package cn.pay.ebank.common.beans.conversion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.pay.ebank.common.utils.CollectionUtils;
import cn.pay.ebank.common.utils.PrimitiveUtils;


/**
 * {@link Boolean}的类型转换器。
 * 
 * @author Agreal·Lee (e-mail:lixiang@ebank.pay.cn)
 * 
 */
public class BooleanTypeConverter extends AbstractTypeConverter<Boolean> {
	
	public Class<Boolean> getTargetType() {
		return Boolean.class;
	}
	
	public List<Class<?>> getSupportedSourceTypes() {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		CollectionUtils.add (classes, PrimitiveUtils.getAllPrimitiveClasses ());
		CollectionUtils.add(classes, PrimitiveUtils.getAllWrapperClasses());
		classes.add(Object[].class);
		classes.add(Collection.class);
		classes.add(CharSequence.class);
		classes.add(CharSequence[].class);
		return classes;
	}
	
	public Boolean convert(Object value, Class<? extends Boolean> toType) {
		if (value == null) {
			return null;
		}
		return BooleanPrimitiveTypeConverter.booleanValue(value) ? Boolean.TRUE : Boolean.FALSE;
	}
}

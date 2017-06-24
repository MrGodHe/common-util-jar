package cn.pay.ebank.common.beans.conversion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.pay.ebank.common.utils.CollectionUtils;
import cn.pay.ebank.common.utils.PrimitiveUtils;


/**
 * boolean 的类型转换器。
 * 
 * @author Agreal·Lee (e-mail:lixiang@ebank.pay.cn)
 * 
 */
public class BooleanPrimitiveTypeConverter extends AbstractTypeConverter<Boolean> {
	
	public Class<Boolean> getTargetType() {
		return Boolean.TYPE;
	}
	
	public List<Class<?>> getSupportedSourceTypes() {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		CollectionUtils.add (classes, PrimitiveUtils.getAllPrimitiveClasses ());
		CollectionUtils.add(classes, PrimitiveUtils.getAllWrapperClasses ());
		classes.add(Object[].class);
		classes.add(Collection.class);
		classes.add(CharSequence.class);
		classes.add(CharSequence[].class);
		return classes;
	}
	
	public Boolean convert(Object value, Class<? extends Boolean> toType) {
		return booleanValue(value);
	}
	
	public static boolean booleanValue(Object value) {
		if (value == null)
			return false;
		Class<?> c = value.getClass();
		if (c == Boolean.class) {
			return ((Boolean) value).booleanValue();
		}
		if (c == Character.class) {
			return ((Character) value).charValue() != 0;
		}
		if (value instanceof Number) {
			return ((Number) value).doubleValue() != 0;
		}
		if (c == String.class) {
			return Boolean.parseBoolean((String) value);
		}
		return true;
	}
}

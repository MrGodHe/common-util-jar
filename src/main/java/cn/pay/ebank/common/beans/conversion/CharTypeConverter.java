package cn.pay.ebank.common.beans.conversion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.pay.ebank.common.utils.CollectionUtils;
import cn.pay.ebank.common.utils.PrimitiveUtils;

/**
 * char 的类型转换器。
 * 
 * @author Agreal·Lee (e-mail:lixiang@ebank.pay.cn)
 * 
 */
public class CharTypeConverter extends AbstractTypeConverter<Character> {
	
	public Class<Character> getTargetType() {
		return Character.TYPE;
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
	
	public Character convert(Object value, Class<? extends Character> toType) {
		try {
			return charValue(value);
		} catch (Exception e) {
			throw new TypeConversionException(e);
		}
	}
	
	public static char charValue(Object value) throws NumberFormatException {
		return (char) IntTypeConverter.intValue(value);
	}
}

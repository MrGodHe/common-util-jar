package cn.pay.ebank.common.beans.conversion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.pay.ebank.common.utils.CollectionUtils;
import cn.pay.ebank.common.utils.PrimitiveUtils;

/**
 * byte 的类型转换器。
 * 
 * @author Agreal·Lee (e-mail:lixiang@ebank.pay.cn)
 * 
 */
public class BytePrimitiveTypeConverter extends AbstractTypeConverter<Byte> {
	
	public Class<Byte> getTargetType() {
		return Byte.TYPE;
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
	
	public Byte convert(Object value, Class<? extends Byte> toType) {
		try {
			return byteValue(value);
		} catch (Exception e) {
			throw new TypeConversionException(e);
		}
	}
	
	public static byte byteValue(Object value) throws NumberFormatException {
		return (byte) IntTypeConverter.intValue(value);
	}
}

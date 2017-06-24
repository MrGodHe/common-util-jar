package cn.pay.ebank.common.beans.conversion;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 数组的类型转换器。
 * 
 * @author Agreal·Lee (e-mail:lixiang@ebank.pay.cn)
 * 
 */
public class ArrayTypeConverter extends ArrayTypeConverterSupport<Object[]> {
	
	public ArrayTypeConverter(TypeConverterManager typeConverterManager) {
		super(typeConverterManager);
	}
	
	public Class<Object[]> getTargetType() {
		return Object[].class;
	}
	
	public List<Class<?>> getSupportedSourceTypes() {
		return Arrays.asList(Object[].class, Collection.class);
	}
	
}

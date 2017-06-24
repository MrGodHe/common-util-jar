package cn.pay.ebank.common.beans.conversion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.pay.ebank.common.utils.CollectionUtils;
import cn.pay.ebank.common.utils.PrimitiveUtils;

/**
 * char数组的类型转换器。
 * 
 * @author Agreal·Lee (e-mail:lixiang@ebank.pay.cn)
 * 
 */
public class CharArrayTypeConverter extends ArrayTypeConverterSupport<char[]> {
	
	public CharArrayTypeConverter(TypeConverterManager typeConverterManager) {
		super(typeConverterManager);
	}
	
	public Class<char[]> getTargetType() {
		return char[].class;
	}
	
	public List<Class<?>> getSupportedSourceTypes() {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		CollectionUtils.add(classes, PrimitiveUtils.getAllPrimitiveArrayClasses());
		CollectionUtils.add(classes, PrimitiveUtils.getAllWrapperArrayClasses());
		classes.add(Object[].class);
		classes.add(Collection.class);
		classes.add(CharSequence[].class);
		return classes;
	}
	
}

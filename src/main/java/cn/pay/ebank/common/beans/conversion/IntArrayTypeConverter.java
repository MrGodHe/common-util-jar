package cn.pay.ebank.common.beans.conversion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.pay.ebank.common.utils.CollectionUtils;
import cn.pay.ebank.common.utils.PrimitiveUtils;

/**
 * int数组的类型转换器。
 * 
 * @author Agreal·Lee (e-mail:lixiang@ebank.pay.cn)
 * 
 */
public class IntArrayTypeConverter extends ArrayTypeConverterSupport<int[]> {
	
	public IntArrayTypeConverter(TypeConverterManager typeConverterManager) {
		super(typeConverterManager);
	}
	
	public Class<int[]> getTargetType() {
		return int[].class;
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

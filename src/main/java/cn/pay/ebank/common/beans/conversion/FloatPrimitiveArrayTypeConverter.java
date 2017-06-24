package cn.pay.ebank.common.beans.conversion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.pay.ebank.common.utils.CollectionUtils;
import cn.pay.ebank.common.utils.PrimitiveUtils;

/**
 * float数组的类型转换器。
 * 
 * @author Agreal·Lee (e-mail:lixiang@ebank.pay.cn)
 * 
 */
public class FloatPrimitiveArrayTypeConverter extends ArrayTypeConverterSupport<float[]> {
	
	public FloatPrimitiveArrayTypeConverter(TypeConverterManager typeConverterManager) {
		super(typeConverterManager);
	}
	
	public Class<float[]> getTargetType() {
		return float[].class;
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

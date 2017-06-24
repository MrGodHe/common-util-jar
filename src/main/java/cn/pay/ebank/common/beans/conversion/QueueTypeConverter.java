package cn.pay.ebank.common.beans.conversion;

import java.util.*;

import cn.pay.ebank.common.utils.CollectionUtils;
import cn.pay.ebank.common.utils.InvocationTargetRunTimeException;
import cn.pay.ebank.common.utils.ReflectionUtils;


/**
 * {@link java.util.Queue}的类型转换器。
 * 
 * @author Agreal·Lee (e-mail:lixiang@ebank.pay.cn)
 * 
 */
public class QueueTypeConverter extends AbstractTypeConverter<Queue<?>> {
	
	@SuppressWarnings("unchecked")
	public Class<Queue<?>> getTargetType() {
		Class<?> queueClass = Queue.class;
		return (Class<Queue<?>>) queueClass;
	}
	
	public List<Class<?>> getSupportedSourceTypes() {
		return Arrays.asList(Collection.class, Object[].class);
	}
	
	public Queue<?> convert(Object value, Class<? extends Queue<?>> toType) {
		return queueValue(value, toType);
	}
	
	@SuppressWarnings("unchecked")
	public static <E extends Queue<?>> E queueValue(Object value, Class<? extends E> queueClassType) {
		Queue<Object> queue = null;
		if ((Class<?>) queueClassType == Queue.class) {
			// 使用 LinkedList 作为实现
			queue = new LinkedList<Object>();
		} else { // 如果是具体的类则使用该类的类型
			try {
				queue = (Queue<Object>) ReflectionUtils.createObject (queueClassType);
			} catch (InvocationTargetRunTimeException e) {
				throw new TypeConversionException(e.getTargetException());
			} catch (Exception e) {
				throw new TypeConversionException(e);
			}
		}
		CollectionUtils.add (queue, value);
		return (E) queue;
	}
}

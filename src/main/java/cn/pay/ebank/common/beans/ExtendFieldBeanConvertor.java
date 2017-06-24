/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhouyang@yiji.com 2016-04-12 14:18 创建
 *
 */
package cn.pay.ebank.common.beans;

import cn.pay.ebank.common.beans.money.Money;
import cn.pay.ebank.common.utils.CollectionUtils;
import cn.pay.ebank.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.CodeGenerationException;
import org.springframework.cglib.core.ReflectUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 带扩展字段的属性拷贝
 * 
 * @author zhouyang@yiji.com
 */
public abstract class ExtendFieldBeanConvertor {
	protected final static Logger logger = LoggerFactory.getLogger (ExtendFieldBeanConvertor.class);
	
	private static Map<Class<?>, List<PropertyDescriptor>> extendFieldGetterCache = CollectionUtils
		.newConcurrentMap ();
	private static Map<Class<?>, List<Field>> notExtendFieldCache = CollectionUtils
		.newConcurrentMap ();
	private static Map<Class<?>, Map<String, PropertyDescriptor>> extendFieldSetterCache = CollectionUtils
		.newConcurrentMap ();
	
	/**
	 * 将对象中的扩展字段序列化为json字符串
	 * 
	 * @param obj IExtendFieldObject的子类
	 * @return
	 */
	public static <O extends IExtendFieldObject> String getExtendFields(O obj) {
		
		Map<String, Object> extendFields = CollectionUtils.newHashMap();
		//获取domain中的扩展字段集合
		if(CollectionUtils.isNotEmpty (obj.getExtendFields())) {
			extendFields.putAll (obj.getExtendFields ());
		}
		
		Class<?> clazz = obj.getClass();
		//得到非扩展字段
		List<Field> notExtendFields = notExtendFieldCache.get(clazz);
		if (notExtendFields == null) {
			notExtendFields = getNotExtendField(clazz);
			notExtendFieldCache.put(clazz, notExtendFields);
		}
		//从扩展字段中排除非扩展字段
		if (!extendFields.isEmpty()) {
			for (Field field : notExtendFields) {
				extendFields.remove(field.getName());
			}
		}
		
		//取得所有标识为扩展字段的 getter
		List<PropertyDescriptor> getterMethods = extendFieldGetterCache.get(clazz);
		if (getterMethods == null) {
			getterMethods = getExtendFieldOptMethods(clazz);
			extendFieldGetterCache.put(clazz, getterMethods);
		}
		
		Map<String, Object> storeFields = CollectionUtils.newHashMap();
		
		//取得标识为扩展字段的值
		for (PropertyDescriptor getter : getterMethods) {
			try {
				Method getterMethod = getter.getReadMethod();
				Object value = getterMethod.invoke(obj);
				if (value == null || StringUtils.isEmpty (value.toString ())) {
					continue;
				} else if (value instanceof String) {
					storeFields.put(getter.getName(), value);
				} else if (value instanceof Enum) {
					Method enumGetCodeMethod = value.getClass().getMethod("getCode");
					if (enumGetCodeMethod != null) {
						value = enumGetCodeMethod.invoke(value);
						storeFields.put(getter.getName(), value);
					}
				} else {
					storeFields.put(getter.getName(), value.toString());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//去除空字段
		removeNullValue(storeFields);
		if (storeFields.isEmpty()) {
			return null;
		}
		//将扩展字段序列化为 json 字符串
		return JSON.toJSONString(storeFields);
	}
	
	/**
	 * 将json字符串反序列化为对象值
	 * 
	 * @param obj
	 * @param extendFieldsJson Map对象的Json字符串
	 * @return
	 */
	public static <O extends IExtendFieldObject> O setExtendFields(O obj, String extendFieldsJson) {
		if (StringUtils.isEmpty(extendFieldsJson)) {
			return obj;
		}
		
		@SuppressWarnings("unchecked")
		Map<String, Object> extendFieldsJsonObject = JSON.parseObject(extendFieldsJson, Map.class);
		
		return setExtendFields(obj, extendFieldsJsonObject);
	}
	
	/**
	 * 将Map对象中的值设置到对象中
	 * @param obj
	 * @param extendFieldsMap
	 * @param <O>
	 * @return
	 */
	public static <O extends IExtendFieldObject> O setExtendFields(	O obj,
																	Map<String, Object> extendFieldsMap) {
		
		Class<?> clazz = obj.getClass();
		//获取所有扩展字段的setter
		Map<String, PropertyDescriptor> setterMethods = extendFieldSetterCache.get(clazz);
		
		if (setterMethods == null) {
			setterMethods = CollectionUtils.newConcurrentMap();
			extendFieldSetterCache.put(clazz, setterMethods);
			
			PropertyDescriptor[] methods = ReflectUtils.getBeanGetters(clazz);
			for (PropertyDescriptor setter : methods) {
				setterMethods.put(setter.getName(), setter);
			}
		}
		
		for (String key : extendFieldsMap.keySet()) {
			Object value = extendFieldsMap.get(key);
			
			if (value == null || StringUtils.isEmpty(value.toString())
				|| StringUtils.equalsIgnoreCase("null", value.toString())) {
				//空字符串类型
				continue;
			} else {
				PropertyDescriptor setter = setterMethods.get(key);
				if (setter == null) {
					Map<String, Object> extendFields = obj.getExtendFields();
					if (extendFields != null) {
						extendFields.put(key, value.toString());
					}
				} else {
					Method method = setter.getWriteMethod();
					try {
						Class<?> paraType = method.getParameterTypes()[0];
						if (paraType.isEnum()) {
							Method enumGetByCodeMethod = paraType.getMethod("getByCode",
								String.class);
							value = enumGetByCodeMethod.invoke(paraType, value);
						} else if (Money.class.equals(paraType)) {
							value = new Money(value.toString());
						}
						method.invoke(obj, value);
						
					} catch (Exception e) {
						logger.error("invoke class:" + clazz.getName() + "." + method.getName()
										+ "failed!value=" + value, e.getMessage());
					}
				}
			}
		}
		
		return obj;
	}
	
	/**
	 * 获取类中的 注解为ExtendField的属性集合
	 * 
	 * @param clazz
	 * @return
	 */
	private static List<PropertyDescriptor> getExtendFieldOptMethods(Class<?> clazz) {
		List<PropertyDescriptor> optMethods = CollectionUtils.newArrayList();
		
		PropertyDescriptor[] getters = null;
		getters = ReflectUtils.getBeanGetters(clazz);
		try {
			for (PropertyDescriptor getter : getters) {
				String fieldName = getter.getName();
				Field field = getDeclaredField(clazz, fieldName);
				if (field != null) {
					ExtendField extendField = field.getAnnotation(ExtendField.class);
					if (extendField != null) {
						optMethods.add(getter);
					}
				}
			}
		} catch (Exception e) {
		}
		
		return optMethods;
	}
	
	/**
	 * 获取类中 注解为 RuntimeField 的几何
	 * 
	 * @param clazz
	 * @return
	 */
	public static List<Field> getRuntimeField(Class<?> clazz) {
		List<Field> runtimeFields = CollectionUtils.newArrayList();
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			RuntimeField runtimeField = field.getAnnotation(RuntimeField.class);
			if (runtimeField != null) {
				runtimeFields.add(field);
			}
		}
		Class<?> superClass = clazz.getSuperclass();
		if (superClass != null) {
			List<Field> runtimeFieldsInSuperClass = getRuntimeField(superClass);
			runtimeFields.addAll(runtimeFieldsInSuperClass);
		}
		return runtimeFields;
	}
	
	/**
	 * 获取类中 注解 不为 ExtendField 的所有字段
	 * 
	 * @param clazz
	 * @return
	 */
	public static List<Field> getNotExtendField(Class<?> clazz) {
		List<Field> notExtendFields = CollectionUtils.newArrayList();
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			ExtendField extendField = field.getAnnotation(ExtendField.class);
			if (extendField == null) {
				notExtendFields.add(field);
			}
		}
		Class<?> superClass = clazz.getSuperclass();
		if (superClass != null) {
			List<Field> runtimeFieldsInSuperClass = getNotExtendField(superClass);
			notExtendFields.addAll(runtimeFieldsInSuperClass);
		}
		return notExtendFields;
	}
	
	/**
	 * 通过字段名后去 类中的字段
	 * 
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	public static Field getDeclaredField(Class<?> clazz, String fieldName) {
		try {
			return clazz.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			return getDeclaredField(clazz.getSuperclass(), fieldName);
		} catch (Exception e) {
			logger.debug("Cann't find the declaredField [" + fieldName + "]");
			//System.out.println("===============" + fieldName + "===============");
		}
		return null;
	}
	
	public static PropertyDescriptor[] getBeanProperties(Class<?> type) {
		return getPropertiesHelper(type, true, true);
	}
	
	public static PropertyDescriptor[] getBeanGetters(Class<?> type) {
		return getPropertiesHelper(type, true, false);
	}
	
	public static PropertyDescriptor[] getBeanSetters(Class<?> type) {
		return getPropertiesHelper(type, false, true);
	}
	
	private static PropertyDescriptor[] getPropertiesHelper(Class<?> type, boolean read,
															boolean write) {
		try {
			BeanInfo info = Introspector.getBeanInfo(type, Object.class);
			PropertyDescriptor[] all = info.getPropertyDescriptors();
			if (read && write) {
				return all;
			}
			List<PropertyDescriptor> properties = new ArrayList<PropertyDescriptor>(all.length);
			for (int i = 0; i < all.length; i++) {
				PropertyDescriptor pd = all[i];
				if ((read && pd.getReadMethod() != null) || (write && pd.getWriteMethod() != null)) {
					properties.add(pd);
				}
			}
			return (PropertyDescriptor[]) properties.toArray(new PropertyDescriptor[properties
				.size ()]);
		} catch (IntrospectionException e) {
			throw new CodeGenerationException(e);
		}
	}
	
	protected static <K, V> void removeNullValue(Map<K, V> map) {
		Set<K> keys = new HashSet<K>(map.keySet());
		for (K key : keys) {
			if (map.get(key) == null) {
				map.remove(key);
			}
		}
	}
	
	/**
	 * 复制扩展传入的扩展字段
	 * 
	 * @param extendFields
	 * @param params
	 */
	protected static void copyMap(Map<Object, Object> params, Map<String, Object> extendFields) {
		if (CollectionUtils.isNotEmpty(params)) {
			
			Iterator<Map.Entry<Object, Object>> entryKeyIterator = params.entrySet().iterator();
			while (entryKeyIterator.hasNext()) {
				Map.Entry<Object, Object> entry = entryKeyIterator.next();
				Object key = entry.getKey();
				if (key != null) {
					Object value = entry.getValue();
					if (value != null) {
						extendFields.put(key.toString(), value.toString());
					}
				}
			}
		}
	}
}

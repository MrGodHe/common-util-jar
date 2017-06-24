/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-05-26 20:07 创建
 *
 */
package cn.pay.ebank.common.cache;

/**
 * @author zhyang@ebank.pay.cn
 */

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.ReflectUtils;

import cn.pay.ebank.common.utils.CollectionUtils;
import cn.pay.ebank.common.utils.StringUtils;

/**
 * 缓存基类
 *
 * @param <K>
 * @param <V>
 */
public abstract class Cache<K, V> implements ICache {

	/**
	 * 日志
	 */
	protected final Logger logger = LoggerFactory.getLogger (getClass ());

	/**
	 * 返回所有缓存中的值
	 *
	 * @return
	 */
	public List<V> getAllValues () {
		return new LinkedList<V> (values ());
	}

	/**
	 * 抽象方法，子类实现，获取缓存中的所有值
	 *
	 * @return
	 */
	protected abstract Collection<V> values ();

	/**
	 * 更新缓存
	 *
	 * @param values
	 * @param parms  其他参数
	 */
	public void update (Collection<V> values, Object... parms) {
		update (values, false, parms);
	}

	protected abstract Object getCache ();

	public void update (Collection<V> newInfos, boolean isAppend, Object... parms) {
		if (CollectionUtils.isNotEmpty (newInfos)) {
			synchronized (getCache ()) {
				if (!isAppend) {
					clearCache ();
				}
				for (V info : newInfos) {
					addToCache (info, parms);
				}
			}
		}
	}

	/**
	 * 添加 值到缓存
	 *
	 * @param value
	 */
	public abstract void addToCache (V value, Object... parms) ;

	/**
	 * 添加值到缓存，并指定key
	 *
	 * @param key
	 * @param value
	 */
	public abstract void addToCache (K key, V value);

	/**
	 * 清空缓存
	 */
	public abstract void clearCache ();


	/**
	 * 将所有的Key字段合并为一个字符串
	 *
	 * @param keyFields
	 * @return
	 */
	public String buildCacheKey (Object... keyFields) {
		StringBuilder keyBuilder = new StringBuilder ();
		int c = 0;
		for (Object keyField : keyFields) {
			if (c++ > 0) {
				keyBuilder.append ("@");
			}
			if (keyField == null) {
				keyField = "NULL";
			} else if (keyField instanceof String && StringUtils.isEmpty ((String) keyField)) {
				keyField = "NULL";
			}
			keyBuilder.append (keyField);
		}
		return keyBuilder.toString ();
	}

	/**
	 * 获取缓存中的值
	 *
	 * @param key
	 * @return
	 */
	public abstract V getValue (K key);


	/**
	 * 从缓存中删除一条数据
	 *
	 * @param key
	 */
	public abstract void remove (K key);

	/**
	 * 更新缓存，如果缓存中无此记录则添加
	 *
	 * @param value
	 */
	public abstract void update (V value, Object... parms);

	/**
	 * 更新缓存，如果缓存中无此记录则添加
	 *
	 * @param key
	 * @param value
	 */
	public abstract void update (K key, V value);

	/**
	 * 缓存过滤器，从缓存中获取与Value值相同属性的值
	 *
	 * @param value
	 * @return
	 */
	public List<V> filter (V value, Object... parms) {
		return filter (getAllValues (), value, parms);
	}

	public List<V> filter (List<V> values, V value, Object... parms) {
		if (value == null) {
			return values;
		}
		PropertyDescriptor[] properties = ReflectUtils.getBeanProperties (value.getClass ());
		for (PropertyDescriptor property : properties) {
			String propertyValue = null;
			try {
				Object obj = property.getReadMethod ().invoke (value);
				if (obj != null) {
					propertyValue = obj.toString ();
				}
			} catch (Exception e) {
			}
			values = filter (values, property.getName (), propertyValue, FilterStringType.EQUALS_IGNORE_CASE);
		}
		return values;
	}

	public List<V> filter (List<V> values, String propertyName, String propertyValue, Object... parms) {
		return filter (values, propertyName, propertyValue, FilterStringType.EQUALS_IGNORE_CASE, parms);
	}

	public List<V> filter (List<V> values, String propertyName, String propertyValue, FilterStringType filterType, Object... parms) {
		if (StringUtils.isNotEmpty (propertyValue) && CollectionUtils.isNotEmpty (values)) {
			Iterator<V> iterator = values.iterator ();
			V v = values.get (0);
			PropertyDescriptor getter = getGetterMethods (v.getClass (), propertyName);
			if (getter != null) {
				Method method = getter.getReadMethod ();
				while (iterator.hasNext ()) {
					V valueInfo = iterator.next ();
					Object value = null;
					try {
						value = method.invoke (valueInfo);
					} catch (Exception e) {
					}

					boolean flag = false;
					switch (filterType) {
						case EQUALS_IGNORE_CASE:
							flag = propertyValue.equalsIgnoreCase (value == null ? null : value.toString ());
							break;
						case EQUALS:
							flag = propertyValue.equals (value == null ? null : value.toString ());
							break;
						case MATCHES:
							flag = value == null ? false : value.toString ().matches (propertyValue);
							break;
						case CONTAINS:
							flag = value == null ? false : value.toString ().contains (propertyValue);
						case START_WITH:
							flag = value == null ? false : value.toString ().startsWith (propertyValue);
							break;
						case END_WITH:
							flag = value == null ? false : value.toString ().endsWith (propertyValue);
							break;
						default:
							flag = propertyValue.equalsIgnoreCase (value == null ? null : value.toString ());
							break;
					}
					if (!flag) {
						iterator.remove ();
					}
				}
			}
		}
		return values;
	}

	/**
	 * 获取类中的 注解为ExtendField的属性集合
	 *
	 * @param clazz
	 * @return
	 */
	private static PropertyDescriptor getGetterMethods (Class<?> clazz, String propertyName) {
		PropertyDescriptor[] getters = ReflectUtils.getBeanGetters (clazz);
		try {
			for (PropertyDescriptor getter : getters) {
				String fieldName = getter.getName ();
				if (fieldName.equals (propertyName)) {
					return getter;
				}
			}
		} catch (Exception e) {
		}

		return null;
	}


	/**
	 * 格式化Key<P/>
	 * 如果Key是字符串，默认转换为大写
	 *
	 * @param key
	 * @return
	 */
	protected K formatKey (K key) {
		if (key != null && key instanceof String) {
			return (K) ((String) key).toUpperCase ();
		}
		return key;
	}

	/**
	 * 缓存ID
	 *
	 * @return
	 */
	public int getCacheIndex () {
		return DEFAULT_CACHE_INDEX;
	}
}

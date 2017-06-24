/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-04-21 14:07 创建
 *
 */
package cn.pay.ebank.common.utils;

import cn.pay.ebank.common.collections.Stack;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 集合类(List、Map、Set)工具包
 *
 * @author zhyang@ebank.pay.cn
 */
public class CollectionUtils {

	/**
	 * 判断Collection是否为空。
	 *
	 * @param collection 要判断的Collection。
	 * @return 如果Collection为 null 或者 {@link java.util.Collection#isEmpty()} 返回true时，则返回
	 * true。
	 */
	public static boolean isEmpty (Collection<?> collection) {
		return (collection == null || collection.isEmpty ());
	}

	/**
	 * 判断Map是否为空。
	 *
	 * @param map 要判断的Map。
	 * @return 如果Map为 null 或者 {@link java.util.Map#isEmpty()} 返回true时，则返回 true。
	 */
	public static boolean isEmpty (Map<?, ?> map) {
		return (map == null || map.isEmpty ());
	}

	/**
	 * 判断Set是否为空。
	 *
	 * @param set 要判断的Set。
	 * @return 如果Map为 null 或者 {@link java.util.Map#isEmpty()} 返回true时，则返回 true。
	 */
	public static boolean isEmpty (Set<?> set) {
		return (set == null || set.isEmpty ());
	}

	/**
	 * 判断Collection是否不为空。
	 *
	 * @param collection 要判断的Collection。
	 * @return 如果Collection不为 null 且 {@link java.util.Collection#isEmpty()} 返回false时，则返回
	 * true。
	 */
	public static boolean isNotEmpty (Collection<?> collection) {
		return (collection != null && !collection.isEmpty ());
	}

	/**
	 * 判断Set是否不为空。
	 *
	 * @param set 要判断的Set。
	 * @return 如果Map不为 null 且 {@link java.util.Map#isEmpty()} 返回false时，则返回 true。
	 */
	public static boolean isNotEmpty (Set<?> set) {
		return (set != null && !set.isEmpty ());
	}

	/**
	 * 判断Map是否不为空。
	 *
	 * @param map 要判断的Map。
	 * @return 如果Map不为 null 且 {@link java.util.Map#isEmpty()} 返回false时，则返回 true。
	 */
	public static boolean isNotEmpty (Map<?, ?> map) {
		return (map != null && !map.isEmpty ());
	}

	/**
	 * 返回 new ArrayList()
	 *
	 * @param <E>
	 * @return
	 */
	public static <E> ArrayList<E> newArrayList () {
		return new ArrayList<E> ();
	}

	/**
	 * 返回 new ArrayList(initialCapacity)
	 *
	 * @param initialCapacity
	 * @param <E>
	 * @return
	 */
	public static <E> ArrayList<E> newArrayList (int initialCapacity) {
		return new ArrayList<E> (initialCapacity);
	}

	/**
	 * new ArrayList(Collection)
	 *
	 * @param c
	 * @param <E>
	 * @return
	 */
	public static <E> ArrayList<E> newArrayList (Collection<? extends E> c) {
		return new ArrayList<E> (c);
	}


	/**
	 * 返回 new LinkedList()
	 *
	 * @param <E>
	 * @return
	 */
	public static <E> LinkedList<E> newLinkedList () {
		return new LinkedList<E> ();
	}

	/**
	 * new LinkedList(Collection)
	 *
	 * @param c
	 * @param <E>
	 * @return
	 */
	public static <E> LinkedList<E> newLinkedList (Collection<? extends E> c) {
		return new LinkedList<E> (c);
	}

	/**
	 * 返回 new HashMap()
	 *
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V> HashMap<K, V> newHashMap () {
		return new HashMap<K, V> ();
	}

	/**
	 * 返回 new HashMap(initialCapacity)
	 *
	 * @param initialCapacity
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V> HashMap<K, V> newHashMap (int initialCapacity) {
		return new HashMap<K, V> (initialCapacity);
	}

	/**
	 * 返回 new HashMap(Map)
	 *
	 * @param m
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V> HashMap<K, V> newHashMap (Map<? extends K, ? extends V> m) {
		return new HashMap<K, V> (m);
	}

	/**
	 * 返回 new ConcurrentHashMap()
	 *
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V> ConcurrentMap<K, V> newConcurrentMap () {
		return new ConcurrentHashMap<K, V> ();

	}

	/**
	 * 返回 new ConcurrentHashMap(initialCapacity)
	 *
	 * @param initialCapacity
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V> ConcurrentMap<K, V> newConcurrentMap (int initialCapacity) {
		return new ConcurrentHashMap<K, V> (initialCapacity);
	}

	/**
	 * 返回 new ConcurrentHashMap(Map)
	 *
	 * @param m
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V> ConcurrentMap<K, V> newConcurrentMap (Map<? extends K, ? extends V> m) {
		return new ConcurrentHashMap<K, V> (m);
	}

	/**
	 * 返回 new HashMap()
	 *
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V> Hashtable<K, V> newHashtable () {
		return new Hashtable<K, V> ();
	}

	/**
	 * 返回 new Hashtable(initialCapacity)
	 *
	 * @param initialCapacity
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V> Hashtable<K, V> newHashtable (int initialCapacity) {
		return new Hashtable<K, V> (initialCapacity);
	}

	/**
	 * 返回 new Hashtable(Map)
	 *
	 * @param m
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V> Hashtable<K, V> newHashtable (Map<? extends K, ? extends V> m) {
		return new Hashtable<K, V> (m);
	}

	/**
	 * 返回 new LinkedHashMap()
	 *
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V> LinkedHashMap<K, V> newLinkedHashMap () {
		return new LinkedHashMap<K, V> ();
	}

	/**
	 * 返回 new LinkedHashMap(initialCapacity)
	 *
	 * @param initialCapacity
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V> LinkedHashMap<K, V> newLinkedHashMap (int initialCapacity) {
		return new LinkedHashMap<K, V> (initialCapacity);
	}

	/**
	 * 返回 new LinkedHashMap(Map)
	 *
	 * @param m
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V> LinkedHashMap<K, V> newLinkedHashMap (Map<? extends K, ? extends V> m) {
		return new LinkedHashMap<K, V> (m);
	}

	/**
	 * 返回 new TreeMap()
	 *
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V> TreeMap<K, V> newTreeMap () {
		return new TreeMap<K, V> ();
	}

	/**
	 * 返回 new TreeMap(Comparator)
	 *
	 * @param comparator
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V> TreeMap<K, V> newTreeMap (Comparator<? super K> comparator) {
		return new TreeMap<K, V> (comparator);
	}

	/**
	 * 返回 new TreeMap(Map)
	 *
	 * @param m
	 * @param <K>
	 * @param <V>
	 * @return
	 */
	public static <K, V> TreeMap<K, V> newTreeMap (Map<? extends K, ? extends V> m) {
		return new TreeMap<K, V> (m);
	}


	/**
	 * 返回 new HashSet()
	 *
	 * @param <E>
	 * @return
	 */
	public static <E> HashSet<E> newHashSet () {
		return new HashSet<E> ();
	}

	/**
	 * 返回 new HashSet(initialCapacity)
	 *
	 * @param initialCapacity
	 * @param <E>
	 * @return
	 */
	public static <E> HashSet<E> newHashSet (int initialCapacity) {
		return new HashSet<E> (initialCapacity);
	}

	/**
	 * new HashSet(Collection)
	 *
	 * @param c
	 * @param <E>
	 * @return
	 */
	public static <E> LinkedHashSet<E> newHashSet (Collection<? extends E> c) {
		return new LinkedHashSet<E> (c);
	}

	/**
	 * 返回 new LinkedHashSet()
	 *
	 * @param <E>
	 * @return
	 */
	public static <E> LinkedHashSet<E> newLinkedHashSet () {
		return new LinkedHashSet<E> ();
	}

	/**
	 * 返回 new LinkedHashSet(initialCapacity)
	 *
	 * @param initialCapacity
	 * @param <E>
	 * @return
	 */
	public static <E> LinkedHashSet<E> newLinkedHashSet (int initialCapacity) {
		return new LinkedHashSet<E> (initialCapacity);
	}

	/**
	 * new LinkedHashSet(Collection)
	 *
	 * @param c
	 * @param <E>
	 * @return
	 */
	public static <E> LinkedHashSet<E> newLinkedHashSet (Collection<? extends E> c) {
		return new LinkedHashSet<E> (c);
	}

	/**
	 * 返回 new LinkedHashSet()
	 *
	 * @param <E>
	 * @return
	 */
	public static <E> TreeSet<E> newTreeSet () {
		return new TreeSet<E> ();
	}

	/**
	 * 返回 new TreeSet(Comparator)
	 *
	 * @param comparator
	 * @param <E>
	 * @return
	 */
	public static <E> TreeSet<E> newTreeSet (Comparator<? super E> comparator) {
		return new TreeSet<E> (comparator);
	}

	/**
	 * new TreeSet(Collection)
	 *
	 * @param c
	 * @param <E>
	 * @return
	 */
	public static <E> TreeSet<E> newTreeSet (Collection<? extends E> c) {
		return new TreeSet<E> (c);
	}

	/**
	 * 将指定元素添加到指定 collection 中。如果为数组或者{@link java.util.Collection}或者{@link java.util.Iterator}
	 * 的子类则分别添加数组的每个元素，否则直接添加。
	 *
	 * @param collection value 所要插入的 collection。
	 * @param value      插入 collection 的元素。
	 * @throws ClassCastException 如果 value 的类型和 collection 持有类型不兼容。
	 */
	@SuppressWarnings("unchecked")
	public static <T> void add (Collection<T> collection, Object value) {
		if (value == null) {
			collection.add (null);
		} else {
			// 数组
			if (value.getClass ().isArray ()) {
				if (value instanceof Object[]) {
					for (Object o : (Object[]) value) {
						collection.add ((T) o);
					}
				} else {
					int length = Array.getLength (value);
					for (int i = 0; i < length; i++) {
						collection.add ((T) Array.get (value, i));
					}
				}
			} else { // 非数组
				if (value instanceof Collection<?> || value instanceof Stack<?>) {
					for (Object object : (Iterable<T>) value) {
						collection.add ((T) object);
					}
				} else if (value instanceof Iterator<?>) {
					// 迭代器
					for (Iterator<T> iterator = (Iterator<T>) value; iterator.hasNext (); ) {
						collection.add (iterator.next ());
					}
				} else {
					collection.add ((T) value);
				}
			}
		}
	}


	/**
	 * 将所有指定元素添加到指定 stack 中。如果为数组或者{@link java.util.Collection}的子类则分别添加数组的每个元素，否则直接添加。
	 *
	 * @param stack value 所要插入的 Stack。
	 * @param value 推入 stack 的元素。
	 * @throws ClassCastException 如果 value 的类型和 stack 持有类型不兼容。
	 */
	@SuppressWarnings("unchecked")
	public static <T> void push (Stack<T> stack, Object value) {
		if (value == null) {
			stack.push (null);
		} else {
			// 数组
			if (value.getClass ().isArray ()) {
				if (value instanceof Object[]) {
					for (Object o : (Object[]) value) {
						stack.push ((T) o);
					}
				} else {
					int length = Array.getLength (value);
					for (int i = 0; i < length; i++) {
						stack.push ((T) Array.get (value, i));
					}
				}
			} else { // 非数组
				if (value instanceof Collection || value instanceof Stack) {
					for (Object object : (Iterable<? super T>) value) {
						stack.push ((T) object);
					}
				} else {
					stack.push ((T) value);
				}
			}
		}
	}
}

/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-04-21 11:49 创建
 *
 */
package cn.pay.ebank.common.utils;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import javassist.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * /**
 * 转换对象为字符串.
 * <p/>
 * 不输出的请在getter上标注{@link ToStringBuilder.Invisible}
 * <p/>
 * 需要掩码的请在getter上标注{@link ToStringBuilder.Maskable}
 * <p/>
 * 需要更改默认的toString()方法的Getter上标注{@link ToStringBuilder.ToStringMethod}
 * <p/>
 * java.util.Date会按照"yyyy-MM-dd HH:mm:ss"格式输出
 * <p/>
 * 数组(50)/集合(50)/Map(50)/String(100)超过一定的限制(括号内数字),多余的元素会输出为...
 * <p/>
 * 集合/Map已检查循环引用问题.能确保原来toString不出问题,此方法也不会出问题.
 * <p/>
 * mask长度超过100时,会先截断在mask.
 * <p/>
 * StringBuilder会初始化大小
 * <p/>
 * <p/>
 * <h3>Usage Examples</h3>
 * 修改javabean的toString方法为:
 * <p/>
 * <pre class="code">
 * {@code
 *
 * @author zhyang@ebank.pay.cn
 * @Override public String toString() {
 * return ToStringBuilder.toString(this);
 * }
 * }
 * </pre>
 * <p/>
 * 使用时请确保classpath中有javassist:
 * <p/>
 * <pre class="code">
 * {@code
 * <dependency>
 * <groupId>org.javassist</groupId>
 * <artifactId>javassist</artifactId>
 * </dependency>
 * }
 * </pre>
 */
public class ToStringBuilder {
	private static final String packageName = getPackageName (ToStringBuilder.class);
	private static final String NULL = "null";
	private static final Map<String, I> cache = CollectionUtils.newConcurrentMap ();
	private static final char SEPARATOR_CHAR_ASTERISK = '*';
	private static final String DATE_FORMART = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 打印生成源代码
	 */
	public static boolean logSource = false;
	public static String dumpClass = null;
	private static final Logger logger = LoggerFactory.getLogger (ToStringBuilder.class);

	public static interface I {
		String toString (Object source);
	}

	/**
	 * 标注在getter上,标识此属性不会输出
	 */
	@Target({ElementType.METHOD})
	@Retention(RUNTIME)
	@Documented
	public static @interface Invisible {

	}

	/**
	 * 标注在getter上,标识此属性会被mask,只支持String类型
	 */
	@Target({ElementType.METHOD})
	@Retention(RUNTIME)
	@Documented
	public static @interface Maskable {

	}

	/**
	 * 标注在getter上,标识此属性在超长时不被drop
	 */
	@Target({ElementType.METHOD})
	@Retention(RUNTIME)
	@Documented
	public static @interface DropIfTooLong {
		int value () default 100;
	}

	/**
	 * 标注在getter上,调用此getter对象xx方法 <br/>
	 * xx方法必须满足下面两个条件: 1.没有入参 2.返回String <br/>
	 * 比如:
	 * <p/>
	 * <pre>
	 * {@code
	 *   @ToString.ToStringMethod("toSX")
	 *   public NestBean getNestBean() {
	 *      return nestBean;
	 *   }
	 * }
	 * 生成toString会调用NestBean对象的toSX()方法.
	 * </pre>
	 */
	@Target({ElementType.METHOD})
	@Retention(RUNTIME)
	@Documented
	public static @interface ToStringMethod {
		String value ();
	}

	/**
	 * 把对象转换为字符串.
	 *
	 * @param o
	 *
	 * @return
	 */
	public static String toString (Object o) {
		if (o == null) {
			return NULL;
		}

		I i = cache.get (o.getClass ().getName ());
		if (i == null) {
			synchronized (ToStringBuilder.class) {
				i = cache.get (o.getClass ().getName ());
				if (i == null) {
					Generator generator = new Generator (o.getClass ());
					try {
						i = generator.generate ().newInstance ();
						cache.put (o.getClass ().getName (), i);
					} catch (Exception e) {
					}
				}
			}
		}

		return i.toString (o);
	}

	public static class Generator {

		/**
		 * 超过此长度的数组元素用...表示
		 */
		private static final int TO_STRING_ARRAY_SIZE_THRESHOLD = 50;
		private static final int TO_STRING_COLLECTION_SIZE_THRESHOLD = 50;
		private static final int TO_STRING_MAP_SIZE_THRESHOLD = 50;
		private static final int TO_STRING_STRING_SIZE_THRESHOLD = 100;
		private static final int TO_STRING_MASK_SIZE_THRESHOLD = 100;
		private static AtomicInteger classNameIndex = new AtomicInteger (100000);

		private Class source;
		private static final String SOURCE = "s";
		private String beginSource;
		private String endSources;
		private List<String> bodySources = CollectionUtils.newArrayList ();
		private int fieldIndex = 0;
		private int fieldCount = 0;

		public Generator (Class source) {
			this.source = source;
		}

		private void generateBegin () {
			beginSource = "public String toString(Object " + SOURCE + "1){\n";
			// 强制转换源对象
			String convertSource = source.getName () + " " + SOURCE + " =" + "(" + source.getName () + ")" + SOURCE + "1;\n";

			beginSource += convertSource;
		}

		private void generateEnd () {

			endSources = "\nsb.append(\"}\");\n return sb.toString();}";
		}

		private void generateBody () {
			PropertyDescriptor[] getters = getPropertyDescriptors (source);

			bodySources.add ("if(" + SOURCE + "==null){return null;}");
			bodySources.add ("\n");
			int size = (getters.length - 1) * 15;
			bodySources.add ("StringBuilder sb=new StringBuilder(" + size + ");");
			bodySources.add ("\n");
			bodySources.add ("sb.append(\"" + source.getSimpleName () + "{\");");
			fieldCount = getters.length - 1;
			for (PropertyDescriptor getter : getters) {
				Method read = getter.getReadMethod ();
				if (getter.getName ().equals ("class")) {
					continue;
				}
				if (read == null || read.getAnnotation (Invisible.class) != null) {
					fieldCount--;
					continue;
				}
				fieldIndex++;
				Class<?> type = getter.getPropertyType ();
				String readMethod = SOURCE + "." + read.getName () + "()";
				String propName = getter.getName ();
				String toStringClassName = ToStringBuilder.class.getName ();

				if (read.getAnnotation (ToStringMethod.class) != null) {
					readMethod = "(" + readMethod + "==null?null:" + readMethod + "." + getter.getReadMethod ().getAnnotation (ToStringMethod.class).value () + "())";
				}

				boolean dropIfTooLong = (read.getAnnotation (DropIfTooLong.class) != null);
				int dropIfTooLongValue = TO_STRING_STRING_SIZE_THRESHOLD;
				if(dropIfTooLong) {
					dropIfTooLongValue=read.getAnnotation (DropIfTooLong.class).value ();
				}
				if (isPrimitive (type) || isWrapClass (type)) {
					doProp (propName, readMethod);
				} else if (type.isArray ()) {
					doProp (propName, toStringClassName + ".toString(" + readMethod + ", " + (dropIfTooLong ? dropIfTooLongValue : TO_STRING_ARRAY_SIZE_THRESHOLD) + ")");

				} else if (type == String.class) {
					if (getter.getReadMethod ().getAnnotation (Maskable.class) != null) {
						doProp (propName, toStringClassName + ".mask(" + readMethod + ")");
					} else {
						doProp (propName, toStringClassName + ".toString(" + readMethod + ", " + (dropIfTooLong ? dropIfTooLongValue : TO_STRING_STRING_SIZE_THRESHOLD) + ")");
					}
				} else {
					if (isCollection (type)) {
						doProp (propName, toStringClassName + ".toString(" + readMethod + ", " + (dropIfTooLong ? dropIfTooLongValue : TO_STRING_COLLECTION_SIZE_THRESHOLD) + ")");

					} else if (isMap (type)) {
						doProp (propName, toStringClassName + ".toString(" + readMethod + ", " + (dropIfTooLong ? dropIfTooLongValue : TO_STRING_MAP_SIZE_THRESHOLD) + ")");
					} else if (type == Date.class) {
						doProp (propName, toStringClassName + ".parseDate(" + readMethod + ")");
					} else {
						doProp (propName, readMethod);
					}
				}
			}

		}

		private void doProp (String name, String readMethod) {
			bodySources.add ("\nsb.append(\"" + name + "=\");");
			bodySources.add ("\nsb.append(" + readMethod + ");");
			if (fieldIndex < fieldCount) {
				bodySources.add ("\nsb.append(\",\");");
			}

		}

		public Class<I> generate () {
			generateBegin ();
			generateBody ();
			generateEnd ();
			StringBuilder sb = new StringBuilder ();
			sb.append (beginSource);
			for (String propSource : bodySources) {
				sb.append (propSource);
			}
			sb.append (endSources);
			String source = sb.toString ();
			if (logSource) {
				logger.info ("\n{}", source);
			}

			ClassPool pool = ClassPool.getDefault ();
			ClassClassPath classPath = new ClassClassPath (this.getClass ());
			pool.insertClassPath (classPath);
			CtClass cc = pool.makeClass (packageName + ".ToStringImpl" + classNameIndex.incrementAndGet ());

			Class<I> copyClass = null;
			try {
				cc.addInterface (pool.get (I.class.getName ()));
				CtMethod m = CtNewMethod.make (source, cc);
				cc.addMethod (m);
				if (dumpClass != null) {
					CtClass.debugDump = dumpClass;
				}
				ClassLoader classLoader = getDefaultClassLoader ();
				logger.debug ("classloader:{}", classLoader);
				copyClass = cc.toClass (classLoader, null);
			} catch (Exception e) {
				throw new RuntimeException (e);
			}
			return copyClass;
		}

		private ClassLoader getDefaultClassLoader () {
			ClassLoader cl = null;
			try {
				cl = Thread.currentThread ().getContextClassLoader ();
			} catch (Exception ex) {
				// ignore
			}
			if (cl == null) {
				cl = this.getClass ().getClassLoader ();
			}
			return cl;
		}

		public PropertyDescriptor[] getPropertyDescriptors (Class<?> clazz) {
			BeanInfo beanInfo;
			try {
				beanInfo = Introspector.getBeanInfo (clazz);
				return beanInfo.getPropertyDescriptors ();
			} catch (IntrospectionException e) {
				throw new RuntimeException (e);
			}

		}

		private boolean isMap (Class<?> type) {
			return Map.class.isAssignableFrom (type);
		}

		private boolean isCollection (Class<?> type) {
			return Collection.class.isAssignableFrom (type);
		}

		public static boolean isPrimitive (Class source) {
			return source.isPrimitive ();
		}

		public static boolean isWrapClass (Class source) {
			try {
				source.getField ("TYPE").get (null);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
	}

	private static String getPackageName (Class<?> clazz) {
		String className = clazz.getName ();
		int lastDotIndex = className.lastIndexOf (".");
		return (lastDotIndex != -1 ? className.substring (0, lastDotIndex) : "");
	}

	/**
	 * 集合toString 注意避免循环引用
	 *
	 * @param collection
	 * @param size
	 *
	 * @return
	 */
	public static String toString (Collection collection, int size) {
		if (collection == null) {
			return NULL;
		}
		Iterator it = collection.iterator ();
		if (!it.hasNext ())
			return "[]";

		StringBuilder sb = new StringBuilder (10 * Math.min (size, collection.size ()));
		sb.append ('[');
		for (int i = 0; i < size; i++) {
			Object e = it.next ();
			sb.append (e == collection ? "(this Collection)" : e);
			if (!it.hasNext ())
				return sb.append (']').toString ();
			sb.append (',').append (' ');
		}
		sb.append ("...]");
		return sb.toString ();
	}

	/**
	 * map tostring 注意避免循环引用
	 *
	 * @param map
	 * @param size
	 *
	 * @return
	 */
	public static String toString (Map map, int size) {
		if (map == null) {
			return NULL;
		}
		Iterator<Map.Entry> it = map.entrySet ().iterator ();
		if (!it.hasNext ())
			return "{}";

		StringBuilder sb = new StringBuilder (10 * Math.min (size, map.size ()));
		sb.append ('{');
		for (int i = 0; i < size; i++) {
			Map.Entry e = it.next ();
			Object key = e.getKey ();
			Object value = e.getValue ();
			sb.append (key == map ? "(this Map)" : key);
			sb.append ('=');
			sb.append (value == map ? "(this Map)" : value);
			if (!it.hasNext ()) {
				return sb.append ('}').toString ();
			}
			sb.append (',').append (' ');
		}
		sb.append ("...}");
		return sb.toString ();
	}

	/**
	 * string tostring
	 *
	 * @param str
	 * @param size
	 *
	 * @return
	 */
	public static String toString (String str, int size) {
		if (str == null) {
			return NULL;
		}
		if (str.length () < size) {
			return str;
		} else {
			return str.substring (0, size) + "...";
		}

	}

	public static String toString (long[] a, int size) {
		if (a == null)
			return NULL;
		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";

		StringBuilder b = new StringBuilder (5 * Math.min (size, a.length));
		b.append ('[');
		for (int i = 0; i < size; i++) {
			b.append (a[i]);
			if (i == iMax)
				return b.append (']').toString ();
			b.append (", ");
		}
		b.append ("...]");
		return b.toString ();
	}

	public static String toString (int[] a, int size) {
		if (a == null)
			return NULL;
		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";

		StringBuilder b = new StringBuilder (5 * Math.min (size, a.length));
		b.append ('[');
		for (int i = 0; i < size; i++) {
			b.append (a[i]);
			if (i == iMax)
				return b.append (']').toString ();
			b.append (", ");
		}
		b.append ("...]");
		return b.toString ();
	}

	public static String toString (short[] a, int size) {
		if (a == null)
			return NULL;
		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";

		StringBuilder b = new StringBuilder (3 * Math.min (size, a.length));
		b.append ('[');
		for (int i = 0; i < size; i++) {
			b.append (a[i]);
			if (i == iMax)
				return b.append (']').toString ();
			b.append (", ");
		}
		b.append ("...]");
		return b.toString ();
	}

	public static String toString (char[] a, int size) {
		if (a == null)
			return NULL;
		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";

		StringBuilder b = new StringBuilder (1 * Math.min (size, a.length));
		b.append ('[');
		for (int i = 0; i < size; i++) {
			b.append (a[i]);
			if (i == iMax)
				return b.append (']').toString ();
			b.append (", ");
		}
		b.append ("...]");
		return b.toString ();
	}

	public static String toString (byte[] a, int size) {
		if (a == null)
			return NULL;
		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";

		StringBuilder b = new StringBuilder (1 * Math.min (size, a.length));
		b.append ('[');
		for (int i = 0; i < size; i++) {
			b.append (a[i]);
			if (i == iMax)
				return b.append (']').toString ();
			b.append (", ");
		}
		b.append ("...]");
		return b.toString ();
	}

	public static String toString (boolean[] a, int size) {
		if (a == null)
			return NULL;
		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";

		StringBuilder b = new StringBuilder (5 * Math.min (size, a.length));
		b.append ('[');
		for (int i = 0; i < size; i++) {
			b.append (a[i]);
			if (i == iMax)
				return b.append (']').toString ();
			b.append (", ");
		}
		b.append ("...]");
		return b.toString ();
	}

	public static String toString (float[] a, int size) {
		if (a == null)
			return NULL;

		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";

		StringBuilder b = new StringBuilder (5 * Math.min (size, a.length));
		b.append ('[');
		for (int i = 0; i < size; i++) {
			b.append (a[i]);
			if (i == iMax)
				return b.append (']').toString ();
			b.append (", ");
		}
		b.append ("...]");
		return b.toString ();
	}

	public static String toString (double[] a, int size) {
		if (a == null)
			return NULL;
		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";

		StringBuilder b = new StringBuilder (5 * Math.min (size, a.length));
		b.append ('[');
		for (int i = 0; i < size; i++) {
			b.append (a[i]);
			if (i == iMax)
				return b.append (']').toString ();
			b.append (", ");
		}
		b.append ("...]");
		return b.toString ();
	}

	public static String toString (Object[] a, int size) {
		if (a == null)
			return NULL;

		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";

		StringBuilder b = new StringBuilder (10 * Math.min (size, a.length));
		b.append ('[');
		for (int i = 0; i < size; i++) {
			b.append (String.valueOf (a[i]));
			if (i == iMax)
				return b.append (']').toString ();
			b.append (", ");
		}
		b.append ("...]");
		return b.toString ();
	}

	public static String mask (String str) {
		if (str == null || str.length () == 0) {
			return str;
		}
		if (str.length () == 1) {
			return String.valueOf (SEPARATOR_CHAR_ASTERISK);
		}
		if (str.length () > Generator.TO_STRING_MASK_SIZE_THRESHOLD) {
			str = str.substring (0, Generator.TO_STRING_MASK_SIZE_THRESHOLD) + "...";
		}
		int maskLen = Math.max (str.length () / 2, 1);
		int begin = (str.length () - maskLen) / 2 + 1;
		//复制整个str
		char[] chars = str.toCharArray ();
		char[] mask = repeatAsterisk (maskLen);
		//复制mask
		System.arraycopy (mask, 0, chars, begin, maskLen);
		//复制输出
		return new String (chars);
	}

	public static String parseDate (Date date) {
		if (date == null) {
			return NULL;
		}
		return new SimpleDateFormat (DATE_FORMART).format (date);
	}

	private static char[] repeatAsterisk (int len) {
		char[] chars = new char[len];
		for (int i = 0; i < len; i++) {
			chars[i] = SEPARATOR_CHAR_ASTERISK;
		}
		return chars;
	}
}

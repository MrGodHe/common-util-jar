package cn.pay.ebank.common.utils.asserttools;


import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import cn.pay.ebank.common.utils.CollectionUtils;
import cn.pay.ebank.common.utils.ObjectUtils;

/**
 * Created by zhyang on 2015/2/16 0016.
 */
public class CollectionAssertTool extends AssertToolBase<CollectionAssertTool, Collection<?>> {

	private CollectionAssertTool (Collection<?> cols, String name) {
		super (cols, name);
	}

	/**
	 * 断言对象
	 * <p/>
	 * assertThat(name,"Name");
	 *
	 * @param cols 需要断言的字符串
	 * @param name 对象名称
	 *
	 * @return
	 */
	public static CollectionAssertTool assertThat (Collection<?> cols, String name) {
		return new CollectionAssertTool (cols, name);
	}


	/**
	 * 断言对象
	 * <p/>
	 * assertThat(str);
	 *
	 * @param cols 需要断言的对象
	 *
	 * @return
	 */
	public static CollectionAssertTool assertThat (Collection<?> cols) {
		return assertThat (cols, "");
	}

	public CollectionAssertTool isEmpty () {
		if (CollectionUtils.isNotEmpty (object)) {
			throw new IllegalArgumentException (objectName + " 应为空!");
		}
		return this;
	}

	public CollectionAssertTool notEmpty () {
		if (CollectionUtils.isEmpty (object)) {
			throw new IllegalArgumentException (objectName + " 不应为空!");
		}
		return this;
	}

	public CollectionAssertTool contains (Object obj) {
		if (CollectionUtils.isEmpty (object)) {
			throw new IllegalArgumentException (objectName + " 不应为空!");
		} else {
			for (Object value : object) {
				if (value != null && value.equals (obj)) {
					return this;
				}
			}
		}
		throw new IllegalArgumentException (objectName + " 应包含[" + obj + "]!");
	}

	/**
	 * 是否已排序
	 *
	 * @return
	 */
	public CollectionAssertTool isSorted () {
		if (object != null) {
			//对于小于1就不用排序了
			if (object.size () > 2) {
				Iterator iterator = object.iterator ();
				Object element1 = iterator.next ();
				Object element2 = iterator.next ();

				//顺序
				int flag = ((Comparable) element1).compareTo (element2);
				while (flag != 0 && iterator.hasNext ()) {
					element1 = element2;
					element2 = iterator.next ();
					flag = ((Comparable) element1).compareTo (element2);
				}

				element1 = element2;
				if (flag > 0) {
					while (flag != 0 && iterator.hasNext ()) {
						element2 = iterator.next ();
						if (((Comparable) element1).compareTo (element2) < 0) {
							throw new IllegalArgumentException (objectName + " 未排序!");
						}
					}
				} else {
					while (flag != 0 && iterator.hasNext ()) {
						element2 = iterator.next ();
						if (((Comparable) element1).compareTo (element2) > 0) {
							throw new IllegalArgumentException (objectName + " 未排序!");
						}
					}
				}
			}
		}
		return this;
	}

	public CollectionAssertTool contains (Object obj, int atIndex) {
		if (CollectionUtils.isEmpty (object)) {
			throw new IllegalArgumentException (objectName + " 不应为空!");
		} else {
			if (object.size () <= atIndex) {
				throw new IllegalArgumentException (objectName + " 的长度应大于或等于[" + atIndex + "]!");
			} else {
				Object value = object.toArray ()[atIndex];
				if (ObjectUtils.equals (obj, value)) {
					return this;
				}
			}
		}
		throw new IllegalArgumentException (objectName + " 应包含[" + obj + "]!");
	}

	public CollectionAssertTool containsOnlyOnce (Object obj) {
		if (CollectionUtils.isEmpty (object)) {
			throw new IllegalArgumentException (objectName + " 不应为空!");
		} else {
			int count = 0;
			Iterator iterator = object.iterator ();
			while (iterator.hasNext ()) {
				Object value = iterator.next ();
				if (ObjectUtils.equals (value, obj)) {
					if (++count > 1) {
						throw new IllegalArgumentException (objectName + " 只应包含[" + obj + "]一次!");
					}
				}
			}
			if (count != 1) {
				throw new IllegalArgumentException (objectName + " 应包含[" + obj + "]一次!");
			}
		}
		return this;
	}

	/**
	 * 断言集合的起始元素<br/>
	 * 只针对有序集合
	 *
	 * @param obj
	 *
	 * @return
	 */
	public CollectionAssertTool endsWith (Object obj) {
		if (CollectionUtils.isEmpty (object)) {
			throw new IllegalArgumentException (objectName + " 不应为空!应结束于[" + obj + "]");
		} else {
			Object element = null;
			int size = object.size ();
			if (object instanceof List) {
				element = ((List) object).get (size - 1);
			} else {
				element = object.toArray ()[size - 1];
			}
			if (ObjectUtils.equals (element, obj)) {
				return this;
			}
		}
		throw new IllegalArgumentException (objectName + " 应开束于[" + obj + "]!");
	}

	public CollectionAssertTool isSubsetOf (Collection col) {
		if (CollectionUtils.isEmpty (object)) {
			return this;
		} else if (CollectionUtils.isEmpty (col)) {
			throw new IllegalArgumentException ("传入的集合不应为空!");
		} else {
			Iterator siterator = object.iterator ();
			Object element = siterator.next ();

			Iterator piterator = col.iterator ();
			while (piterator.hasNext ()) {
				if (ObjectUtils.equals (element, piterator.next ())) {
					//相等
				} else {
					siterator = object.iterator ();
				}
				if (!siterator.hasNext ()) {
					return this;
				}
				element = siterator.next ();
			}
			throw new IllegalArgumentException (objectName + " 应该是传入集合的子集!");
		}
	}

	/**
	 * 断言集合的起始元素<br/>
	 * 只针对有序集合
	 *
	 * @param obj
	 *
	 * @return
	 */
	public CollectionAssertTool startsWith (Object obj) {
		if (CollectionUtils.isEmpty (object)) {
			throw new IllegalArgumentException (objectName + " 不应为空!应开始于[" + obj + "]");
		} else {
			Object element = object.iterator ().next ();
			if (ObjectUtils.equals (element, obj)) {
				return this;
			}
		}
		throw new IllegalArgumentException (objectName + " 应开始于[" + obj + "]!");
	}


}

package cn.pay.ebank.common.utils.asserttools;


import cn.pay.ebank.common.utils.StringUtils;

/**
 * Created by zhyang on 2015/2/16 0016.
 */
public abstract class AssertToolBase<T extends AssertToolBase, O> {
	protected O object;
	protected String objectName;

	protected AssertToolBase (O object, String objectName) {
		this.object = object;
		this.objectName = objectName;
	}


	/**
	 * @return
	 */
	public T isNull () {
		if (object != null) {
			throw new IllegalArgumentException (objectName + " 一定是空!");
		}
		return (T) this;
	}

	public T isNotNull () {
		if (object == null) {
			throw new IllegalArgumentException (objectName + " 不能为空!");
		}
		return (T) this;
	}

	public T isEqualTo (Object others) {
		if (object == null) {
			if (others != null) {
				throw new IllegalArgumentException (objectName + " 为null，和传入值不等");
			}
		} else {
			if (others == null) {
				throw new IllegalArgumentException (objectName + " 不为Null,所以和[Null]不等");
			} else if (!object.getClass ().equals (others.getClass ())) {
				throw new IllegalArgumentException (objectName + " 和比较的对象类型同，所以]不等");
			} else {
				if (object instanceof String) {
					if (!StringUtils.equals ((String) object, (String) others)) {
						throw new IllegalArgumentException (objectName + " 应等于[" + others + "]");
					}
				} else {
					if (!object.equals (others)) {
						throw new IllegalArgumentException (objectName + " 应等于[" + others + "]");
					}
				}
			}
		}
		return (T) this;
	}

	public T isNotEqualTo (Object others) {
		if (object == null) {
			if (others == null) {
				throw new IllegalArgumentException (objectName + " 为null，且比较值也为null");
			}
		} else {
			if (others == null) {
				return (T) this;
			} else if (!object.getClass ().equals (others.getClass ())) {
				return (T) this;
			} else {
				if (object instanceof String) {
					if (StringUtils.equals ((String) object, (String) others)) {
						throw new IllegalArgumentException (objectName + " 应不等于[" + others + "]");
					}
				} else {
					if (object.equals (others)) {
						throw new IllegalArgumentException (objectName + " 应不等于[" + others + "]");
					}
				}
			}
		}
		return (T) this;
	}

	public T isInstanceOf (Class<?> clazz) {
		if (!clazz.isInstance (object)) {
			throw new IllegalArgumentException (objectName + " 不应该是["+clazz+"]的实例!");
		}
		return (T) this;
	}
}

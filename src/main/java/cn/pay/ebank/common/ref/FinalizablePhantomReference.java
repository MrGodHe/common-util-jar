/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-04-21 22:48 创建
 *
 */
package cn.pay.ebank.common.ref;

import java.lang.ref.PhantomReference;

/**
 * 带有回调清除功能的虚引用对象。
 *
 * @param <T> 引用类型
 * @author zhyang@ebank.pay.cn
 */
public abstract class FinalizablePhantomReference<T> extends PhantomReference<T> implements FinalizableReference<T> {

	/**
	 * 创建一个引用给定对象的新的带有回调清除功能的虚引用。
	 *
	 * @param referent 新的带有回调清除功能的虚引用将引用的对象。
	 */
	protected FinalizablePhantomReference (T referent) {
		super (referent, FinalizableReferenceQueue.getInstance ());
	}

	/**
	 * 创建一个引用给定对象的新的带有回调清除功能的虚引用。
	 *
	 * @param referent                  新的带有回调清除功能的虚引用将引用的对象。
	 * @param finalizableReferenceQueue 该引用向其注册的可回调清理的队列。
	 */
	protected FinalizablePhantomReference (T referent, FinalizableReferenceQueue finalizableReferenceQueue) {
		super (referent, finalizableReferenceQueue == null ? FinalizableReferenceQueue.getInstance () : finalizableReferenceQueue);
	}
}

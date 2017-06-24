/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * qzhanbo@ebank.pay.cn 2014-04-15 15:55 创建
 *
 */
package cn.pay.ebank.common.utils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.pay.ebank.common.exception.CommonException;

import com.google.common.collect.Lists;

/**
 * 系统关闭钩子辅助工具类<br/>
 * <p/>
 * 系统关闭钩子是独立应用程序清理资源的理想位置,但是在容器中使用系统钩子可能不是合理地方.<br/>
 * <p/>
 * 比如tomcat,有可能出现在系统钩子中清理资源时,tomcat classloader已经关闭,这个时候我们不能正常的清理资源.<br/>
 * <p/>
 * 本工具类提供添加系统钩子的方法,并且会在容器关闭时(我们都用到了日志,可以在com.yjf.common.log.
 * LogbackConfigListener中调用shutdownAll)清理资源.<br/>
 * <p/>
 * ref: https://issues.apache.org/bugzilla/show_bug.cgi?id=56387
 *
 * @author qzhanbo@ebank.pay.cn
 */
public class ShutdownHooks {
	private static final Logger logger = LoggerFactory.getLogger(ShutdownHooks.class.getName());
	public static List<TaskWrapper> tasks = Lists.newArrayList();
	
	private static AtomicInteger index = new AtomicInteger();
	
	/**
	 * 添加关闭钩子
	 *
	 * @param runnable 钩子内容
	 * @param hookName 钩子名称
	 */
	public static void addShutdownHook(Runnable runnable, String hookName) {
		if (runnable != null) {
			TaskWrapper taskwrapper = new TaskWrapper(runnable, hookName);
			Thread thread = new Thread(taskwrapper, "YIJIShutdownHook" + index.incrementAndGet());
			taskwrapper.setShutdownhook(thread);
			tasks.add(taskwrapper);
			Runtime.getRuntime().addShutdownHook(thread);
		}
	}
	
	/**
	 * 执行所有shutdown hook,建议在容器关闭时执行.
	 * <p/>
	 * 避免容器关闭后,classloader关闭,容器加载类失败.
	 * https://issues.apache.org/bugzilla/show_bug.cgi?id=56387
	 */
	public static void shutdownAll() {
		for (TaskWrapper task : tasks) {
			task.run();
			//清除shutdownhook
			try {
				Runtime.getRuntime().removeShutdownHook(task.getShutdownhook());
			} catch (Exception e) {
				//ignore
			}
		}
		//如果是容器reload,需要清理资源,在shutdown时不需要清理资源
		tasks.clear();
	}
	
	private static class TaskWrapper implements Runnable {
		private Runnable runnable;
		private String hookName;
		private boolean isRunned = false;
		private Thread shutdownhook;
		
		public TaskWrapper(Runnable runnable, String hookName) {
			this.runnable = runnable;
			this.hookName = hookName;
		}
		
		@Override
		public void run() {
			synchronized (this) {
				if (!isRunned) {
					logger.info("[SHUTDOWNHOOK-{}]开始执行", hookName);
					isRunned = true;
					try {
						this.runnable.run();
						logger.info("[SHUTDOWNHOOK-{}]执行结束", hookName);
					} catch (Exception e) {
						logger.error("[SHUTDOWNHOOK-{}]执行失败", hookName, e);
					}
				}
			}
		}
		
		public Thread getShutdownhook() {
			return shutdownhook;
		}
		
		public void setShutdownhook(Thread shutdownhook) {
			this.shutdownhook = shutdownhook;
		}
	}
	
	static {
		//add dubbo shutdown hook
		try {
			final Class protocolConfig = Class.forName("com.alibaba.dubbo.config.ProtocolConfig");
			final Method method = protocolConfig.getMethod("destroyAll");
			addShutdownHook(new Runnable() {
				@Override
				public void run() {
					try {
						method.invoke(protocolConfig);
					} catch (Exception e) {
						throw new CommonException(e);
					}
				}
			}, "DubboShutdownHook");
		} catch (Exception e) {
			//ignore
		}
	}
}

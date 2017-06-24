/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * qzhanbo@ebank.pay.cn 2014-03-28 15:16 创建
 *
 */
package cn.pay.ebank.common.web.http;

import org.slf4j.LoggerFactory;

import cn.pay.ebank.common.exception.CommonException;
import cn.pay.ebank.common.log.LoggerImpl;

import com.jcraft.jsch.*;

/**
 * sftp 处理类
 * <p>
 * 如果不重用连接请使用:
 * {@link cn.pay.ebank.common.web.http.SFTP#download(String, int, String, String, String, String)}
 * </p>
 * <p>
 * 如果要重用连接,请按照下面的步骤:
 * <ul>
 * <li>
 * 1.创建Session
 * {@link cn.pay.ebank.common.web.http.SFTP#createSession(String, int, String, String)}
 * </li>
 * <li>
 * 2.创建channel
 * {@link cn.pay.ebank.common.web.http.SFTP#createChannel(com.jcraft.jsch.Session)}
 * </li>
 * <li>3.下载
 * {@link cn.pay.ebank.common.web.http.SFTP#download(com.jcraft.jsch.ChannelSftp, String, String)}
 * </li>
 * <li>
 * 4.关闭channel
 * {@link cn.pay.ebank.common.web.http.SFTP#closeChannel(com.jcraft.jsch.ChannelSftp)}
 * </li>
 * <li>
 * 5.关闭session
 * {@link cn.pay.ebank.common.web.http.SFTP#closeSession(com.jcraft.jsch.Session)}
 * </li>
 * </ul>
 * 可以重用session,每次创建channel执行下载任务,在程序关闭时关闭sesison
 * </p>
 * 
 * <br/>
 * <br/>
 * 
 * 如果要使用其他命令,可以使用
 * {@link cn.pay.ebank.common.web.http.SFTP#exeSftpCmd(String, int, String, String, cn.pay.ebank.common.web.http.SFTP.SFTPCommand)}
 * 来做其他操作. <br/>
 * <b>注意:</b> 此类依赖jsch:
 * 
 * <pre>
 * {@code
 *  <dependency>
 *      <groupId>com.jcraft</groupId>
 *      <artifactId>jsch</artifactId>
 *  </dependency>
 * }
 * </pre>
 * @author qzhanbo@ebank.pay.cn
 */
public class SFTP {
	/**
	 * 启用jsch日志
	 */
	private static Logger jschLogger = new JschLogger();
	
	/**
	 * 创建session
	 * 
	 * @param host 目标地址
	 * @param port 端口
	 * @param userName 用户名
	 * @param password 密码
	 * @return Session
	 */
	public static Session createSession(String host, int port, String userName, String password) {
		JSch jsch = new JSch();
		JSch.setLogger(jschLogger);
		try {
			Session session = jsch.getSession(userName, host, port);
			session.setUserInfo(new YJFUserInfo(password));
			session.connect();
			return session;
		} catch (JSchException e) {
			throw new CommonException(e);
		}
	}
	
	/**
	 * 创建ChannelSftp
	 * @param session Session
	 * @return ChannelSftp
	 */
	public static ChannelSftp createChannel(Session session) {
		try {
			Channel channel = session.openChannel("sftp");
			channel.connect();
			return (ChannelSftp) channel;
		} catch (JSchException e) {
			throw new CommonException(e);
		}
	}
	
	/**
	 * 从sftp服务器指定目录下载文件到本地目录
	 * @param channelSftp ChannelSftp对象
	 * @param src sftp上文件路径
	 * @param dst 本地目录
	 */
	public static void download(ChannelSftp channelSftp, String src, String dst) {
		try {
			channelSftp.get(src, dst, null, ChannelSftp.OVERWRITE);
		} catch (SftpException e) {
			throw new CommonException(e);
		}
	}
	
	/**
	 * 从sftp服务器指定目录下载文件到本地目录
	 * @param host 目标地址
	 * @param port 端口
	 * @param userName 用户名
	 * @param password 密码
	 * @param src sftp上文件路径
	 * @param dst 本地目录
	 */
	public static void download(String host, int port, String userName, String password,
								final String src, final String dst) {
		exeSftpCmd(host, port, userName, password, new SFTPCommand() {
			@Override
			public void exe(ChannelSftp channelSftp) {
				try {
					channelSftp.get(src, dst, null, ChannelSftp.OVERWRITE);
				} catch (SftpException e) {
					throw new CommonException(e);
				}
			}
		});
	}
	
	/**
	 * 连接到目标服务器执行command
	 * @param host 目标地址
	 * @param port 端口
	 * @param userName 用户名
	 * @param password 密码
	 * @param command 操作命令
	 * 
	 */
	public static void exeSftpCmd(String host, int port, String userName, String password,
									SFTPCommand command) {
		if (command != null) {
			Session session = null;
			ChannelSftp channelSftp = null;
			try {
				session = createSession(host, port, userName, password);
				channelSftp = createChannel(session);
				if (channelSftp != null) {
					command.exe(channelSftp);
				}
			} finally {
				try {
					closeChannel(channelSftp);
				} finally {
					closeSession(session);
				}
			}
		}
	}
	
	/**
	 * 关闭channel
	 * @param channelSftp ChannelSftp
	 */
	public static void closeChannel(ChannelSftp channelSftp) {
		if (channelSftp != null) {
			channelSftp.exit();
		}
	}
	
	/**
	 * 关闭session
	 * @param session Session
	 */
	public static void closeSession(Session session) {
		if (session != null) {
			session.disconnect();
		}
	}
	
	/**
	 * 处理密码输入
	 */
	private static class YJFUserInfo implements UserInfo {
		private String password;
		
		public YJFUserInfo(String password) {
			this.password = password;
		}
		
		@Override
		public String getPassphrase() {
			return null;
		}
		
		@Override
		public String getPassword() {
			return this.password;
		}
		
		@Override
		public boolean promptPassword(String s) {
			return true;
		}
		
		@Override
		public boolean promptPassphrase(String s) {
			return true;
		}
		
		@Override
		public boolean promptYesNo(String s) {
			return true;
		}
		
		@Override
		public void showMessage(String s) {
			
		}
	}
	
	/**
	 * JSCH 日志记录器
	 */
	private static class JschLogger implements com.jcraft.jsch.Logger {
		
		private static final org.slf4j.Logger logger = LoggerFactory.getLogger(JschLogger.class);
		static {
			if (logger instanceof LoggerImpl) {
				((LoggerImpl) logger).setFqcn(JschLogger.class.getName());
			}
		}
		
		@Override
		public boolean isEnabled(int level) {
			return true;
		}
		
		@Override
		public void log(int level, String message) {
			if (level == Logger.INFO) {
				logger.info(message);
			} else if (level == Logger.DEBUG) {
				logger.debug(message);
			} else if (level == Logger.WARN) {
				logger.warn(message);
			} else if (level == Logger.ERROR) {
				logger.error(message);
			} else if (level == Logger.FATAL) {
				logger.error(message);
			}
		}
	}
	
	/**
	 * sftp命令
	 */
	public static interface SFTPCommand {
		void exe(ChannelSftp channelSftp);
	}
	
}

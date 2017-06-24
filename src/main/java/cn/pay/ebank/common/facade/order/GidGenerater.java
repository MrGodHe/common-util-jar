/*
 * www.ebank.pay.cn Inc.
 * Copyright (c) 2014 All Rights Reserved
 */

/*
 * 修订记录:
 * zhyang@ebank.pay.cn 2015-05-13 20:09 创建
 *
 */
package cn.pay.ebank.common.facade.order;

import cn.pay.ebank.common.utils.DateUtils;
import cn.pay.ebank.common.utils.IPUtils;
import cn.pay.ebank.common.utils.MD5Utils;
import cn.pay.ebank.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Random;

/**
 * 
 * 业务流水id生成器，生成的id为35位
 * <p/>
 * <p>
 * id=系统ID[8位]+serverId[5位]+时间[20位]+分区标识[2位] 共35位
 * <p/>
 * <p>
 * serverId：由mac地址、ip、服务端口共同生成。ID在初始化时,会调用cs项目提供的server注册服务,通过传入这些参数,cs会返回一个唯一标识
 * . 使用这三个参数来定位一个jvm实例,又因为mac有可能被修改,ip在多个局域网会出现重复,所以选择了同时用mac和ip.
 * 端口采用jmx来获取tomcat的端口.如果有必要,以后会加上JBOSS支持
 * </p>
 * <p>
 * 时间：由毫秒数*1000+counter生成，支持单jvm进程1ms内顺利的生成1000个唯一的id.如果1ms内的请求操过1000,也会保证ID的唯一性
 * ,只是生成的时间会比当前时间快.
 * </p>
 * <p>
 * 分区标识：长度为2,用于以后对数据分库分表
 * </p>
 * <p/>
 * 此id生成器的生成效率比java自带的UUID.randomUUID()效率更高,而且能保证唯一.java自带的uuid生成器,由于是采用type
 * 4(随机的)模式生成的，不能保证绝对唯一
 * <p>
 * <h3>Usage Examples</h3>
 * <p/>
 * 
 * <pre class="code">
 * {@code
 * String bussinessCode = "ebank00";
 * String id = GidGenerater.newID(bussinessCode,"12");
 * logger.info("生成唯一的id:{}", id);
 * logger.info("此id的业务编码为：{}", GidGenerater.getBussinessCode(id));
 * logger.info("此id的时间为：{}", GidGenerater.getTime(id));
 * logger.info("此id的serverId为：{}", GidGenerater.getServerId(id));
 * logger.info("此id的sharding为：{}", GidGenerater.getSharding(id));
 * }
 * </pre>
 * </p>
 * <h3>注意:</h3>
 * 时间跳变(比如运维人员把时间向前调整了)可能会生成重复的id,这个问题基本上没有办法避免,基于时间的id生成器都会遇到这样的问题.
 * 
 * 
 * @author zhyang@ebank.pay.cn
 */
public class GidGenerater {
	
	private static final Logger logger = LoggerFactory.getLogger(GidGenerater.class.getName());
	
	/**
	 * 补全字符串时所用字符
	 */
	private static final char PADDING_CHAR = '0';
	private static final String DATE_FORMATER = "yyyyMMddHHmmssSSS";
	/**
	 * 默认系统编码
	 */
	private static final String DEFUALT_SYSTEM_CODE = "GID";
	/**
	 * GID 长度
	 */
	private static final int GID_LEN = 35;
	/**
	 * 默认物理地址，如果获取mac失败，用此物理地址
	 */
	private static final String DEFAULT_MAC = "00:00:00:00:00:00";
	private static final char[] INDEX_CHARS = new char[] { '0', '1', '2', '3', '4', '5', '6', '7',
															'8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
															'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
															'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
															'W', 'X', 'Y', 'Z' };
	private static Random rand = new Random();
	
	private static final String $SERVER_ID = getServerID(5);
	
	private static long index = 1;
	
	public static final String DEFAULT_DATABASE_INDEX = "00";
	
	public static void main(String[] args) {
		System.out.println(newGid());
		System.out.println(newGid());
		System.out.println(newGid());
		System.out.println(newGid().length());
	}
	
	/**
	 * 生成GID
	 * @return
	 */
	public static String newGid() {
		return newGid(DEFUALT_SYSTEM_CODE);
	}
	
	public static String newGid(String systemCode) {
		return newGid(systemCode, DEFAULT_DATABASE_INDEX);
	}
	
	/**
	 * 
	 * @param systemCode
	 * @param dbIndex
	 * @return
	 */
	public static String newGid(String systemCode, String dbIndex) {
		Date now = DateUtils.NOW();
		String index = (systemCode + "000").substring(0, 3) + DateUtils.shortDate(now) + "0"
						+ StringUtils.right("00" + dbIndex, 2)
						+ StringUtils.right(now.getTime() + $SERVER_ID, 9) + getIndex()
						+ getRandomStr(10);
		return index;
	}
	
	private static boolean checkSign(String index) {
		String sign = Integer.toString(index.substring(0, 34).hashCode(), 32);
		return StringUtils.right(index, 1).toUpperCase()
			.equalsIgnoreCase(StringUtils.right(sign, 1));
	}
	
	/**
	 * 增加校验位
	 * @param index
	 * @return
	 */
	private static String sign(String index) {
		String sign = Integer.toString(index.hashCode(), 32);
		return index + StringUtils.right(sign, 2).toUpperCase();
	}
	
	/**
	 * 序号
	 * @return
	 */
	private static String getIndex() {
		String idx;
		synchronized ($SERVER_ID) {
			if (index >= 1023) {
				index = 0;
			}
			idx = Long.toString(index++, 32);
		}
		if (idx.length() == 1) {
			return "0" + idx.toUpperCase();
		} else {
			return idx.toUpperCase();
		}
	}
	
	/**
	 * 填充字符串
	 * 
	 * @param str 待填充字符串
	 * @param len 填充后的位数
	 * @param padding 填充字符
	 */
	private static String padding(String str, int len, char padding) {
		if (str.length() < len) {
			StringBuilder sb = new StringBuilder(len);
			int toPadLen = len - str.length();
			for (int i = 1; i <= toPadLen; i++) {
				sb.append(padding);
			}
			sb.append(str);
			return sb.toString();
		} else {
			return str;
		}
	}
	
	private static String getServerID(int len) {
		//随机生成长度为5的serverId
		return StringUtils.right(MD5Utils.encodeByMD5(getMAC() + rand.nextInt(100000) + getIp())
			.substring(5, 12).toUpperCase(), len);
	}
	
	private static String getMAC() {
		String mac;
		try {
			mac = IPUtils.getMACAddress();
			mac = mac.replace("-", ":");
		} catch (Exception e) {
			mac = DEFAULT_MAC;
			//logger.info("获取mac失败，使用默认物理地址:{}", mac);
		}
		return mac;
	}
	
	private static String getIp() {
		return IPUtils.getFirstNoLoopbackIPV4Address();
	}
	
	/**
	 * 随机字符串
	 * @param len
	 * @return
	 */
	private static String getRandomStr(int len) {
		StringBuilder builder = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			builder.append(INDEX_CHARS[rand.nextInt(36)]);
		}
		return builder.toString();
	}
	
}

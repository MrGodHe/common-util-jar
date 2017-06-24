package cn.pay.ebank.common.log;

import org.slf4j.Marker;
import org.slf4j.spi.LocationAwareLogger;

/**
 * logger实现类
 * 
 * @Filename LoggerImpl.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author bohr
 * 
 * @Email qzhanbo@ebank.pay.cn
 * 
 * @History <li>Author: bohr.qiu</li> <li>Date: 2013-5-3</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 * 
 */
@SuppressWarnings("static-access")
public class LoggerImpl implements Logger {
	private static final String DELIM_STR = "{}";
	private static String FQCN = LoggerImpl.class.getName();
	private LocationAwareLogger log;
	
	/**
	 * 提供扩展机制
	 */
	private String fqcn;
	
	public LoggerImpl(org.slf4j.Logger logger) {
		if (!(logger instanceof LocationAwareLogger)) {
			throw new UnsupportedOperationException(logger + " 不支持日志增强");
		}
		this.log = (LocationAwareLogger) logger;
	}
	
	public void setFqcn(String fqcn) {
		this.fqcn = fqcn;
	}
	
	public String getFqcn() {
		if (fqcn != null) {
			return fqcn;
		} else {
			return FQCN;
		}
	}
	
	@Override
	public String getName() {
		return log.getName();
	}
	
	@Override
	public boolean isTraceEnabled() {
		return log.isTraceEnabled();
	}
	
	@Override
	public void trace(String msg) {
		log.log(null, getFqcn(), LocationAwareLogger.TRACE_INT, msg, null, null);
	}
	
	@Override
	public void trace(String format, Object arg) {
		log.log(null, getFqcn(), LocationAwareLogger.TRACE_INT, format, new Object[] { arg }, null);
	}
	
	@Override
	public void trace(String format, Object arg1, Object arg2) {
		log.log(null, getFqcn(), LocationAwareLogger.TRACE_INT, format, new Object[] { arg1, arg2 }, null);
	}
	
	@Override
	public void trace(String msg, Throwable t) {
		log.log(null, getFqcn(), LocationAwareLogger.TRACE_INT, msg, null, t);
	}
	
	@Override
	public boolean isTraceEnabled(Marker marker) {
		return log.isTraceEnabled(marker);
	}
	
	@Override
	public void trace(Marker marker, String msg) {
		log.log(marker, getFqcn(), LocationAwareLogger.TRACE_INT, msg, null, null);
	}
	
	@Override
	public void trace(Marker marker, String format, Object arg) {
		log.log(marker, getFqcn(), LocationAwareLogger.TRACE_INT, format, new Object[] { arg }, null);
	}
	
	@Override
	public void trace(Marker marker, String format, Object arg1, Object arg2) {
		log.log(marker, getFqcn(), LocationAwareLogger.TRACE_INT, format, new Object[] { arg1, arg2 }, null);
	}
	
	@Override
	public void trace(Marker marker, String format, Object[] argArray) {
		log.log(marker, getFqcn(), LocationAwareLogger.TRACE_INT, format, argArray, null);
	}
	
	@Override
	public void trace(Marker marker, String msg, Throwable t) {
		log.log(marker, getFqcn(), LocationAwareLogger.TRACE_INT, msg, null, t);
	}
	
	@Override
	public boolean isDebugEnabled() {
		return log.isDebugEnabled();
	}
	
	@Override
	public void debug(String msg) {
		log.log(null, getFqcn(), LocationAwareLogger.DEBUG_INT, msg, null, null);
	}
	
	@Override
	public void debug(String format, Object arg) {
		log.log(null, getFqcn(), LocationAwareLogger.DEBUG_INT, format, new Object[] { arg }, null);
	}
	
	@Override
	public void debug(String format, Object arg1, Object arg2) {
		log.log(null, getFqcn(), LocationAwareLogger.DEBUG_INT, format, new Object[] { arg1, arg2 }, null);
	}
	
	@Override
	public void debug(String msg, Throwable t) {
		log.log(null, getFqcn(), LocationAwareLogger.DEBUG_INT, msg, null, t);
	}
	
	@Override
	public boolean isDebugEnabled(Marker marker) {
		return log.isDebugEnabled(marker);
	}
	
	@Override
	public void debug(Marker marker, String msg) {
		log.log(marker, getFqcn(), LocationAwareLogger.DEBUG_INT, msg, null, null);
	}
	
	@Override
	public void debug(Marker marker, String format, Object arg) {
		log.log(marker, getFqcn(), LocationAwareLogger.DEBUG_INT, format, new Object[] { arg }, null);
	}
	
	@Override
	public void debug(Marker marker, String format, Object arg1, Object arg2) {
		log.log(marker, getFqcn(), LocationAwareLogger.DEBUG_INT, format, new Object[] { arg1, arg2 }, null);
	}
	
	@Override
	public void debug(Marker marker, String format, Object[] argArray) {
		log.log(marker, getFqcn(), LocationAwareLogger.DEBUG_INT, format, argArray, null);
	}
	
	@Override
	public void debug(Marker marker, String msg, Throwable t) {
		log.log(marker, getFqcn(), LocationAwareLogger.DEBUG_INT, msg, null, t);
	}
	
	@Override
	public boolean isInfoEnabled() {
		return log.isInfoEnabled();
	}
	
	@Override
	public void info(String msg) {
		log.log(null, getFqcn(), LocationAwareLogger.INFO_INT, msg, null, null);
	}
	
	@Override
	public void info(String format, Object arg) {
		log.log(null, getFqcn(), LocationAwareLogger.INFO_INT, format, new Object[] { arg }, null);
	}
	
	@Override
	public void info(String format, Object arg1, Object arg2) {
		log.log(null, getFqcn(), LocationAwareLogger.INFO_INT, format, new Object[] { arg1, arg2 }, null);
	}
	
	@Override
	public void info(String msg, Throwable t) {
		log.log(null, getFqcn(), LocationAwareLogger.INFO_INT, msg, null, t);
	}
	
	@Override
	public boolean isInfoEnabled(Marker marker) {
		return log.isInfoEnabled(marker);
	}
	
	@Override
	public void info(Marker marker, String msg) {
		log.log(marker, getFqcn(), LocationAwareLogger.INFO_INT, msg, null, null);
	}
	
	@Override
	public void info(Marker marker, String format, Object arg) {
		log.log(marker, getFqcn(), LocationAwareLogger.INFO_INT, format, new Object[] { arg }, null);
	}
	
	@Override
	public void info(Marker marker, String format, Object arg1, Object arg2) {
		log.log(marker, getFqcn(), LocationAwareLogger.INFO_INT, format, new Object[] { arg1, arg2 }, null);
	}
	
	@Override
	public void info(Marker marker, String format, Object[] argArray) {
		log.log(marker, getFqcn(), LocationAwareLogger.INFO_INT, format, argArray, null);
	}
	
	@Override
	public void info(Marker marker, String msg, Throwable t) {
		log.log(marker, getFqcn(), LocationAwareLogger.INFO_INT, msg, null, t);
	}
	
	@Override
	public boolean isWarnEnabled() {
		return log.isWarnEnabled();
	}
	
	@Override
	public void warn(String msg) {
		log.log(null, getFqcn(), LocationAwareLogger.WARN_INT, msg, null, null);
	}
	
	@Override
	public void warn(String format, Object arg) {
		log.log(null, getFqcn(), LocationAwareLogger.WARN_INT, format, new Object[] { arg }, null);
	}
	
	@Override
	public void warn(String format, Object arg1, Object arg2) {
		log.log(null, getFqcn(), LocationAwareLogger.WARN_INT, format, new Object[] { arg1, arg2 }, null);
	}
	
	@Override
	public void warn(String msg, Throwable t) {
		log.log(null, getFqcn(), LocationAwareLogger.WARN_INT, msg, null, t);
	}
	
	@Override
	public boolean isWarnEnabled(Marker marker) {
		return log.isWarnEnabled();
	}
	
	@Override
	public void warn(Marker marker, String msg) {
		log.log(marker, getFqcn(), LocationAwareLogger.WARN_INT, msg, null, null);
	}
	
	@Override
	public void warn(Marker marker, String format, Object arg) {
		log.log(marker, getFqcn(), LocationAwareLogger.WARN_INT, format, new Object[] { arg }, null);
	}
	
	@Override
	public void warn(Marker marker, String format, Object arg1, Object arg2) {
		log.log(marker, getFqcn(), LocationAwareLogger.WARN_INT, format, new Object[] { arg1, arg2 }, null);
	}
	
	@Override
	public void warn(Marker marker, String format, Object[] argArray) {
		log.log(marker, getFqcn(), LocationAwareLogger.WARN_INT, format, argArray, null);
	}
	
	@Override
	public void warn(Marker marker, String msg, Throwable t) {
		log.log(marker, getFqcn(), LocationAwareLogger.WARN_INT, msg, null, t);
	}
	
	@Override
	public boolean isErrorEnabled() {
		return log.isErrorEnabled();
	}
	
	@Override
	public void error(String msg) {
		log.log(null, getFqcn(), LocationAwareLogger.ERROR_INT, msg, null, null);
	}
	
	@Override
	public void error(String format, Object arg) {
		log.log(null, getFqcn(), LocationAwareLogger.ERROR_INT, format, new Object[] { arg }, null);
	}
	
	@Override
	public void error(String format, Object arg1, Object arg2) {
		log.log(null, getFqcn(), LocationAwareLogger.ERROR_INT, format, new Object[] { arg1, arg2 }, null);
	}
	
	@Override
	public void error(String msg, Throwable t) {
		log.log(null, getFqcn(), LocationAwareLogger.ERROR_INT, msg, null, t);
	}
	
	@Override
	public boolean isErrorEnabled(Marker marker) {
		return log.isErrorEnabled(marker);
	}
	
	@Override
	public void error(Marker marker, String msg) {
		log.log(marker, getFqcn(), LocationAwareLogger.ERROR_INT, msg, null, null);
	}
	
	@Override
	public void error(Marker marker, String format, Object arg) {
		log.log(marker, getFqcn(), LocationAwareLogger.ERROR_INT, format, new Object[] { arg }, null);
	}
	
	@Override
	public void error(Marker marker, String format, Object arg1, Object arg2) {
		log.log(marker, getFqcn(), LocationAwareLogger.ERROR_INT, format, new Object[] { arg1, arg2 }, null);
	}
	
	@Override
	public void error(Marker marker, String format, Object[] argArray) {
		log.log(marker, getFqcn(), LocationAwareLogger.ERROR_INT, format, argArray, null);
	}
	
	@Override
	public void error(Marker marker, String msg, Throwable t) {
		log.log(marker, getFqcn(), LocationAwareLogger.ERROR_INT, msg, null, null);
	}
	
	@Override
	public void trace(String format, Object... arg) {
		logMultiArg(format, LocationAwareLogger.TRACE_INT, arg);
	}
	
	@Override
	public void debug(String format, Object... arg) {
		logMultiArg(format, LocationAwareLogger.DEBUG_INT, arg);
	}
	
	@Override
	public void info(String format, Object... arg) {
		logMultiArg(format, LocationAwareLogger.INFO_INT, arg);
	}
	
	@Override
	public void warn(String format, Object... arg) {
		logMultiArg(format, LocationAwareLogger.WARN_INT, arg);
	}
	
	@Override
	public void error(String format, Object... arg) {
		logMultiArg(format, LocationAwareLogger.ERROR_INT, arg);
	}
	
	private static String formatWithThrow(String str, Object[] args) {
		if (str == null) {
			return null;
		}
		String string = str;
		int dIndex = string.indexOf(DELIM_STR);
		if (dIndex == -1) {
			return string;
		}
		int beginIndex = 0;
		StringBuilder sb = new StringBuilder(100);
		int index = 0;
		//循环替换{},不用正则表达式，感觉正则表达式较慢
		for (int i = index; i < args.length - 1; i++) {
			sb.append(string.substring(beginIndex, dIndex));
			sb.append(args[i]);
			beginIndex = dIndex + DELIM_STR.length();
			dIndex = string.indexOf(DELIM_STR, beginIndex);
			if (dIndex == -1) {
				break;
			}
		}
		sb.append(string.substring(beginIndex));
		return sb.toString();
	}
	
	private void logMultiArg(String format, int level, Object... arg) {
		if (arg == null) {
			log.log(null, getFqcn(), level, format, null, null);
		} else {
			if (arg[arg.length - 1] instanceof Throwable) {
				String msg = formatWithThrow(format, arg);
				log.log(null, getFqcn(), level, msg, null, (Throwable) arg[arg.length - 1]);
			} else {
				log.log(null, getFqcn(), level, format, arg, null);
			}
		}
	}
}

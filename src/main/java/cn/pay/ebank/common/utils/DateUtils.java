package cn.pay.ebank.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhyang on 2015/2/15 0015.
 */
public abstract class DateUtils {
	/**
	 * 设置默认的时间格式 yyyy-MM-dd HH:mm:ss
	 */
	public static final String DEFAULT_DATE_FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";
	public static final String SIMPLE_DATE_FORMAT_STRING = "yyyy-MM-dd";
	public static final String WEEK_FORMAT_STRING = "EEEE";
	/**
	 * 年月日(无下划线) yyyyMMdd
	 */
	public static final String dtShort = "yyyyMMdd";
	/**
	 * 完整时间 yyyy-MM-dd HH:mm:ss
	 */
	public static final String simple = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 年月日 yyyy-MM-dd
	 */
	public static final String dtSimple = "yyyy-MM-dd";
	/**
	 * 年月日时分秒(无下划线) yyyyMMddHHmmss
	 */
	public static final String dtLong = "yyyyMMddHHmmss";
	/**
	 * 时间原点
	 *
	 * @return
	 */
	public static Date EPOCH () {
		Calendar date = Calendar.getInstance ();
		date.clear ();
		//1970年1月1日
		return date.getTime ();
	}

	/**
	 * 当前时间
	 *
	 * @return
	 */
	public static Date NOW () {
		return new Date ();
	}

	/**
	 * 格式化当前日期
	 *
	 * @return 将当前日期格式化为  "yyyy-MM-dd HH:mm:ss"
	 */
	public static String formatDate () {
		return formatDate (NOW ());
	}

	/**
	 * 格式化指定日期
	 *
	 * @param date 指定日期
	 *
	 * @return 将指定日期格式化为  "yyyy-MM-dd HH:mm:ss"
	 */
	public static String formatDate (Date date) {
		return formatDate (date, DEFAULT_DATE_FORMAT_STRING);
	}

	/**
	 * 将当前日期转换为指定格式的字符串
	 *
	 * @param pattern
	 *
	 * @return
	 */
	public static String formatDate (String pattern) {
		return formatDate (NOW (), pattern);
	}

	/**
	 * 将给定时间按照 指定的格式转换为字符串
	 *
	 * @param date
	 * @param pattern
	 *
	 * @return
	 */
	public static String formatDate (Date date, String pattern) {
		if (date == null) {
			return null;
		}
		return getFormat(pattern).format (date);
	}

	public static Date parseDate (String date, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat (pattern);
		try {
			return dateFormat.parse (date);
		} catch (Exception e) {
			return NOW ();
		}
	}

	public static String getDateString (Date date, DateFormat dateFormat) {
		if (date == null || dateFormat == null) {
			return null;
		}
		return dateFormat.format (date);
	}

	/**
	 * 获取星期名，如“星期一”、“星期二”
	 *
	 * @param date
	 *
	 * @return
	 */
	public static final String getWeekDay (Date date) {
		return getFormat (WEEK_FORMAT_STRING).format (date);
	}

	/**
	 * 获取格式
	 *
	 * @param format
	 *
	 * @return
	 */
	public static final DateFormat getFormat (String format) {
		return new SimpleDateFormat (format);
	}

	private static Date getDateCalendar (Date d, int field, int value, Regulation type) {
		Calendar now = Calendar.getInstance ();
		now.setTime (d);
		if (type == Regulation.AFTER) {
			now.set (field, now.get (field) + value);
		} else {
			now.set (field, now.get (field) - value);
		}
		return now.getTime ();
	}

	/**
	 * @return int
	 * @name getMouth
	 * @author zhyang
	 * @date 2011-1-14
	 * @description 得到当前的月份
	 */
	public static int getMouth () {
		return getMouth (NOW ());
	}

	/**
	 * @param date
	 *
	 * @return int
	 * @name getMouth
	 * @author zhyang
	 * @date 2011-1-14
	 * @description 得到指定日期的月份
	 */
	public static int getMouth (Date date) {
		Calendar cal = Calendar.getInstance ();
		cal.setTime (date);
		return cal.get (Calendar.MONTH);
	}

	public static int getSeason () {
		return getDate (NOW ());
	}

	/**
	 * 1 第一季度 2 第二季度 3 第三季度 4 第四季度
	 *
	 * @param date
	 *
	 * @return
	 */
	public static int getSeason (Date date) {

		int season = 0;

		Calendar c = Calendar.getInstance ();
		c.setTime (date);
		int month = c.get (Calendar.MONTH);
		switch (month) {
			case Calendar.JANUARY:
			case Calendar.FEBRUARY:
			case Calendar.MARCH:
				season = 1;
				break;
			case Calendar.APRIL:
			case Calendar.MAY:
			case Calendar.JUNE:
				season = 2;
				break;
			case Calendar.JULY:
			case Calendar.AUGUST:
			case Calendar.SEPTEMBER:
				season = 3;
				break;
			case Calendar.OCTOBER:
			case Calendar.NOVEMBER:
			case Calendar.DECEMBER:
				season = 4;
				break;
			default:
				break;
		}
		return season;
	}

	/**
	 * @return int
	 * @name getYear
	 * @author zhyang
	 * @date 2011-1-14
	 * @description 得到当前的年份
	 */
	public static int getYear () {
		return getYear (NOW ());
	}

	/**
	 * @param date
	 *
	 * @return int
	 * @name getYear
	 * @author zhyang
	 * @date 2011-1-14
	 * @description 得到指定时间的年分数
	 */
	public static int getYear (Date date) {
		Calendar cal = Calendar.getInstance ();
		cal.setTime (date);
		return cal.get (Calendar.YEAR);
	}

	/**
	 * @return int
	 * @name getDate
	 * @author zhyang
	 * @date 2011-1-14
	 * @description 得到当前日期的天数
	 */
	public static int getDate () {
		return getDate (NOW ());
	}

	/**
	 * @param date
	 *
	 * @return int
	 * @name getDate
	 * @author zhyang
	 * @date 2011-1-14
	 * @description
	 */
	public static int getDate (Date date) {
		Calendar cal = Calendar.getInstance ();
		cal.setTime (date);
		return cal.get (Calendar.DATE);
	}

	/**
	 * @return int
	 * @name getHour
	 * @author zhyang
	 * @date 2011-1-14
	 * @description 得到当前时间小时数
	 */
	public static int getHour () {
		return getHour (NOW ());
	}

	/**
	 * @param date
	 *
	 * @return int
	 * @name getHour
	 * @author zhyang
	 * @date 2011-1-14
	 * @description 得到指定日期的小时数
	 */
	public static int getHour (Date date) {
		Calendar cal = Calendar.getInstance ();
		cal.setTime (date);
		return cal.get (Calendar.HOUR_OF_DAY);
	}

	/**
	 * @return int
	 * @name getMinutes
	 * @author zhyang
	 * @date 2011-1-14
	 * @description 得到当前时间的分钟数
	 */
	public static int getMinutes () {
		return getMinutes (NOW ());
	}

	/**
	 * @param date
	 *
	 * @return int
	 * @name getMinutes
	 * @author zhyang
	 * @date 2011-1-14
	 * @description 得到指定时间的分钟数
	 */
	public static int getMinutes (Date date) {
		Calendar cal = Calendar.getInstance ();
		cal.setTime (date);
		return cal.get (Calendar.MINUTE);
	}

	/**
	 * @return int
	 * @name getSeconds
	 * @author zhyang
	 * @date 2011-1-14
	 * @description 得到当前的秒数
	 */
	public static int getSeconds () {
		return getSeconds (NOW ());
	}

	/**
	 * @param date 计算的时间
	 *
	 * @return int
	 * @name getSeconds
	 * @author zhyang
	 * @date 2011-1-14
	 * @description 得到指定时间的秒数
	 */
	public static int getSeconds (Date date) {
		Calendar cal = Calendar.getInstance ();
		cal.setTime (date);
		return cal.get (Calendar.SECOND);
	}

	/**
	 * @return int
	 * @name getDay
	 * @author zhyang
	 * @date 2011-1-14
	 * @description 得到就今天是星期几
	 */
	public static int getDay () {
		return getDay (NOW ());
	}

	/**
	 * 判断日期为星期几,0为星期六,依此类推
	 *
	 * @param date
	 *
	 * @return int
	 * @name getDay
	 * @author zhyang
	 * @date 2011-1-14
	 * @description
	 */
	public static int getDay (Date date) {
		// 首先定义一个calendar，必须使用getInstance()进行实例化
		Calendar aCalendar = Calendar.getInstance ();
		// 里面野可以直接插入date类型
		aCalendar.setTime (date);
		// 计算此日期是一周中的哪一天
		int x = aCalendar.get (Calendar.DAY_OF_WEEK);
		return x - 1;
	}

	/**
	 * @return int
	 * @name getLastDateOfMouth
	 * @author zhyang
	 * @date 2011-1-14
	 * @description 得到本月最后一天
	 */
	public static int getLastDateOfMouth () {
		return getLastTimeOfMouth (NOW ());
	}

	/**
	 * @return
	 * @name getDayOfYear
	 * @author zhyang
	 * @date 2011-11-27
	 * @description 返回今天是一年的第几天
	 */
	public static int getDayOfYear () {
		return getDayOfYear (NOW ());
	}

	/**
	 * @param date
	 *
	 * @return
	 * @name getDayOfYear
	 * @author zhyang
	 * @date 2011-11-27
	 * @description 返回给定的日期是一年的第几天
	 */
	public static int getDayOfYear (Date date) {
		Calendar cal = Calendar.getInstance ();
		cal.setTime (date);
		return cal.get (Calendar.DAY_OF_YEAR);
	}

	/**
	 * @return
	 * @name getWeekOfYear
	 * @author zhyang
	 * @date 2011-11-27
	 * @description 返回今天是今年的第几周
	 */
	public static int getWeekOfYear () {
		return getWeekOfYear (NOW ());
	}

	/**
	 * @param date
	 *
	 * @return
	 * @name getWeekOfYear
	 * @author zhyang
	 * @date 2011-11-27
	 * @description 返回给定日期是当年的第几周
	 */
	public static int getWeekOfYear (Date date) {
		Calendar cal = Calendar.getInstance ();
		cal.setTime (date);
		return cal.get (Calendar.WEEK_OF_YEAR);
	}

	/**
	 * @param date
	 *
	 * @return int
	 * @name getLastTimeOfMouth
	 * @author zhyang
	 * @date 2011-1-14
	 * @description 得到当月的最后一天
	 */
	public static int getLastTimeOfMouth (Date date) {
		Calendar c = Calendar.getInstance ();
		c.set (Calendar.YEAR, getYear (date));
		c.set (Calendar.MONTH, getMouth (date));

		return c.getActualMaximum (Calendar.DAY_OF_MONTH);
	}

	/**
	 * @param s
	 *
	 * @return String
	 * @name formatDateString
	 * @author zhyang
	 * @date 2011-1-14
	 * @description 将时间字符串格式化为yyyy-MM-dd HH:mm:ss的形式
	 */
	public static String formatDateString (String s) {
		String strDateFomater = getDateFomater (s);
		if (null == strDateFomater) {
			return null;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd-HH-mm-ss");
			try {
				Date d = sdf.parse (strDateFomater);
				return formatDate (d);
			} catch (ParseException e) {
			}
			return null;
		}
	}

	/**
	 * @param s
	 *
	 * @return String
	 * @name getDateFomater
	 * @author zhyang
	 * @date 2011-1-14
	 * @description 格式化时间字符串，使他为标准形式yyyy-MM-dd HH:mm:ss
	 */
	private static String getDateFomater (String s) {
		if (s == null || s.equals ("")) {
			return null;
		}
		s = s.replaceAll ("[^0-9]", "-");
		s = s.replaceAll ("[-]+", "-");
		s = s.replaceAll ("[-]*$", "");

		String strSpliter = "-";
		String eL;
		Pattern p;
		Matcher m;

		eL = "((?:0?[1-9]|1[0-2])" + strSpliter + "(?:0?[1-9]|[12][0-9]|3[01]))(" + strSpliter + "(((0?[0-9])|(1[0-9])|(2[0-3]))" + strSpliter + "([0-5]?[0-9])((" + strSpliter + "([0-5]?[0-9]))?)))?$";
		p = Pattern.compile (eL);
		m = p.matcher (s.trim ());
		if (m.matches ()) {
			s = getYear () + "-" + s;
		}

		eL = "(?:[0-9]{1,4}(?<!^0?0?0?0))" + strSpliter + "(?:0?[1-9]|1[0-2])" + strSpliter + "(?:0?[1-9]|1[0-9]|2[0-8]|(?:(?<=-(?:0?[13578]|1[02])-)(?:29|3[01]))|(?:(?<=-(?:0?[469]|11)-)(?:29|30))|(?:(?<=(?:(?:[0-9]{0,2}(?!0?0)(?:[02468]?(?<![13579])[048]|[13579][26]))|(?:(?:[02468]?[048]|[13579][26])00))-0?2-)(?:29)))$";
		p = Pattern.compile (eL);
		m = p.matcher (s.trim ());
		if (m.matches ()) {
			return s + "-00-00-00";
		}
		eL = "(?:[0-9]{1,4}(?<!^0?0?0?0))" + strSpliter + "(?:0?[1-9]|1[0-2])" + strSpliter + "(?:0?[1-9]|1[0-9]|2[0-8]|(?:(?<=-(?:0?[13578]|1[02])-)(?:29|3[01]))|(?:(?<=-(?:0?[469]|11)-)(?:29|30))|(?:(?<=(?:(?:[0-9]{0,2}(?!0?0)(?:[02468]?(?<![13579])[048]|[13579][26]))|(?:(?:[02468]?[048]|[13579][26])00))-0?2-)(?:29)))(" + strSpliter + "(((0?[0-9])|(1[0-9])|(2[0-3]))" + strSpliter + "([0-5]?[0-9])))$";
		p = Pattern.compile (eL);
		m = p.matcher (s.trim ());
		if (m.matches ()) {
			return s + "-00";
		}

		eL = "(?:[0-9]{1,4}(?<!^0?0?0?0))" + strSpliter + "(?:0?[1-9]|1[0-2])" + strSpliter + "(?:0?[1-9]|1[0-9]|2[0-8]|(?:(?<=-(?:0?[13578]|1[02])-)(?:29|3[01]))|(?:(?<=-(?:0?[469]|11)-)(?:29|30))|(?:(?<=(?:(?:[0-9]{0,2}(?!0?0)(?:[02468]?(?<![13579])[048]|[13579][26]))|(?:(?:[02468]?[048]|[13579][26])00))-0?2-)(?:29)))(" + strSpliter + "(((0?[0-9])|(1[0-9])|(2[0-3]))" + strSpliter + "([0-5]?[0-9])((" + strSpliter + "([0-5]?[0-9]))?)))$";
		p = Pattern.compile (eL);
		m = p.matcher (s.trim ());
		if (m.matches ()) {
			return s;
		}
		return null;
	}

	/**
	 * @param strDate
	 *
	 * @return Date
	 * @name parseDate
	 * @author zhyang
	 * @date 2011-1-14
	 * @description 将格式为[yyyy-MM-dd HH:mm:ss]的字符串转为时间
	 */
	public static Date parseDate (String strDate) {
		String strDateFomater = getDateFomater (strDate);
		if (null == strDateFomater) {
			strDateFomater = (strDate.replaceAll ("[^0-9]", "") + "00000000000000").substring (0, 14);
		}
		return parseDate (strDateFomater.replaceAll ("-", ""), "yyyyMMddHHmmss");
	}

	public static String formatTime (long millis) {

		millis = Math.abs (millis);

		StringBuilder infoBuilder = new StringBuilder ();
		long tmp = 0;
		//毫秒
		tmp = millis % 1000;
		//秒
		millis = millis / 1000;

		if (millis == 0) {
			if (tmp > 0) {
				infoBuilder.insert (0, tmp + "毫秒");
			} else {
				return "0";
			}
		} else {
			//大于秒
			if (tmp > 0) {
				infoBuilder.insert (0, tmp);
			}

			//秒
			tmp = millis % 60;
			infoBuilder.insert (0, tmp + "秒");

			//分
			millis = millis / 60;
			//有分
			if (millis > 0) {

				//分
				tmp = millis % 60;
				infoBuilder.insert (0, tmp + "分");

				//时
				millis = millis / 60;
				//有时
				if (millis > 0) {
					//时
					tmp = millis % 24;
					infoBuilder.insert (0, tmp + "小时");

					//天
					millis = millis / 24;

					//有天
					if (millis > 0) {
						if (millis > 7) {
							//周
							infoBuilder.insert (0, "(" + millis / 7 + "周)");
						}
						infoBuilder.insert (0, millis + "天");
					}
				}
			}
		}

		return infoBuilder.toString ();
	}

	/**
	 * @param year
	 *
	 * @return Date
	 * @name getYearBefore
	 * @author zhyang
	 * @date 2011-1-14
	 * @description 得到几年前的时间
	 */
	public static Date getYearBefore (int year) {
		return getYearBefore (NOW (), year);
	}

	public static Date getYearBefore (Date d, int year) {
		return getDateCalendar (d, Calendar.YEAR, year, Regulation.BEFOR);
	}

	/**
	 * @param month
	 *
	 * @return Date
	 * @name getMonthBefore
	 * @author zhyang
	 * @date 2011-1-14
	 * @description 得到几个月前的时间
	 */
	public static Date getMonthBefore (int month) {
		return getMonthBefore (NOW (), month);
	}

	public static Date getMonthBefore (Date d, int month) {
		return getDateCalendar (d, Calendar.MONTH, month, Regulation.BEFOR);
	}

	/**
	 * @param week
	 *
	 * @return Date
	 * @name getWeekBefore
	 * @author zhyang
	 * @date 2011-1-15
	 * @description 得到几周前的时间
	 */
	public static Date getWeekBefore (int week) {
		return getWeekBefore (NOW (), week);
	}

	public static Date getWeekBefore (Date d, int week) {
		return getDateCalendar (d, Calendar.DATE, week * 7, Regulation.BEFOR);
	}

	/**
	 * @param d
	 * @param day
	 *
	 * @return Date
	 * @name getDateBefore
	 * @author zhyang
	 * @date 2011-1-14
	 * @description 得到几天前的时间
	 */
	public static Date getDateBefore (Date d, int day) {
		return getDateCalendar (d, Calendar.DATE, day, Regulation.BEFOR);
	}

	public static Date getDateBefore (int day) {
		return getDateBefore (NOW (), day);
	}

	/**
	 * @param hour
	 *
	 * @return Date
	 * @name getHourBefore
	 * @author zhyang
	 * @date 2011-1-14
	 * @description 得到几小时前的时间
	 */
	public static Date getHourBefore (int hour) {
		return getHourBefore (NOW (), hour);
	}

	public static Date getHourBefore (Date d, int hour) {
		return getDateCalendar (d, Calendar.HOUR, hour, Regulation.BEFOR);
	}

	/**
	 * @param minute
	 *
	 * @return Date
	 * @name getMinuteBefore
	 * @author zhyang
	 * @date 2011-1-14
	 * @description 返回当前时间minute分钟前的时间
	 */
	public static Date getMinuteBefore (int minute) {
		return getMinuteBefore (NOW (), minute);
	}

	public static Date getMinuteBefore (Date d, int minute) {
		return getDateCalendar (d, Calendar.MINUTE, minute, Regulation.BEFOR);
	}

	/**
	 * @param second
	 *
	 * @return Date
	 * @name getSecondBefore
	 * @author zhyang
	 * @date 2011-1-14
	 * @description 返回当前时间second秒钟前的时间
	 */
	public static Date getSecondBefore (int second) {
		return getSecondBefore (NOW (), second);
	}

	public static Date getSecondBefore (Date d, int second) {
		return getDateCalendar (d, Calendar.SECOND, second, Regulation.BEFOR);
	}

	/**
	 * @param year
	 *
	 * @return Date
	 * @name getYearAfter
	 * @author zhyang
	 * @date 2011-1-14
	 * @description 得到几年后的时间
	 */
	public static Date getYearAfter (int year) {
		return getYearAfter (NOW (), year);
	}

	public static Date getYearAfter (Date d, int year) {
		return getDateCalendar (d, Calendar.YEAR, year, Regulation.AFTER);
	}

	/**
	 * @param month
	 *
	 * @return Date
	 * @name getMonthAfter
	 * @author zhyang
	 * @date 2011-1-14
	 * @description 得到几个月后的时间
	 */
	public static Date getMonthAfter (int month) {
		return getMonthAfter (NOW (), month);
	}

	public static Date getMonthAfter (Date d, int month) {
		return getDateCalendar (d, Calendar.MONTH, month, Regulation.AFTER);
	}

	/**
	 * @param week
	 *
	 * @return Date
	 * @name getWeekAfter
	 * @author zhyang
	 * @date 2011-1-15
	 * @description 得到多少周以后的时间
	 */
	public static Date getWeekAfter (int week) {
		return getWeekAfter (NOW (), week);
	}

	public static Date getWeekAfter (Date d, int week) {
		return getDateCalendar (d, Calendar.DATE, week * 7, Regulation.AFTER);
	}

	public static Date getDateAfter (int day) {
		return getDateAfter (NOW (), day);
	}

	/**
	 * @param d
	 * @param day
	 *
	 * @return Date
	 * @name getDateAfter
	 * @author zhyang
	 * @date 2011-1-14
	 * @description 得到几天后的时间
	 */
	public static Date getDateAfter (Date d, int day) {
		return getDateCalendar (d, Calendar.DATE, day, Regulation.AFTER);
	}

	public static Date getHourAfter (int hour) {
		return getHourAfter (NOW (), hour);
	}

	public static Date getHourAfter (Date d, int hour) {
		return getDateCalendar (d, Calendar.HOUR, hour, Regulation.AFTER);
	}

	/**
	 * @param minute
	 *
	 * @return Date
	 * @name getMinuteAfter
	 * @author zhyang
	 * @date 2011-1-14
	 * @description 返回当前时间minute分钟后的时间
	 */
	public static Date getMinuteAfter (int minute) {
		return getMinuteAfter (NOW (), minute);
	}

	public static Date getMinuteAfter (Date d, int minute) {
		return getDateCalendar (d, Calendar.MINUTE, minute, Regulation.AFTER);
	}

	/**
	 * @param second
	 *
	 * @return Date
	 * @name getSecondAfter
	 * @author zhyang
	 * @date 2011-1-14
	 * @description 返回当前时间second秒钟后的时间
	 */
	public static Date getSecondAfter (int second) {
		return getSecondAfter (NOW (), second);
	}

	public static Date getSecondAfter (Date d, int second) {
		return getDateCalendar (d, Calendar.SECOND, second, Regulation.AFTER);
	}

	/**
	 * 时间在某点之前
	 *
	 * @param time
	 * @param point
	 *
	 * @return
	 */
	public static boolean isBefore (Date time, Date point) {
		return time.getTime () < point.getTime ();
	}

	/**
	 * 时间在某一点之后
	 *
	 * @param time
	 * @param point
	 *
	 * @return
	 */
	public static boolean isAfter (Date time, Date point) {
		return time.getTime () > point.getTime ();
	}

	/**
	 * 时间在两点之间
	 *
	 * @param time
	 * @param pointA
	 * @param pointB
	 *
	 * @return
	 */
	public static boolean isBetween (Date time, Date pointA, Date pointB) {
		if (pointA.getTime () < pointB.getTime ()) {
			return time.getTime () > pointA.getTime () && time.getTime () < pointB.getTime ();
		} else {
			return time.getTime () < pointA.getTime () && time.getTime () > pointB.getTime ();
		}
	}

	/**
	 * 日期格式（yyyyMMdd格式）
	 *
	 * @return
	 */
	public static String nowStr() {
		return shortDate ();
	}
	public static String shortDate() {
		return formatDate (NOW ());
	}
	public static String shortDate(Date date) {
		return formatDate (date,dtShort);
	}

	public static final String longDate(){
		return longDate (NOW ());
	}
	/**
	 * 返回长日期格式（yyyyMMddHHmmss格式）
	 *
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static final String longDate(Date date) {
		if (date == null) {
			return null;
		}
		return getFormat(dtLong).format(date);
	}

	public static String simpleFormat(Date date){
		return formatDate (date);
	}

	/**
	 * yyyy-MM-dd 日期字符转换为时间
	 *
	 * @param stringDate
	 * @return
	 * @throws ParseException
	 */
	public static final Date string2Date(String stringDate) throws ParseException {
		if (stringDate == null) {
			return null;
		}
		return getFormat(dtSimple).parse(stringDate);
	}

	/**
	 * 返回日期时间
	 *
	 * @param stringDate
	 * @return
	 * @throws ParseException
	 */
	public static final Date string2DateTime(String stringDate) throws ParseException {
		if (stringDate == null) {
			return null;
		}
		return getFormat(simple).parse(stringDate);
	}

	/**
	 * yyyy-MM-dd
	 *
	 * @param date
	 * @return
	 */
	public static final String dtSimpleFormat(Date date) {
		if (date == null) {
			return "";
		}
		return getFormat(dtSimple).format(date);
	}
	/**
	 * 加减天数
	 *
	 * @param date
	 * @return Date
	 */
	public static final Date increaseDate(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();
	}
	/**
	 * 获取一个月前的时间
	 */
	public static Date getOneMonthBegin() {

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);

		return cal.getTime();
	}
	/**
	 * 计算时间差
	 *
	 * @param dBefor 首日
	 * @param dAfter 尾日
	 * @return 时间差(毫秒)
	 */
	public static final long getDateBetween(Date dBefor, Date dAfter) {
		long lBefor = 0;
		long lAfter = 0;
		long lRtn = 0;
		/** 取得距离 1970年1月1日 00:00:00 GMT 的毫秒数 */
		lBefor = dBefor.getTime();
		lAfter = dAfter.getTime();
		lRtn = lAfter - lBefor;
		return lRtn;
	}

	/**
	 * 是否闰年
	 *
	 * @param year
	 * @return
	 */
	public static final boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}

	public static enum Regulation {
		/**
		 * 前
		 */
		BEFOR,
		/**
		 * 后
		 */
		AFTER
	}
}

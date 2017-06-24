package cn.pay.ebank.common.time;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhyang on 2014/12/17 0017.
 */
public class DateTimeUtils {
	
	/**
	 * 修改时间
	 * @param date 被修改的日期
	 * @param field 修改的字段，参考Calendar字段，如Calendar.HOUR_OFF_DAY 修改小时字段
	 * @param amount 增量
	 *
	 *
	 * @return
	 *
	 * @See SEE java.util.Calendar#add(int, int)
	 */
	public static Date add(Date date, int field, int amount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);
		
		return calendar.getTime();
	}
	
	/**
	 * 解析时间长度
	 * @param timeStr
	 * @return
	 */
	public static long getTime(String timeStr) {
		int intYear;
		int intMounth;
		int intDate;
		int intDay;
		int intHour;
		int intMinute;
		int intSecond;

		timeStr = formatString(timeStr);
		if(!timeStr.matches("\\d*N\\d*Y\\d*R\\d*Z\\d*X\\d*F\\d*M")){
			return -1;
		}
		intYear = getNumber(timeStr, 0, timeStr.indexOf("N"));
		intMounth = getNumber(timeStr, timeStr.indexOf("N") + 1, timeStr.indexOf("Y"));
		intDate = getNumber(timeStr, timeStr.indexOf("Y") + 1, timeStr.indexOf("R"));
		intDay = getNumber(timeStr, timeStr.indexOf("R") + 1, timeStr.indexOf("Z"));
		intHour = getNumber(timeStr, timeStr.indexOf("Z") + 1, timeStr.indexOf("X"));
		intMinute = getNumber(timeStr, timeStr.indexOf("X") + 1, timeStr.indexOf("F"));
		intSecond = getNumber(timeStr, timeStr.indexOf("F") + 1, timeStr.indexOf("M"));
		
		//得到月
		long time = intYear * 12 + intMounth;
		//得到天
		time = time * 30 + intDate + intDay * 7;
		//得到小时
		time = time * 24 + intHour;
		//得到分
		time = time * 60 + intMinute;
		//得到秒
		time = time * 60 + intSecond;
		//得到毫秒
		time *= 1000;
		
		return time;
	}
	
	/**
	 *
	 * @name formatString
	 * @author zhyang
	 * @date 2011-1-14
	 * @param checkTime
	 * @return boolean
	 * @description 格式化时间检查字符串
	 */
	private static String formatString(String checkTime) {
		checkTime = checkTime.toUpperCase();
		if (!checkTime.matches("\\d*N\\d*Y\\d*R\\d*Z\\d*X\\d*F\\d*M")) {
			//modify by ghl 2011-3月1日 修改replaceAll 的正则表达式
			checkTime = checkTime.replaceAll("(年|(YEAR))+", "N");
			checkTime = checkTime.replaceAll("[N]+", "N");
			checkTime = checkTime.replaceAll("(月|(MONTH)|MON)+", "Y");
			checkTime = checkTime.replaceAll("[Y]+", "Y");
			checkTime = checkTime.replaceAll("(日|(DAYS)|(DAY))+", "R");
			checkTime = checkTime.replaceAll("[R]+", "R");
			checkTime = checkTime.replaceAll("(周|(WEEK))+", "Z");
			checkTime = checkTime.replaceAll("[Z]+", "Z");
			checkTime = checkTime.replaceAll("(时|(HOURS)|(HOUR)|H)+", "X");
			checkTime = checkTime.replaceAll("[X]+", "X");
			checkTime = checkTime.replaceAll("(分|(MINUTES)|(MINUTE)|(MIN))+", "F");

			checkTime = checkTime.replaceAll("[F]+", "F");
			checkTime = checkTime.replaceAll("(秒|(SECOND)|S)+", "M");
			checkTime = checkTime.replaceAll("[M]+", "M");
			checkTime = checkTime.replaceAll("[^NYRZXFM0-9]", "");
			//modify by ghl 2011-3月1日 修改replaceAll 的正则表达式
		} else {
			return checkTime;
		}
		//如果格式正确，就不用检查了
		if (checkTime.matches("\\d*N\\d*Y\\d*R\\d*Z\\d*X\\d*F\\d*M")) {
			return checkTime;
		}
		
		if (checkTime.indexOf("N") < 0) {
			checkTime = "0N" + checkTime;
		}
		if (checkTime.indexOf("Y") < 0) {
			checkTime = checkTime.substring(0, checkTime.indexOf("N") + 1) + "0Y"
						+ checkTime.substring(checkTime.indexOf("N") + 1);
		}
		if (checkTime.indexOf("R") < 0) {
			checkTime = checkTime.substring(0, checkTime.indexOf("Y") + 1) + "0R"
						+ checkTime.substring(checkTime.indexOf("Y") + 1);
		}
		if (checkTime.indexOf("Z") < 0) {
			checkTime = checkTime.substring(0, checkTime.indexOf("R") + 1) + "0Z"
						+ checkTime.substring(checkTime.indexOf("R") + 1);
		}
		if (checkTime.indexOf("X") < 0) {
			checkTime = checkTime.substring(0, checkTime.indexOf("Z") + 1) + "0X"
						+ checkTime.substring(checkTime.indexOf("Z") + 1);
		}
		if (checkTime.indexOf("F") < 0) {
			checkTime = checkTime.substring(0, checkTime.indexOf("X") + 1) + "0F"
						+ checkTime.substring(checkTime.indexOf("X") + 1);
		}
		if (checkTime.indexOf("M") < 0) {
			checkTime = checkTime.substring(0, checkTime.indexOf("F") + 1) + "0M"
						+ checkTime.substring(checkTime.indexOf("F") + 1);
		}
		//如果格式正确，就不用检查了
		if (checkTime.matches("\\d*N\\d*Y\\d*R\\d*Z\\d*X\\d*F\\d*M")) {
			return checkTime;
		} else {
			return checkTime;
		}
	}
	
	private static int getNumber(String strNumber, int intStartIndex, int intEndIndex) {

		if(intEndIndex==-1 || intStartIndex==-1){
			return 0;
		}
		try {
			String strTemp;
			
			strTemp = strNumber.substring(intStartIndex, intEndIndex);
			if (strTemp.equals("")) {
				return 0;
			} else {
				return new Integer(strTemp).intValue();
			}
		} catch (Exception e) {
		}
		return 0;
	}
	
	public static void main(String[] args) {
		System.out.println(getTime("1minute"));
	}
}

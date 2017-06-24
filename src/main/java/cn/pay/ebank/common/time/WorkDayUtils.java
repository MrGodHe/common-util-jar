package cn.pay.ebank.common.time;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import cn.pay.ebank.common.utils.DateUtils;

/**
 * Created by zhyang on 2014/12/17 0017.
 */
public class WorkDayUtils {
	/**
	 * 节假日
	 */
	private static final HashMap<String, Holiday> $HOLIDAYS = new HashMap<String, Holiday> ();
	/**
	 * 调休上班
	 */
	private static final HashMap<String, OnDutyDay> $WORKDAY = new HashMap<String, OnDutyDay> ();
	/**
	 * 工作时间
	 */
	private static final TimeSlot $WORKTIME = new TimeSlot (new Time (9, 30), new Time (18, 0));


	static {

		//元旦
		//___放假
		$HOLIDAYS.put ("2015-01-01", new Holiday (2015, 1, 1));
		$HOLIDAYS.put ("2015-01-02", new Holiday (2015, 1, 2));
		$HOLIDAYS.put ("2015-01-03", new Holiday (2015, 1, 3));
		//___调休上班
		$WORKDAY.put ("2015-01-04", new OnDutyDay (2015, 1, 4, $WORKTIME));


		//春节
		//___放假
		$HOLIDAYS.put ("2015-02-18", new Holiday (2015, 2, 18));
		$HOLIDAYS.put ("2015-02-19", new Holiday (2015, 2, 19));
		$HOLIDAYS.put ("2015-02-20", new Holiday (2015, 2, 20));
		$HOLIDAYS.put ("2015-02-21", new Holiday (2015, 2, 21));
		$HOLIDAYS.put ("2015-02-22", new Holiday (2015, 2, 22));
		$HOLIDAYS.put ("2015-02-23", new Holiday (2015, 2, 23));
		$HOLIDAYS.put ("2015-02-24", new Holiday (2015, 2, 24));
		//___调休上班
		$WORKDAY.put ("2015-02-15", new OnDutyDay (2015, 2, 15, $WORKTIME));
		$WORKDAY.put ("2015-02-28", new OnDutyDay (2015, 2, 28, $WORKTIME));

		//清明节
		//___放假
		$HOLIDAYS.put ("2015-04-05", new Holiday (2015, 4, 5));
		$HOLIDAYS.put ("2015-04-06", new Holiday (2015, 4, 6));

		//劳动节
		//___放假
		$HOLIDAYS.put ("2015-05-01", new Holiday (2015, 5, 1));
		$HOLIDAYS.put ("2015-05-02", new Holiday (2015, 5, 2));
		$HOLIDAYS.put ("2015-05-03", new Holiday (2015, 5, 3));

		//端午节
		//___放假
		$HOLIDAYS.put ("2015-06-20", new Holiday (2015, 6, 20));
		$HOLIDAYS.put ("2015-06-21", new Holiday (2015, 6, 21));
		$HOLIDAYS.put ("2015-06-22", new Holiday (2015, 6, 22));

		//中秋节
		//___放假
		$HOLIDAYS.put ("2015-09-27", new Holiday (2015, 9, 27));

		//国庆节
		//___放假
		$HOLIDAYS.put ("2015-10-01", new Holiday (2015, 10, 1));
		$HOLIDAYS.put ("2015-10-02", new Holiday (2015, 10, 2));
		$HOLIDAYS.put ("2015-10-03", new Holiday (2015, 10, 3));
		$HOLIDAYS.put ("2015-10-04", new Holiday (2015, 10, 4));
		$HOLIDAYS.put ("2015-10-05", new Holiday (2015, 10, 5));
		$HOLIDAYS.put ("2015-10-06", new Holiday (2015, 10, 6));
		$HOLIDAYS.put ("2015-10-07", new Holiday (2015, 10, 7));

		//___调休上班
		$WORKDAY.put ("2015-10-10", new OnDutyDay (2015, 10, 10, $WORKTIME));


	}

	/**
	 * 今天是否工作日
	 *
	 * @return
	 */
	public static boolean isTodayWorkDay () {
		return isTodayWorkDay ($WORKDAY, $HOLIDAYS);
	}

	/**
	 * 判断给定时间是否是工作日
	 *
	 * @param dutyDayMap 工作时间(周六、周日)
	 * @param holidayMap 假日时间(周一至周五)
	 * @return
	 */
	public static boolean isTodayWorkDay (HashMap<String, OnDutyDay> dutyDayMap, HashMap<String, Holiday> holidayMap) {
		return isWorkDay (new Date ());
	}

	/**
	 * 判断给定日期是否工作日（2015年）
	 *
	 * @param date （2015年）
	 * @return
	 */
	public static boolean isWorkDay (Date date) {
		return isWorkDay (date, $WORKDAY, $HOLIDAYS);
	}

	/**
	 * 判断给定时间是否是工作日
	 *
	 * @param date
	 * @param dutyDayMap 工作时间(周六、周日)
	 * @param holidayMap 假日时间(周一至周五)
	 * @return
	 */
	public static boolean isWorkDay (Date date, HashMap<String, OnDutyDay> dutyDayMap, HashMap<String, Holiday> holidayMap) {
		Calendar calendar = Calendar.getInstance ();
		calendar.setTime (date);

		if (calendar.get (Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get (Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			//如果是周末周日，判断是否为调休上班时间
			return dutyDayMap != null && dutyDayMap.containsKey (DateUtils.formatDate (date, DateUtils.SIMPLE_DATE_FORMAT_STRING));
		} else {
			//如果不是周末周日，判断是否节日
			return holidayMap == null || !holidayMap.containsKey (DateUtils.formatDate (date, DateUtils.SIMPLE_DATE_FORMAT_STRING));
		}
	}

	/**
	 * 当前时间是否工作时间
	 *
	 * @return
	 */
	public static boolean isNowWorkTime () {
		return isNowWorkTime ($WORKTIME);
	}

	/**
	 * 当前时间是否工作时间
	 *
	 * @param timeSlot 工作时间区间
	 * @return
	 */
	public static boolean isNowWorkTime (TimeSlot timeSlot) {
		return isWorkTime (new Date (), timeSlot);
	}

	/**
	 * 判断给定时间是否工作时间
	 *
	 * @param date
	 * @return
	 */
	public static boolean isWorkTime (Date date) {
		return isWorkTime (date, $WORKTIME);
	}

	/**
	 * 判断给定时间是否工作时间
	 *
	 * @param date
	 * @param workTime 工作时间区间
	 * @return
	 */
	public static boolean isWorkTime (Date date, TimeSlot workTime) {
		return isWorkTime (date, $WORKDAY, $HOLIDAYS, workTime);
	}

	/**
	 * 判断给定时间是否工作时间
	 *
	 * @param date
	 * @param dutyDayMap 工作时间(周六、周日)
	 * @param holidayMap 假日时间(周一至周五)
	 * @param workTime
	 * @return
	 */
	public static boolean isWorkTime (Date date, HashMap<String, OnDutyDay> dutyDayMap, HashMap<String, Holiday> holidayMap, TimeSlot workTime) {
		//判断当日是否工作日
		if (isWorkDay (date, dutyDayMap, holidayMap)) {
			if (workTime == null) {
				workTime = $WORKTIME;
			}
			return workTime.isInTimeSlot (date);
		}
		return false;
	}

	/**
	 * 获取下一个工作日
	 *
	 * @return
	 */
	public static Date getNextWorkDay () {
		return getNextWorkDay (new Date ());
	}

	/**
	 * 获取下一个工作日
	 *
	 * @param date
	 * @return
	 */
	public static Date getNextWorkDay (Date date) {
		return getNextWorkDay (date, $WORKTIME);
	}

	public static Date getNextWorkDay (Date date, TimeSlot workTime) {
		return getNextWorkDay (date, $WORKDAY, $HOLIDAYS, workTime);
	}

	public static Date getNextWorkDay (Date date, HashMap<String, OnDutyDay> dutyDayMap, HashMap<String, Holiday> holidayMap) {
		return getNextWorkDay (date, dutyDayMap, holidayMap, $WORKTIME);
	}


	public static Date getNextWorkDay (Date date, HashMap<String, OnDutyDay> dutyDayMap, HashMap<String, Holiday> holidayMap, TimeSlot workTime) {
		if (workTime == null) {
			workTime = $WORKTIME;
		}
		Date workDate = date;
		Calendar calendar = Calendar.getInstance ();
		calendar.setTime (date);

		//非工作时间，调整到工作时间
		if (!isWorkTime (date, dutyDayMap, holidayMap, workTime)) {
			Time now = new Time (date);
			//未到开始时间
			if (workTime.getStart ().isGreaterThan (now)) {
				calendar.set (Calendar.HOUR_OF_DAY, workTime.getStart ().getHour ());
				calendar.set (Calendar.MINUTE, workTime.getStart ().getMinute ());
			} else if (workTime.getEnd ().isGreaterThan (now)) {
				//超过结束时间,调整到下一日
				calendar.add (Calendar.DATE, 1);
				calendar.set (Calendar.HOUR_OF_DAY, workTime.getStart ().getHour ());
				calendar.set (Calendar.MINUTE, workTime.getStart ().getMinute ());
			}
		}

		//非工作日，调整到工作日
		if (!isWorkDay (date, dutyDayMap, holidayMap)) {
			calendar.add (Calendar.DATE, 1);
			while (!isWorkDay (calendar.getTime (), dutyDayMap, holidayMap)) {
				calendar.add (Calendar.DATE, 1);
			}
			workDate = calendar.getTime ();
		}


		return workDate;
	}
}

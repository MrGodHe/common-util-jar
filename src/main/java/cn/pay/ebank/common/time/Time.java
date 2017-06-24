package cn.pay.ebank.common.time;

import java.util.Calendar;
import java.util.Date;

/**
 * 时间   不包含秒
 * Created by zhyang on 2014/12/17 0017.
 */
public class Time {
	/**
	 * 小时
	 */
	private int hour;
	/**
	 * 分钟
	 */
	private int minute;

	public Time () {
		Calendar calendar = Calendar.getInstance ();
		setHour (calendar.get (Calendar.HOUR_OF_DAY));
		setMinute (calendar.get (Calendar.MINUTE));
	}

	public Time (int hour, int minute) {
		setHour (hour);
		setMinute (minute);
	}

	public Time (Date date) {
		Calendar calendar = Calendar.getInstance ();
		calendar.setTime (date);
		setHour (calendar.get (Calendar.HOUR_OF_DAY));
		setMinute (calendar.get (Calendar.MINUTE));
	}

	/**
	 * 小时
	 *
	 * @return
	 */
	public int getHour () {
		return hour;
	}

	/**
	 * 小时
	 *
	 * @param hour
	 */
	public void setHour (int hour) {
		this.hour = hour;
	}

	/**
	 * 分钟
	 *
	 * @return
	 */
	public int getMinute () {
		return minute;
	}

	/**
	 * 分钟
	 *
	 * @param minute
	 */
	public void setMinute (int minute) {
		this.minute = minute;
	}

	public boolean isGreaterThan (Time another) {
		return hour > another.getHour () || (hour == another.getHour () && minute > another.getMinute ());
	}

	public boolean isEqualsOrGreaterThan (Time another) {
		return hour > another.getHour () || (hour == another.getHour () && minute >= another.getMinute ());
	}

	public boolean isEquals (Time another) {
		return hour == another.getHour () && minute == another.getMinute ();
	}

	public boolean isLessThan (Time another) {
		return hour < another.getHour () || (hour == another.getHour () && minute < another.getMinute ());
	}

	public boolean isEqualsOrLessThan (Time another) {
		return hour < another.getHour () || (hour == another.getHour () && minute <= another.getMinute ());
	}

	public String toString () {
		return (hour < 10 ? "0" : "") + hour + ":" + (minute < 10 ? "0" : "") + minute;
	}
}

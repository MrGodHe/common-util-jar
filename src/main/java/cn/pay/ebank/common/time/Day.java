package cn.pay.ebank.common.time;

import java.util.Calendar;

/**
 * Created by zhyang on 2014/12/17 0017.
 */
public class Day {
	/**
	 * 年
	 */
	private int year;
	/**
	 * 月
	 */
	private int month;
	/**
	 * 日
	 */
	private int date;

	public Day () {
		Calendar calendar = Calendar.getInstance ();
		setYear (calendar.get (Calendar.YEAR));
		setMonth (calendar.get (Calendar.MONTH) + 1);
		setDate (calendar.get (Calendar.DATE));
	}

	public Day (int year, int month, int date) {
		setYear (year);
		setMonth (month);
		setDate (date);
	}


	/**
	 * 年
	 *
	 * @return
	 */
	public int getYear () {
		return year;
	}

	/**
	 * 年
	 *
	 * @param year
	 */
	public void setYear (int year) {
		this.year = year;
	}

	/**
	 * 月
	 *
	 * @return
	 */
	public int getMonth () {
		return month;
	}

	/**
	 * 月
	 *
	 * @param month
	 */
	public void setMonth (int month) {
		this.month = month;
	}

	/**
	 * 日
	 *
	 * @return
	 */
	public int getDate () {
		return date;
	}

	/**
	 * 日
	 *
	 * @param date
	 */
	public void setDate (int date) {
		this.date = date;
	}

	public String toString () {
		return year + "-" +(month<10?"0":"") + month + "-" +(date<10?"0":"")+ date;
	}
}

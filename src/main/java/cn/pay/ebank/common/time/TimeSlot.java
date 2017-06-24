package cn.pay.ebank.common.time;

import java.util.Date;
import java.util.List;

import cn.pay.ebank.common.utils.CollectionUtils;
import cn.pay.ebank.common.utils.NumbericUtils;
import cn.pay.ebank.common.utils.StringUtils;

/**
 * Created by zhyang on 2014/12/17 0017.
 */
public class TimeSlot {
	/**
	 * 时间段表达式
	 */
	public static final String TIMESLOT_REGEX = "\\d{1,2}:\\d{1,2}~\\d{1,2}:\\d{1,2}";

	/**
	 * 开始时间
	 */
	private Time start;

	/**
	 * 结束时间
	 */
	private Time end;

	public TimeSlot () {

	}

	public TimeSlot (Time start, Time end) {
		setStart (start);
		setEnd (end);
	}

	/**
	 * 开始时间
	 *
	 * @return
	 */
	public Time getStart () {
		return start;
	}

	/**
	 * 开始时间
	 *
	 * @param start
	 */
	public void setStart (Time start) {
		this.start = start;
	}

	/**
	 * 结束时间
	 *
	 * @return
	 */
	public Time getEnd () {
		return end;
	}

	/**
	 * 结束时间
	 *
	 * @param end
	 */
	public void setEnd (Time end) {
		this.end = end;
	}

	public String toString () {
		return start + "~" + end;
	}

	/**
	 * 判断给定时间片在当前
	 *
	 * @param time
	 *
	 * @return
	 */
	public boolean isInTimeSlot (Time time) {
		return start.isEqualsOrLessThan (time) && end.isEqualsOrGreaterThan (time);
	}

	/**
	 * 判断给定时间片在当前
	 *
	 * @param time
	 *
	 * @return
	 */
	public boolean isInTimeSlot (Date time) {
		return isInTimeSlot (new Time (time));
	}


	/**
	 * @param timeSlot
	 *
	 * @return
	 */
	public static List<TimeSlot> parser (String timeSlot) {
		List<TimeSlot> timeSlots = CollectionUtils.newArrayList ();
		if (StringUtils.isNotEmpty (timeSlot)) {
			timeSlot = timeSlot.replaceAll ("\\s+", "").replaceAll ("[-—]+", "~");
			String[] slots = timeSlot.split ("\\|");
			for (String slot : slots) {
				if (StringUtils.isMatches (slot, TIMESLOT_REGEX)) {
					timeSlots.add (parserTimeSlot (slot));
				}
			}
		}
		return timeSlots;
	}

	private static TimeSlot parserTimeSlot (String time) {
		TimeSlot timeSlot = new TimeSlot ();
		if (null == time || "".equals (time)) {
			return timeSlot;
		}

		String[] tmp = time.split ("~");
		if (tmp.length > 0) {
			Time beginTime = new Time ();
			String[] beginTimeTmp = tmp[0].split (":");
			if (beginTimeTmp.length > 0) {
				beginTime.setHour (NumbericUtils.toInt (beginTimeTmp[0]));
			}
			if (beginTimeTmp.length > 1) {
				beginTime.setMinute (NumbericUtils.toInt (beginTimeTmp[1]));
			}
			timeSlot.setStart (beginTime);
		}

		if (tmp.length > 1) {
			Time endTime = new Time ();
			String[] endTimeTmp = tmp[1].split (":");
			if (endTimeTmp.length > 0) {
				endTime.setHour (NumbericUtils.toInt (endTimeTmp[0]));
			}
			if (endTimeTmp.length > 1) {
				endTime.setMinute (NumbericUtils.toInt (endTimeTmp[1]));
			}
			timeSlot.setEnd (endTime);
		}

		return timeSlot;
	}

	public static void main (String[] args) {
		System.out.println ("ab-c b   de--t a———d ".replaceAll ("\\s+", "").replaceAll ("[-—]+", "~"));
		System.out.println (parser ("8:00-22:00|89:00-21:00"));
	}
}

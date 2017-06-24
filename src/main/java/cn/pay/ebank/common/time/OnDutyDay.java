package cn.pay.ebank.common.time;

/**
 * Created by zhyang on 2014/12/17 0017.
 */
public class OnDutyDay {
	/** 时间 */
	private Day day;

	/** 值班时间 */
	private TimeSlot onDutyTime = new TimeSlot (new Time (),new Time ());

	public OnDutyDay (int year, int month, int date) {
		day = new Day (year, month, date);
	}
	public OnDutyDay (int year, int month, int date,TimeSlot onDutyTime) {
		day = new Day (year, month, date);
		setOnDutyTime (onDutyTime);
	}

	public OnDutyDay (TimeSlot onDutyTime) {
		this();
		setOnDutyTime (onDutyTime);
	}

	public OnDutyDay(){
		day=new Day ();
	}


	public Day getDay () {
		return day;
	}

	public void setDay (Day day) {
		this.day = day;
	}

	public TimeSlot getOnDutyTime () {
		return onDutyTime;
	}

	public void setOnDutyTime (TimeSlot onDutyTime) {
		this.onDutyTime = onDutyTime;
	}

	public String toString(){
		return day.toString ()+ ",onDutyTime="+onDutyTime;
	}
}

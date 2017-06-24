package cn.pay.ebank.common.time;

/**
 * 节假日
 */
public class Holiday extends  OnDutyDay {
	/** 是否值日 */
	private boolean onDuty = false;

	public Holiday (int year, int month, int date) {
		super(year,month,date);
	}

	public Holiday (int year, int month, int date,TimeSlot onDutyTiime) {
		super(year,month,date,onDutyTiime);
		setOnDuty (true);
	}

	public Holiday (int year, int month, int date,boolean onDuty) {
		super(year,month,date);
		setOnDuty (onDuty);
	}

	public Holiday (int year, int month, int date,boolean onDuty,TimeSlot onDutyTiime) {
		super(year,month,date,onDutyTiime);
		setOnDuty (onDuty);
	}

	public Holiday () {
		super();
	}

	public boolean isOnDuty () {
		return onDuty;
	}

	public void setOnDuty (boolean onDuty) {
		this.onDuty = onDuty;
	}

	public String toString(){
		return getDay ().toString ()+",onDuty="+onDuty+ (onDuty?",onDutyTime="+getOnDutyTime ():"");
	}
}

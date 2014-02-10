package database;

import java.util.Calendar;

public class SmsRecordRepDbStatus {

	private int id;
	private Calendar date;
	private String parameterName;
	private String parameterValue;

	/*public SmsRecord(int id, Calendar date,
			String parameterName, String parameterValue) {

		this.id = id;
		this.date = date;
		this.parameterName = parameterName;
		this.parameterValue = parameterValue;

	}*/
	
	public SmsRecordRepDbStatus(Integer id, String date,
			String parameterName, String parameterValue) {

		this.id = id;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(Long.parseLong(date));
		this.date = calendar;
		this.parameterName = parameterName;
		this.parameterValue = parameterValue;

	}

	public SmsRecordRepDbStatus(String date,
			String parameterName, String parameterValue) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(Long.parseLong(date));
		this.date = calendar;
		this.parameterName = parameterName;
		this.parameterValue = parameterValue;

	}

	public SmsRecordRepDbStatus(Calendar date,
			String parameterName, String parameterValue) {

		this.id = 0;
		this.date = date;
		this.parameterName = parameterName;
		this.parameterValue = parameterValue;

	}

	public Calendar getDate() {
		return this.date;
	}

	public String getParameterName() {
		return this.parameterName;
	}

	public String getParameterValue() {
		return this.parameterValue;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public void setParameterName(String name) {
		this.parameterName = name;
	}

	public void setParameterValue(String value) {
		this.parameterValue = value;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

}

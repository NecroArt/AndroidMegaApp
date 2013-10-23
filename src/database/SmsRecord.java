package database;

import java.util.Calendar;

public class SmsRecord {

	private int id;
	private String smsId;
	private Calendar date;
	private String parameterName;
	private String parameterValue;

	public SmsRecord(int id, String smsId, Calendar date, String parameterName,
			String parameterValue) {

		this.setId(id);
		this.smsId = smsId;
		this.date = date;
		this.parameterName = parameterName;
		this.parameterValue = parameterValue;

	}

	public SmsRecord(String smsId, String date, String parameterName,
			String parameterValue) {

		this.smsId = smsId;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(Long.parseLong(date));
		this.date = calendar;
		this.parameterName = parameterName;
		this.parameterValue = parameterValue;

	}

	public SmsRecord(String smsId, Calendar date, String parameterName,
			String parameterValue) {

		this.setId(0);
		this.smsId = smsId;
		this.date = date;
		this.parameterName = parameterName;
		this.parameterValue = parameterValue;

	}

	public String getSmsId() {
		return this.smsId;
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
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

}

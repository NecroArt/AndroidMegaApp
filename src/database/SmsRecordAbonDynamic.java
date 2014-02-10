package database;

import java.util.Calendar;

public class SmsRecordAbonDynamic {
	
	private int id;
	private Calendar date;
	private String region;
	private String subs;
	private String churn;
	private String trend;
	
	public SmsRecordAbonDynamic (Integer id, Calendar date, String region, String subs, String churn, String trend) {
		
		this.id = id;
		this.date = date;
		this.region = region;
		this.subs = subs;
		this.churn = churn;
		this.trend = trend;
		
	}
	
	public SmsRecordAbonDynamic (Integer id, String date, String region, String subs, String churn, String trend) {
		
		this.id = id;
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(Long.parseLong(date));
		this.date = calendar;
		
		this.region = region;
		this.subs = subs;
		this.churn = churn;
		this.trend = trend;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSubs() {
		return subs;
	}

	public void setSubs(String subs) {
		this.subs = subs;
	}

	public String getChurn() {
		return churn;
	}

	public void setChurn(String churn) {
		this.churn = churn;
	}

	public String getTrend() {
		return trend;
	}

	public void setTrend(String trend) {
		this.trend = trend;
	}

}

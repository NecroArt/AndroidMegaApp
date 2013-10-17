package com.example.myfirstapp;

import java.util.Calendar;

public class SMS {
	
	private String smsId;
	private String content;
	private Calendar date;
	
	/*public SMS (String content, Calendar date){
		this.content = content;
		this.date = date;
	}*/
	
	public SMS (String smsId, String content, Calendar date){
		this.smsId = smsId;
		this.content = content;
		this.date = date;
	}
	
	public String getContent () {
		return this.content;
	}
	
	public Calendar getDate () {
		return this.date;
	}

	public String getId() {
		return this.smsId;
	}
}
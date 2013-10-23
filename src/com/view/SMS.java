package com.view;

public class SMS {
	
	private String smsId;
	private String content;
	private String date;
	
	/*public SMS (String content, Calendar date){
		this.content = content;
		this.date = date;
	}*/
	
	public SMS (String smsId, String content, String date){
		this.smsId = smsId;
		this.content = content;
		this.date = date;
	}
	
	public String getContent () {
		return this.content;
	}
	
	public String getDate () {
		return this.date;
	}

	public String getId() {
		return this.smsId;
	}
}
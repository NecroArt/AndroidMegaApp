package com.view;

/**
 * Этот класс нужен для передачи парсеру смс-ки.
 * 
 * @author artem.voytsekhovsky
 * 
 */
public class SMSInstance {

	private String smsId;
	private String content;
	private String date;

	public SMSInstance(String content, String date) {
		this.content = content;
		this.date = date;
	}

	public SMSInstance(String smsId, String content, String date) {
		this.smsId = smsId;
		this.content = content;
		this.date = date;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String conent) {
		this.content = conent;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getId() {
		return this.smsId;
	}

	public void setId(String smsId) {
		this.smsId = smsId;
	}

}
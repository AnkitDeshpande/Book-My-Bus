package com.masai.exception;


import java.time.LocalDate;

public class ErrorDetails {
	private LocalDate date;
	private String msg;
	private String url;

	public ErrorDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ErrorDetails(LocalDate date, String msg, String url) {
		super();
		this.date = date;
		this.msg = msg;
		this.url = url;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}

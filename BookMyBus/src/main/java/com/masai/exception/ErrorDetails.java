package com.masai.exception;


import lombok.Getter;

import java.time.LocalDate;

@Getter
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

    public void setDate(LocalDate date) {
		this.date = date;
	}

    public void setMsg(String msg) {
		this.msg = msg;
	}

    public void setUrl(String url) {
		this.url = url;
	}

}

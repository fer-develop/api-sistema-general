package com.sistema.general.entity;

import java.util.List;

public class Response {
	
	int code;
	String message;
	Object data = List.of();
	String token;
	
	public Response(int code, String message, Object data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public Response(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	
	public Response(int code, String message, Object data, String token) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}

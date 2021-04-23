package com.sistema.general.exception;

public class SistemaException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int errorCode;
	private String messageError;
	private Object data;
	
	public SistemaException(int errorCode, String messageError, Object data) {
		super();
		this.errorCode = errorCode;
		this.messageError = messageError;
		this.data = data;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessageError() {
		return messageError;
	}

	public void setMessageError(String messageError) {
		this.messageError = messageError;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "SistemaException [errorCode=" + errorCode + ", messageError=" + messageError + ", data=" + data + "]";
	}
	
}

package com.getlocalization.client;

public class QueryException extends Exception {
	public QueryException(String message, int statusCode)
	{
		super(message);
		this.statusCode = statusCode;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	
	private int statusCode;
}

package com.ratelimiting.exception;

public class RateLimitingException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public RateLimitingException(String message) {
		super(message);
	}
	
	public RateLimitingException(String message, Throwable cause) {
		super(message, cause);
	}

}

package com.ratelimiting.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ratelimiting.exception.RateLimitingException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(RateLimitingException.class)
	public ResponseEntity<String> handleRateLimitngException(RateLimitingException ex){
		//Logging the exception for debugging and monitoring purposes
		logger.error("Rate Limiting Exception: {}", ex.getMessage());
		//Return an error response with custom message
		return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Rate Limit Exceed: " + ex.getMessage());
	}
	
}

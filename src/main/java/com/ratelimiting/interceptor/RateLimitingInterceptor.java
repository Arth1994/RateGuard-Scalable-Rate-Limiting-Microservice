package com.ratelimiting.interceptor;

import java.io.IOException;

import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ratelimiting.exception.RateLimitingException;
import com.ratelimiting.service.RateLimitingService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitingInterceptor implements HandlerInterceptor {
	
	@Autowired
	RateLimitingService rateLimitingService;
	
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RateLimitingInterceptor.class);

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws RateLimitingException, IOException{
		String serviceIdentifier = request.getHeader("Service-Identifier");
		if(serviceIdentifier == null || serviceIdentifier.isEmpty()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("Service-Identifier is Missing");
			return false;
		}
		String method = request.getMethod();
		String url = request.getRequestURI();
		String remoteAddress = request.getRemoteAddr();
		logger.info("Request: {} {} from {}", method, url, remoteAddress);
		return true;
	}
}

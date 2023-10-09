package com.ratelimiting.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ratelimiting.config.RateLimitConfigDto;
import com.ratelimiting.exception.RateLimitingException;
import com.ratelimiting.service.RateLimitingService;

@RestController
@RequestMapping("/middleware/rate-limitng")
public class RateLimitingController {
	
	@SuppressWarnings("unused")
	private  RateLimitingService rateLimitingService;
	

	public RateLimitingController(RateLimitingService rateLimitingService) {
		this.rateLimitingService = rateLimitingService;
	}
	
	@PostMapping("/configure")
	public ResponseEntity<String> configureRateLimit(@RequestHeader("Service-Identifier") String clientId, @RequestBody RateLimitConfigDto dto) throws RateLimitingException{
		rateLimitingService.configureRateLimit(clientId, dto);
		return ResponseEntity.status(HttpStatus.OK).body("RateLimiting is Configured for service id: "+clientId);
	}
	
	@GetMapping("/check")
	public ResponseEntity<String> checkRateLimited(@RequestHeader("Service-Identifier") String serviceId) throws RateLimitingException{
		boolean isAllowed = rateLimitingService.isRequestAllowed(serviceId);
		if(isAllowed) {
			return ResponseEntity.status(HttpStatus.OK).body("Request Allowed");
		}else {
			return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Rate limit exceeded. Please try again later.");
		}
	}
	
}

package com.ratelimiting.algorithm;

import org.springframework.stereotype.Component;

@Component
public class TokenBucketLimitingStrategy implements RateLimitingStrategy {

	@Override
	public boolean allowRequest(String key) {
		// TODO Auto-generated method stub
		return false;
	}

}

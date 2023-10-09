package com.ratelimiting.algorithm;

/**
 * We will extend this interface to develop different rate limiting strategies 
 * in the future
 */
public interface RateLimitingStrategy{
	boolean allowRequest(String key);
}

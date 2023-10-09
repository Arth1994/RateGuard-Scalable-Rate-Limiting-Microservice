package com.ratelimiting.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Rate Limiting in the Spring Boot application.
 *
 * This configuration class defines Spring beans related to rate limiting, such as the token bucket storage.
 * Rate limiting is used to control the rate of incoming requests based on IP addresses.
 * When a request arrives, the token bucket for the corresponding IP address is checked, and the request
 * is allowed or rejected based on the availability of tokens in the bucket.
 *
 * The `ConcurrentHashMap` bean defined in this class is used to store token buckets for different IP addresses.
 * Each token bucket represents the rate limit for a specific IP address, ensuring fair access to resources.
 *
 * Note: This configuration is an example and may need to be adapted to your specific rate limiting requirements.
 *
 * @see TokenBucket
 */
@Configuration
public class RateLimitingConfig {

    private final Map<String, TokenBucket> tokenBuckets = new ConcurrentHashMap<>();

    public TokenBucket getTokenBucket(String serviceIdentifier) {
        return tokenBuckets.get(serviceIdentifier);
    }

    public void configureTokenBucket(String serviceIdentifier, TokenBucket tokenBucket) {
        tokenBuckets.put(serviceIdentifier, tokenBucket);
    }

    public void removeTokenBucket(String serviceIdentifier) {
        tokenBuckets.remove(serviceIdentifier);
    }
}

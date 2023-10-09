package com.ratelimiting.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ratelimiting.config.RateLimitConfigDto;
import com.ratelimiting.config.RateLimitingConfig;
import com.ratelimiting.config.TokenBucket;

@Service
public class RateLimitingService {

    private final RateLimitingConfig config;

    @Value("${ratelimit.defaultCapacity}")
    private int defaultCapacity;

    @Value("${ratelimit.defaultRefillRate}")
    private int defaultRefillRate;


    public RateLimitingService(RateLimitingConfig config) {
        this.config = config;
    }

    /**
     * Configures rate limiting for a specific client with the provided settings.
     * If the capacity or refill rate is not provided (zero or negative), default values are used.
     *
     * @param clientId   The identifier for the client/service.
     * @param configDto  Rate limit configuration for the client.
     */
    public void configureRateLimit(String clientId, RateLimitConfigDto configDto) {
        int capacity = configDto.getCapacity();
        int refillRate = configDto.getRefillRate();

        // Use the provided rate limiting settings, or fall back to defaults
        int actualCapacity = capacity > 0 ? capacity : defaultCapacity;
        int actualRefillRate = refillRate > 0 ? refillRate : defaultRefillRate;

        // Create a TokenBucket with the configured settings
        TokenBucket tokenBucket = new TokenBucket(actualCapacity, actualRefillRate);

        // Configure the TokenBucket in the RateLimitingConfig
        config.configureTokenBucket(clientId, tokenBucket);
    }

    /**
     * Checks if a request is allowed for the given key (client or service identifier).
     *
     * @param key The key (client or service identifier) for rate limiting.
     * @return True if the request is allowed based on rate limiting, false otherwise.
     */
    public boolean isRequestAllowed(String key) {
        TokenBucket tokenBucket = config.getTokenBucket(key);

        if (tokenBucket == null) {
            // Rate limiting is not configured for this service, allow all requests
            return true;
        }

        // Consume tokens from the TokenBucket and return true if allowed
        return tokenBucket.consumeTokens(key);
    }
}

package com.ratelimiting.config;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Represents a token bucket used for rate limiting.
 *
 * A token bucket is used to control the rate of incoming requests based on some predefined capacity
 * and refill rate. Requests can be processed if there are tokens available in the bucket.
 */
public class TokenBucket {
	
	// The maximum capacity of the token bucket.
	private final int capacity;
	// The current number of tokens in the bucket.
	private AtomicLong tokens;
	// The time stamp when tokens were last refilled.
	private Instant lastRefillTime;
	//Refill Rate of Tokens
	private int refillRate;
	
    /**
     * Constructs a new token bucket with the specified capacity.
     * @param capacity The maximum capacity of the token bucket.
     */
	public TokenBucket(int capacity, int refillRate) {
		this.capacity = capacity;
		this.tokens = new AtomicLong(capacity);
		this.refillRate = refillRate;
		this.lastRefillTime = Instant.now();
	}
	
    /**
     * Attempts to consume tokens from the token bucket.
     *
     * @param tokensToConsume The number of tokens to consume.
     * @return `true` if there are enough tokens to consume; `false` otherwise.
     */
	public synchronized boolean consumeTokens(String clientId) {
		// Ensure tokens are refilled before consumption.
		refillTokens();
		if(tokens.get() > 0) {
			tokens.decrementAndGet();
			// There are enough tokens to consume.
			return true;
		}
		return false;
	}
	
    /**
     * Refills tokens in the bucket based on the refill rate.
     */
	private synchronized void refillTokens() {
		Instant now = Instant.now();
		long timeElapased = now.toEpochMilli() - lastRefillTime.toEpochMilli();
		// Adding tokens based on refill rate per second
		long tokensToAdd = timeElapased * refillRate / 1000; 
		if(tokensToAdd > 0) {
			tokens.set(Math.min(capacity, tokensToAdd + tokens.get()));
			lastRefillTime = now;
		}
	}
	
	
    /**
     * Gets the current number of tokens in the token bucket.
     *
     * @return The number of tokens.
     */
	public long getTokens() {
		return tokens.get();
	}
	
}

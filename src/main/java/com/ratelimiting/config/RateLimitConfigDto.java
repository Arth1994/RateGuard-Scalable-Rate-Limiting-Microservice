package com.ratelimiting.config;

public class RateLimitConfigDto {
	
    private int capacity;
    private int refillRate;
    
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getRefillRate() {
		return refillRate;
	}
	public void setRefillRate(int refillRate) {
		this.refillRate = refillRate;
	}

}

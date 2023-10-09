package com.ratelimitng;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.eq;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.ratelimiting.config.RateLimitConfigDto;
import com.ratelimiting.config.RateLimitingConfig;
import com.ratelimiting.config.TokenBucket;
import com.ratelimiting.service.RateLimitingService;

@SpringBootTest
@ContextConfiguration(classes = { RateLimitingConfig.class })
public class RateLimitingServiceIntegrationTest {
	
    private RateLimitingService rateLimitingService;
    
    @Mock
    private RateLimitingConfig config;
    
    @BeforeEach
    public void setUp() {
    	MockitoAnnotations.initMocks(this);
    	this.rateLimitingService = new RateLimitingService(this.config);
    }
    
    @Test
    public void testConfiguredRateLimit() {
    	String clientId = "AuthenticationMicroservice";
    	RateLimitConfigDto configDto = new RateLimitConfigDto();
        configDto.setCapacity(10);
        configDto.setRefillRate(5);
        rateLimitingService.configureRateLimit(clientId, configDto);
        verify(config).configureTokenBucket(eq(clientId), (TokenBucket) any(TokenBucket.class));
    }


}

package com.ratelimiting.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Autowired
	private RateLimitingInterceptor rateLimitingInterceptor;
	
	public WebMvcConfig(RateLimitingInterceptor rateLimitingInterceptor) {
		this.rateLimitingInterceptor = rateLimitingInterceptor;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(rateLimitingInterceptor).addPathPatterns("/middleware/rate-limitng");
	}
	
}

package com.demo.ReactiveCircuitBreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer;
import io.github.resilience4j.common.timelimiter.configuration.TimeLimiterConfigCustomizer;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.util.function.Function;

@SpringBootApplication
public class ReactiveCircuitBreakerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveCircuitBreakerApplication.class, args);
	}

	/*
	@Bean
	static TimeLimiterConfigCustomizer customizer(){
		return TimeLimiterConfigCustomizer.of("cbtest", builder -> builder.timeoutDuration(Duration.ofMillis(5000)).build());
	}*/
	//Backup of resilience4j.timelimiter.instances. Also supersedes the settings defined in application,yml
}

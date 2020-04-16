package com.demo.ReactiveCircuitBreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.util.function.Function;

@SpringBootApplication
public class ReactiveCircuitBreakerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveCircuitBreakerApplication.class, args);
	}

	@Bean
	ReactiveCircuitBreakerFactory circuitBreakerFactory(){
		var factory = new ReactiveResilience4JCircuitBreakerFactory();
		factory.configureDefault((Function<String, Resilience4JConfigBuilder.Resilience4JCircuitBreakerConfiguration>)
				s -> new Resilience4JConfigBuilder(s)
				.timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(5)).build())
				.circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
				.build());
		return factory;
	}
}

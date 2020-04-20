package com.demo.ReactiveCircuitBreaker.service;

import com.demo.ReactiveCircuitBreaker.exception.RecordDoesNotExistException;
import com.demo.ReactiveCircuitBreaker.model.DemoModel;
import com.demo.ReactiveCircuitBreaker.repository.CircuitBreakerRepository;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@Log4j2
public class CircuitBreakerService {
    private static final String DEFAULT = "cbtest";

    @Autowired
    private CircuitBreakerRepository cbRepo;


    @TimeLimiter(name = DEFAULT)
    @CircuitBreaker(name = DEFAULT, fallbackMethod = "monoFallback")
    @Retry(name = DEFAULT)
    @Bulkhead(name = DEFAULT)
    //Method used to retry a string return. Used for recording metrics in Grafana
    //Aspect ordering is changed in application.yml. This is done so that retry activates before circuitbreaker does
    public Mono<String> greet(String name){

        long seconds = (long) (Math.random() * 10);
        log.info("Seconds used for request = {}", seconds);
        return Mono.just("Hello " + name + "!").delayElement(Duration.ofSeconds(6));
    }

    @TimeLimiter(name = DEFAULT)
    @CircuitBreaker(name = DEFAULT, fallbackMethod = "monoFallback")
    public Mono<DemoModel> getByName(String name){
        return cbRepo.getByName(name).switchIfEmpty(Mono.error(new RecordDoesNotExistException("Record does not exist!")));
    }


    @TimeLimiter(name = DEFAULT)
    @CircuitBreaker(name = DEFAULT, fallbackMethod = "monoFallback")
    @Retry(name = DEFAULT)
    public Mono<DemoModel> setRecord(@NonNull DemoModel model){
        long seconds = (long) (Math.random() * 10);
        log.info("Seconds used for request = {}", seconds);
        return cbRepo.save(model).delayElement(Duration.ofSeconds(seconds));
    }

    public Mono<String> monoFallback(Exception e){
        log.warn("Fallback method triggered " + e.toString());
        return Mono.just("Recovered from fallback " + e.toString());
    }
    public Mono<String> retryFallback(String name, Exception e){
        log.warn("Fallback method triggered from timeout, cause - {}", e.toString());
        return Mono.just("Hello " + name + " [FALLBACK SUCCESS!]");
    }
}

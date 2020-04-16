package com.demo.ReactiveCircuitBreaker;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;

import java.time.Duration;
import java.util.Optional;
import java.util.logging.Level;

@Service
@Log4j2
public class CircuitBreakerService {
    @Autowired
    private CircuitBreakerRepository cbRepo;
    public Mono<String> greet(Optional<String> name){
        return name.map(str -> Mono.just("Hello " + str)).orElse(Mono.error(new NullPointerException()));
    }
    public Mono<DemoModel> getByName(Optional<String> name){
        Mono<DemoModel> fallback = Mono.error(new Exception("Name cannot be found " + name));
        return cbRepo.getByName(name).switchIfEmpty(fallback).log(log.getName());
    }
    public Mono<DemoModel> setRecord(DemoModel model){
        Mono<DemoModel> fallback = Mono.error(new NullPointerException("Record cannot be input" + model));
        return cbRepo.save(model).switchIfEmpty(fallback).log(log.getName());
    }
}

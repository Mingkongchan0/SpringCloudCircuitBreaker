package com.demo.ReactiveCircuitBreaker;

import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.function.Function;

@RestController
public class CircuitBreakerController {
    private CircuitBreakerService cbs;
    private ReactiveCircuitBreaker cb;

    public CircuitBreakerController(CircuitBreakerService cbService, ReactiveCircuitBreakerFactory cbf) {
        this.cb = cbf.create("test");
        this.cbs = cbService;
    }

    @GetMapping("greet")
    public Mono<String> getGreeting(Optional<String> name){
        Mono<String> results = this.cbs.greet(name);
        return this.cb.run(results, throwable -> Mono.just("Hello :O"));
    }
    @GetMapping("/getByName/{name}")
    public Mono<DemoModel> getByName(@PathVariable String name){
        Mono<DemoModel> results = this.cbs.getByName(Optional.ofNullable(name));
        return this.cb.run(results, throwable -> Mono.just(new DemoModel()));
    }
    @PostMapping("/set")
    public Mono<DemoModel> setRecord(@RequestBody DemoModel model){
        Mono<DemoModel> results = this.cbs.setRecord(model);
        return this.cb.run(results, throwable -> Mono.just(new DemoModel()));
    }
}

package com.demo.ReactiveCircuitBreaker.controller;

import com.demo.ReactiveCircuitBreaker.service.CircuitBreakerService;
import com.demo.ReactiveCircuitBreaker.model.DemoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class CircuitBreakerController{

    @Autowired
    private CircuitBreakerService cbService;



    @GetMapping("greet")
    public Mono<String> getGreeting(String name){
        return cbService.greet(name);
    }
    @GetMapping("/getByName/{name}")
    public Mono<DemoModel> getByName(@PathVariable String name){
        return cbService.getByName(name);
    }
    @PostMapping("/set")
    public Mono<DemoModel> setRecord(@RequestBody DemoModel model){
        return cbService.setRecord(model);
    }
}

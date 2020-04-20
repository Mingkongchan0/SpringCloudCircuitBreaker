package com.demo.ReactiveCircuitBreaker.repository;

import com.demo.ReactiveCircuitBreaker.model.DemoModel;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CircuitBreakerRepository extends ReactiveMongoRepository<DemoModel, String> {
    Mono<DemoModel> getByName(String name);
    Mono<Boolean> findByName(String name);
}

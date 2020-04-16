package com.demo.ReactiveCircuitBreaker;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface CircuitBreakerRepository extends ReactiveMongoRepository<DemoModel, String> {
    Mono<DemoModel> getByName(Optional<String> name);
}

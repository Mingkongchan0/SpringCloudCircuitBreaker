package com.demo.ReactiveCircuitBreaker.exception;

public class RecordDoesNotExistException extends RuntimeException {
    public RecordDoesNotExistException(String message){
        super(message);
    }
}

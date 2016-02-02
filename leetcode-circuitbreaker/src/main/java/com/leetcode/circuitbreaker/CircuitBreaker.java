package com.leetcode.circuitbreaker;


/**
 * A circuit breaker acts as a proxy for operations that may fail.
 * The proxy should monitor the number of recent failures that have occurred,
 * and then use this information to decide whether to allow the operation to proceed,
 * or simply return an exception immediately.
 * Circuit breaker logic is hooked into {@link HystrixCommand} execution to protect service.
 * @author fang_j
 *
 */
public interface CircuitBreaker {
	/**
	 * To determine whether or not to allow {@link HystrixCommand} request to execute.
	 * @return {@code true} if allow this request to be executed,otherwise {@code false}.
	 */
	boolean allowRequest();

	/**
	 * To determine whether or not open circuit breaker.
	 * @return {@code true} if circuit breaker is open, otherwise {@code false}.
	 */
	boolean isOpen();

	/**
	 * When circuit breaker in a half-open state and a {@link HystrixCommand} request execute successfully, open circuit breaker.
	 */
	void markSuccess();
}

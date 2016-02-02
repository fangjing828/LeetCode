package com.leetcode.circuitbreaker;


/**
 * An implementation of the circuit breaker that does nothing.
 * Used if circuit breaker is disabled for a command.
 * @author fang_j
 *
 */
public class NoOpCircuitBreaker implements CircuitBreaker{
	@Override
	public boolean allowRequest() {
		return true;
	}

	@Override
	public boolean isOpen() {
		return false;
	}

	@Override
	public void markSuccess() {
	}
}
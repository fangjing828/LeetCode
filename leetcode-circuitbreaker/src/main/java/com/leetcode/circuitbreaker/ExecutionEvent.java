package com.leetcode.circuitbreaker;

public enum ExecutionEvent {
	/**
	 * When a {@link HystrixCommand} successfully completes.
	 */
	Success,

	/**
	 * When a {@link HystrixCommand} times out (fails to complete).
	 */
	Timeout,

	/**
	 * When a {@link HystrixCommand} performs a short-circuited fallback.
	 */
	ShortCircuited,

	/**
	 * When Baiji throws an Framework exception.
	 */
	FrameworkExceptionThrown,

	/**
	 * When Baiji throws an Service exception.
	 */
	ServiceExceptionThrown,

	/**
	 * When CServiceStack throws an Validation exception.
	 */
	ValidationExceptionThrown,
}


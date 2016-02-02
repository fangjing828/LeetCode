package com.leetcode.circuitbreaker;

import org.junit.Assert;
import org.junit.Test;

public class NoOpCircuitBreakerTest {
	private final NoOpCircuitBreaker circuitBreaker = new NoOpCircuitBreaker();

	@Test
	public void allowRequestTest() {
		Assert.assertEquals(true, this.circuitBreaker.allowRequest());
	}

	@Test
	public void isOpenTest() {
		Assert.assertEquals(false, this.circuitBreaker.isOpen());
	}
}

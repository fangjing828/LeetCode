package com.leetcode.circuitbreaker.metrics;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.leetcode.circuitbreaker.metrics.HealthCounts;

public class HealthCountsTest {
	private long successCount;
	private long timeoutCount;
	private long shortCircuitedCount;
	private long frameworkExceptionCount;
	private long serviceExceptionCount;
	private long validationExceptionCount;

	@Before
	public void init() {
		this.successCount = this.random();
		this.timeoutCount = this.random();
		this.shortCircuitedCount = this.random();
		this.frameworkExceptionCount = this.random();
		this.serviceExceptionCount = this.random();
		this.validationExceptionCount = this.random();
	}

	@Test
	public void consistencyTest () {
		final HealthCounts healthcounts = new HealthCounts(this.successCount, this.timeoutCount, this.shortCircuitedCount
				, this.frameworkExceptionCount, this.serviceExceptionCount, this.validationExceptionCount);
		Assert.assertEquals(this.successCount, healthcounts.successCount());
		Assert.assertEquals(this.timeoutCount, healthcounts.timeoutCount());
		Assert.assertEquals(this.frameworkExceptionCount, healthcounts.frameworkExceptionCount());
		Assert.assertEquals(this.serviceExceptionCount, healthcounts.serviceExceptionCount());
		Assert.assertEquals(this.validationExceptionCount, healthcounts.validationExceptionCount());
	}

	@Test
	public void functionalityTest() {
		final HealthCounts healthcounts = new HealthCounts(this.successCount, this.timeoutCount, this.shortCircuitedCount
				, this.frameworkExceptionCount, this.serviceExceptionCount, this.validationExceptionCount);

		final long totalRequests = this.successCount + this.timeoutCount + this.shortCircuitedCount
				+ this.frameworkExceptionCount + this.serviceExceptionCount + this.validationExceptionCount;

		final long totalErrorCount = this.timeoutCount + this.shortCircuitedCount
				+ this.frameworkExceptionCount + this.serviceExceptionCount;
		final long totalExceptionCount = this.shortCircuitedCount + this.frameworkExceptionCount
				+ this.serviceExceptionCount + this.validationExceptionCount;
		final long totalFailureCount = this.frameworkExceptionCount + this.serviceExceptionCount;

		Assert.assertEquals(totalRequests, healthcounts.totalRequests());
		Assert.assertEquals(totalErrorCount, healthcounts.totalErrorCount());
		Assert.assertEquals(totalExceptionCount, healthcounts.totalExceptionCount());
		Assert.assertEquals(totalFailureCount, healthcounts.totalFailureCount());
		final long errorPercentage = healthcounts.errorPercentage();
		Assert.assertEquals(true, (errorPercentage >= 0) && (errorPercentage <= 100));
	}

	private long random() {
		return (long)(Math.random() * 1000);
	}
}


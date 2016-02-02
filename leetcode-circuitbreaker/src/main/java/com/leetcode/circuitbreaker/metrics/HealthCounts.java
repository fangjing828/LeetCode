package com.leetcode.circuitbreaker.metrics;


/**
 *  Statistical information of {@link HystrixCommand} health metrics.
 * @author fang_j
 *
 */
public class HealthCounts {
	private long totalRequests;
	private long totalErrorCount;
	private long totalExceptionCount;
	private long totalFailureCount;
	private long totalSuccessCount;
	private int errorPercentage;
	private long successCount;
	private long timeoutCount;
	private long shortCircuitedCount;
	private long frameworkExceptionCount;
	private long serviceExceptionCount;
	private long validationExceptionCount;

	public HealthCounts()
	{
	}

	public HealthCounts(final long successCount, final long timeoutCount, final long shortCircuitedCount,
			final long frameworkExceptionCount, final long serviceExceptionCount, final long validationExceptionCount)
	{
		this.successCount(successCount)
		.timeoutCount(timeoutCount)
		.shortCircuitedCount(shortCircuitedCount)
		.frameworkExceptionCount(frameworkExceptionCount)
		.serviceExceptionCount(serviceExceptionCount)
		.validationExceptionCount(validationExceptionCount);

		this.totalRequests(this.successCount()
				+ this.timeoutCount()
				+ this.shortCircuitedCount()
				+ this.frameworkExceptionCount()
				+ this.serviceExceptionCount()
				+ this.validationExceptionCount());

		this.totalErrorCount(this.timeoutCount()
				+ this.shortCircuitedCount()
				+ this.frameworkExceptionCount()
				+ this.serviceExceptionCount());

		this.totalExceptionCount(this.shortCircuitedCount()
				+ this.frameworkExceptionCount()
				+ this.serviceExceptionCount()
				+ this.validationExceptionCount());

		this.totalFailureCount(this.frameworkExceptionCount()
				+ this.serviceExceptionCount());

		if (this.totalRequests() > 0) {
			this.errorPercentage((int)(((double) this.totalErrorCount() / this.totalRequests()) * 100));
		}

	}

	public long totalRequests() {
		return this.totalRequests;
	}

	public HealthCounts totalRequests(final long totalRequests) {
		this.totalRequests = totalRequests;
		return this;
	}

	public long totalErrorCount() {
		return this.totalErrorCount;
	}

	public HealthCounts totalErrorCount(final long totalErrorCount) {
		this.totalErrorCount = totalErrorCount;
		return this;
	}

	public long totalExceptionCount() {
		return this.totalExceptionCount;
	}

	public HealthCounts totalExceptionCount(final long totalExceptionCount) {
		this.totalExceptionCount = totalExceptionCount;
		return this;
	}

	public long totalFailureCount() {
		return this.totalFailureCount;
	}

	public HealthCounts totalFailureCount(final long totalFailureCount) {
		this.totalFailureCount = totalFailureCount;
		return this;
	}

	public long totalSuccessCount() {
		return this.totalSuccessCount;
	}

	public HealthCounts totalSuccessCount(final long totalSuccessCount) {
		this.totalSuccessCount = totalSuccessCount;
		return this;
	}

	public int errorPercentage() {
		return this.errorPercentage;
	}

	public HealthCounts errorPercentage(final int errorPercentage) {
		this.errorPercentage = errorPercentage;
		return this;
	}

	public long successCount() {
		return this.successCount;
	}

	private HealthCounts successCount(final long successCount) {
		this.successCount = successCount;
		return this;
	}

	public long timeoutCount() {
		return this.timeoutCount;
	}

	private HealthCounts timeoutCount(final long timeoutCount) {
		this.timeoutCount = timeoutCount;
		return this;
	}

	public long shortCircuitedCount() {
		return this.shortCircuitedCount;
	}

	private HealthCounts shortCircuitedCount(final long shortCircuitedCount) {
		this.shortCircuitedCount = shortCircuitedCount;
		return this;
	}

	public long frameworkExceptionCount() {
		return this.frameworkExceptionCount;
	}

	private HealthCounts frameworkExceptionCount(final long frameworkExceptionCount) {
		this.frameworkExceptionCount = frameworkExceptionCount;
		return this;
	}

	public long serviceExceptionCount() {
		return this.serviceExceptionCount;
	}

	private HealthCounts serviceExceptionCount(final long serviceExceptionCount) {
		this.serviceExceptionCount = serviceExceptionCount;
		return this;
	}

	public long validationExceptionCount() {
		return this.validationExceptionCount;
	}

	private HealthCounts validationExceptionCount(final long validationExceptionCount) {
		this.validationExceptionCount = validationExceptionCount;
		return this;
	}
}

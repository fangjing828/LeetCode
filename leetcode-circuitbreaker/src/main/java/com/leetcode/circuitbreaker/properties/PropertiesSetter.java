package com.leetcode.circuitbreaker.properties;

public class PropertiesSetter {
	private Boolean circuitBreakerEnabled;
	private Integer circuitBreakerErrorThresholdPercentage;
	private Boolean circuitBreakerForceClosed;
	private Boolean circuitBreakerForceOpen;
	private Integer circuitBreakerRequestCountThreshold;
	private Integer circuitBreakerSleepWindowInMilliseconds;

	private Integer executionIsolationThreadTimeoutInMilliseconds;

	private Integer metricsHealthSnapshotIntervalInMilliseconds;

	private Integer metricsRollingStatisticalWindowInMilliseconds;
	private Integer metricsRollingStatisticalWindowBuckets;
	private Integer metricsRollingPercentileWindowInMilliseconds;
	private Integer metricsRollingPercentileWindowBuckets;
	private Integer metricsRollingPercentileBucketSize;

	public Boolean getCircuitBreakerEnabled() {
		return this.circuitBreakerEnabled;
	}
	public void setCircuitBreakerEnabled(final Boolean circuitBreakerEnabled) {
		this.circuitBreakerEnabled = circuitBreakerEnabled;
	}
	public Integer getCircuitBreakerErrorThresholdPercentage() {
		return this.circuitBreakerErrorThresholdPercentage;
	}
	public void setCircuitBreakerErrorThresholdPercentage(
			final Integer circuitBreakerErrorThresholdPercentage) {
		this.circuitBreakerErrorThresholdPercentage = circuitBreakerErrorThresholdPercentage;
	}
	public Boolean getCircuitBreakerForceClosed() {
		return this.circuitBreakerForceClosed;
	}
	public void setCircuitBreakerForceClosed(final Boolean circuitBreakerForceClosed) {
		this.circuitBreakerForceClosed = circuitBreakerForceClosed;
	}
	public Boolean getCircuitBreakerForceOpen() {
		return this.circuitBreakerForceOpen;
	}
	public void setCircuitBreakerForceOpen(final Boolean circuitBreakerForceOpen) {
		this.circuitBreakerForceOpen = circuitBreakerForceOpen;
	}
	public Integer getCircuitBreakerRequestCountThreshold() {
		return this.circuitBreakerRequestCountThreshold;
	}
	public void setCircuitBreakerRequestCountThreshold(
			final Integer circuitBreakerRequestCountThreshold) {
		this.circuitBreakerRequestCountThreshold = circuitBreakerRequestCountThreshold;
	}
	public Integer getCircuitBreakerSleepWindowInMilliseconds() {
		return this.circuitBreakerSleepWindowInMilliseconds;
	}
	public void setCircuitBreakerSleepWindowInMilliseconds(
			final Integer circuitBreakerSleepWindowInMilliseconds) {
		this.circuitBreakerSleepWindowInMilliseconds = circuitBreakerSleepWindowInMilliseconds;
	}
	public Integer getExecutionIsolationThreadTimeoutInMilliseconds() {
		return this.executionIsolationThreadTimeoutInMilliseconds;
	}
	public void setExecutionIsolationThreadTimeoutInMilliseconds(
			final Integer executionIsolationThreadTimeoutInMilliseconds) {
		this.executionIsolationThreadTimeoutInMilliseconds = executionIsolationThreadTimeoutInMilliseconds;
	}
	public Integer getMetricsHealthSnapshotIntervalInMilliseconds() {
		return this.metricsHealthSnapshotIntervalInMilliseconds;
	}
	public void setMetricsHealthSnapshotIntervalInMilliseconds(
			final Integer metricsHealthSnapshotIntervalInMilliseconds) {
		this.metricsHealthSnapshotIntervalInMilliseconds = metricsHealthSnapshotIntervalInMilliseconds;
	}
	public Integer getMetricsRollingStatisticalWindowInMilliseconds() {
		return this.metricsRollingStatisticalWindowInMilliseconds;
	}
	public void setMetricsRollingStatisticalWindowInMilliseconds(
			final Integer metricsRollingStatisticalWindowInMilliseconds) {
		this.metricsRollingStatisticalWindowInMilliseconds = metricsRollingStatisticalWindowInMilliseconds;
	}
	public Integer getMetricsRollingStatisticalWindowBuckets() {
		return this.metricsRollingStatisticalWindowBuckets;
	}
	public void setMetricsRollingStatisticalWindowBuckets(
			final Integer metricsRollingStatisticalWindowBuckets) {
		this.metricsRollingStatisticalWindowBuckets = metricsRollingStatisticalWindowBuckets;
	}
	public Integer getMetricsRollingPercentileWindowInMilliseconds() {
		return this.metricsRollingPercentileWindowInMilliseconds;
	}
	public void setMetricsRollingPercentileWindowInMilliseconds(
			final Integer metricsRollingPercentileWindowInMilliseconds) {
		this.metricsRollingPercentileWindowInMilliseconds = metricsRollingPercentileWindowInMilliseconds;
	}
	public Integer getMetricsRollingPercentileWindowBuckets() {
		return this.metricsRollingPercentileWindowBuckets;
	}
	public void setMetricsRollingPercentileWindowBuckets(
			final Integer metricsRollingPercentileWindowBuckets) {
		this.metricsRollingPercentileWindowBuckets = metricsRollingPercentileWindowBuckets;
	}
	public Integer getMetricsRollingPercentileBucketSize() {
		return this.metricsRollingPercentileBucketSize;
	}
	public void setMetricsRollingPercentileBucketSize(
			final Integer metricsRollingPercentileBucketSize) {
		this.metricsRollingPercentileBucketSize = metricsRollingPercentileBucketSize;
	}
}

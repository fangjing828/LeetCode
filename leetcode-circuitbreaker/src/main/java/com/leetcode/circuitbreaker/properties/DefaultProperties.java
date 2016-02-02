package com.leetcode.circuitbreaker.properties;

import com.leetcode.property.DynamicProperty;
import com.leetcode.property.Property;
import com.leetcode.property.PropertyFactory;

public class DefaultProperties implements Properties{
	private static final Property<Boolean> circuitBreakerEnabled = PropertyFactory.asProperty(true);
	private static final Property<Integer> circuitBreakerErrorThresholdPercentage = PropertyFactory.asProperty(50);
	private static final DynamicProperty<Boolean> circuitBreakerForceClosed = PropertyFactory.asDynamicProperty(false);
	private static final DynamicProperty<Boolean> circuitBreakerForceOpen = PropertyFactory.asDynamicProperty(false);
	private static final Property<Integer> circuitBreakerRequestCountThreshold = PropertyFactory.asProperty(20);
	private static final Property<Integer> circuitBreakerSleepWindowInMilliseconds = PropertyFactory.asProperty(5 * 1000);

	private static final DynamicProperty<Integer> executionIsolationThreadTimeoutInMilliseconds = PropertyFactory.asDynamicProperty(2 * 1000);

	private static final Property<Integer> metricsHealthSnapshotIntervalInMilliseconds = PropertyFactory.asProperty(500);

	private static final Property<Integer> metricsRollingStatisticalWindowInMilliseconds = PropertyFactory.asProperty(10 * 1000);
	private static final Property<Integer> metricsRollingStatisticalWindowBuckets = PropertyFactory.asProperty(10);
	private static final Property<Integer> metricsRollingPercentileWindowInMilliseconds = PropertyFactory.asProperty(60 * 1000);
	private static final Property<Integer> metricsRollingPercentileWindowBuckets = PropertyFactory.asProperty(6);
	private static final Property<Integer> metricsRollingPercentileBucketSize = PropertyFactory.asProperty(200);

	@Override
	public Property<Boolean> circuitBreakerEnabled() {
		return circuitBreakerEnabled;
	}

	@Override
	public Property<Integer> circuitBreakerErrorThresholdPercentage() {
		return circuitBreakerErrorThresholdPercentage;
	}

	@Override
	public DynamicProperty<Boolean> circuitBreakerForceClosed() {
		return circuitBreakerForceClosed;
	}

	@Override
	public DynamicProperty<Boolean> circuitBreakerForceOpen() {
		return circuitBreakerForceOpen;
	}

	@Override
	public Property<Integer> circuitBreakerRequestCountThreshold() {
		return circuitBreakerRequestCountThreshold;
	}

	@Override
	public Property<Integer> circuitBreakerSleepWindowInMilliseconds() {
		return circuitBreakerSleepWindowInMilliseconds;
	}

	@Override
	public DynamicProperty<Integer> executionIsolationThreadTimeoutInMilliseconds() {
		return executionIsolationThreadTimeoutInMilliseconds;
	}

	@Override
	public Property<Integer> metricsHealthSnapshotIntervalInMilliseconds() {
		return metricsHealthSnapshotIntervalInMilliseconds;
	}

	@Override
	public Property<Integer> metricsRollingPercentileWindowInMilliseconds() {
		return metricsRollingPercentileWindowInMilliseconds;
	}

	@Override
	public Property<Integer> metricsRollingPercentileWindowBuckets() {
		return metricsRollingPercentileWindowBuckets;
	}

	@Override
	public Property<Integer> metricsRollingPercentileBucketSize() {
		return metricsRollingPercentileBucketSize;
	}

	@Override
	public Property<Integer> metricsRollingStatisticalWindowInMilliseconds() {
		return metricsRollingStatisticalWindowInMilliseconds;
	}

	@Override
	public Property<Integer> metricsRollingStatisticalWindowBuckets() {
		return metricsRollingStatisticalWindowBuckets;
	}

}

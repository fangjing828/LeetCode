package com.leetcode.circuitbreaker.properties;

import com.leetcode.property.DynamicProperty;
import com.leetcode.property.Property;
import com.leetcode.property.PropertyFactory;

/**
 * 
 * @author fang_j
 *
 */
public class CustomProperties implements Properties{
	private static final DefaultProperties defaultProperties = new DefaultProperties();

	private final Property<Boolean> circuitBreakerEnabled;
	private final Property<Integer> circuitBreakerErrorThresholdPercentage;
	private final DynamicProperty<Boolean> circuitBreakerForceClosed;
	private final DynamicProperty<Boolean> circuitBreakerForceOpen;
	private final Property<Integer> circuitBreakerRequestCountThreshold;
	private final Property<Integer> circuitBreakerSleepWindowInMilliseconds;

	private final DynamicProperty<Integer> executionIsolationThreadTimeoutInMilliseconds;

	private final Property<Integer> metricsHealthSnapshotIntervalInMilliseconds;

	private final Property<Integer> metricsRollingStatisticalWindowInMilliseconds;
	private final Property<Integer> metricsRollingStatisticalWindowBuckets;
	private final Property<Integer> metricsRollingPercentileWindowInMilliseconds;
	private final Property<Integer> metricsRollingPercentileWindowBuckets;
	private final Property<Integer> metricsRollingPercentileBucketSize;

	public CustomProperties(final PropertiesSetter setter) {
		if (setter == null) {
			throw new IllegalArgumentException("Properties setter must not null.");
		}

		this.circuitBreakerEnabled = PropertyFactory.asProperty(setter.getCircuitBreakerEnabled(), defaultProperties.circuitBreakerEnabled().get());
		this.circuitBreakerErrorThresholdPercentage = PropertyFactory.asProperty(setter.getCircuitBreakerErrorThresholdPercentage(), defaultProperties.circuitBreakerErrorThresholdPercentage().get());
		this.circuitBreakerForceClosed = PropertyFactory.asDynamicProperty(setter.getCircuitBreakerForceClosed(), defaultProperties.circuitBreakerForceClosed().get());
		this.circuitBreakerForceOpen = PropertyFactory.asDynamicProperty(setter.getCircuitBreakerForceOpen(), defaultProperties.circuitBreakerForceOpen().get());
		this.circuitBreakerRequestCountThreshold = PropertyFactory.asProperty(setter.getCircuitBreakerRequestCountThreshold(), defaultProperties.circuitBreakerRequestCountThreshold().get());
		this.circuitBreakerSleepWindowInMilliseconds = PropertyFactory.asProperty(setter.getCircuitBreakerSleepWindowInMilliseconds(), defaultProperties.circuitBreakerSleepWindowInMilliseconds().get());

		this.executionIsolationThreadTimeoutInMilliseconds = PropertyFactory.asDynamicProperty(setter.getExecutionIsolationThreadTimeoutInMilliseconds(), defaultProperties.executionIsolationThreadTimeoutInMilliseconds().get());

		this.metricsHealthSnapshotIntervalInMilliseconds = PropertyFactory.asProperty(setter.getMetricsHealthSnapshotIntervalInMilliseconds(), defaultProperties.metricsHealthSnapshotIntervalInMilliseconds().get());

		this.metricsRollingStatisticalWindowInMilliseconds = PropertyFactory.asProperty(setter.getMetricsRollingStatisticalWindowInMilliseconds(), defaultProperties.metricsRollingStatisticalWindowInMilliseconds().get());
		this.metricsRollingStatisticalWindowBuckets = PropertyFactory.asProperty(setter.getMetricsRollingStatisticalWindowBuckets(), defaultProperties.metricsRollingStatisticalWindowBuckets().get());
		this.metricsRollingPercentileWindowInMilliseconds = PropertyFactory.asProperty(setter.getMetricsRollingPercentileWindowInMilliseconds(), defaultProperties.metricsRollingPercentileWindowInMilliseconds().get());
		this.metricsRollingPercentileWindowBuckets = PropertyFactory.asProperty(setter.getMetricsRollingPercentileWindowBuckets(), defaultProperties.metricsRollingPercentileWindowBuckets().get());
		this.metricsRollingPercentileBucketSize = PropertyFactory.asProperty(setter.getMetricsRollingPercentileBucketSize(),  defaultProperties.metricsRollingPercentileBucketSize().get());
	}

	@Override
	public Property<Boolean> circuitBreakerEnabled() {
		return this.circuitBreakerEnabled;
	}

	@Override
	public Property<Integer> circuitBreakerErrorThresholdPercentage() {
		return this.circuitBreakerErrorThresholdPercentage;
	}

	@Override
	public DynamicProperty<Boolean> circuitBreakerForceClosed() {
		return this.circuitBreakerForceClosed;
	}

	@Override
	public DynamicProperty<Boolean> circuitBreakerForceOpen() {
		return this.circuitBreakerForceOpen;
	}

	@Override
	public Property<Integer> circuitBreakerRequestCountThreshold() {
		return this.circuitBreakerRequestCountThreshold;
	}

	@Override
	public Property<Integer> circuitBreakerSleepWindowInMilliseconds() {
		return this.circuitBreakerSleepWindowInMilliseconds;
	}

	@Override
	public DynamicProperty<Integer> executionIsolationThreadTimeoutInMilliseconds() {
		return this.executionIsolationThreadTimeoutInMilliseconds;
	}

	@Override
	public Property<Integer> metricsHealthSnapshotIntervalInMilliseconds() {
		return this.metricsHealthSnapshotIntervalInMilliseconds;
	}

	@Override
	public Property<Integer> metricsRollingPercentileWindowInMilliseconds() {
		return this.metricsRollingPercentileWindowInMilliseconds;
	}

	@Override
	public Property<Integer> metricsRollingPercentileWindowBuckets() {
		return this.metricsRollingPercentileWindowBuckets;
	}

	@Override
	public Property<Integer> metricsRollingPercentileBucketSize() {
		return this.metricsRollingPercentileBucketSize;
	}

	@Override
	public Property<Integer> metricsRollingStatisticalWindowInMilliseconds() {
		return this.metricsRollingStatisticalWindowInMilliseconds;
	}

	@Override
	public Property<Integer> metricsRollingStatisticalWindowBuckets() {
		return this.metricsRollingStatisticalWindowBuckets;
	}

}

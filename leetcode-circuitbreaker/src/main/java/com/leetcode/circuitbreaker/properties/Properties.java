package com.leetcode.circuitbreaker.properties;

import com.leetcode.property.DynamicProperty;
import com.leetcode.property.Property;

/**
 * 
 * @author fang_j
 *
 */
public interface Properties {
	/**
	 * Whether circuit breaker should be enabled.
	 * @return
	 */
	Property<Boolean> circuitBreakerEnabled();

	/**
	 * % of 'marks' that must be failed to trip the circuit.
	 * @return
	 */
	Property<Integer> circuitBreakerErrorThresholdPercentage();

	/**
	 * a property to allow ignoring errors and therefore never trip 'open' (ie. allow all traffic through).
	 * @return
	 */
	DynamicProperty<Boolean> circuitBreakerForceClosed();

	/**
	 * a property to allow forcing the circuit open (stopping all requests)
	 * @return
	 */
	DynamicProperty<Boolean> circuitBreakerForceOpen();

	/**
	 * number of requests that must be made within a statisticalWindow before open/close decisions are made using stats.
	 * @return
	 */
	Property<Integer> circuitBreakerRequestCountThreshold();

	/**
	 * milliseconds after tripping circuit before allowing retry
	 * @return
	 */
	Property<Integer> circuitBreakerSleepWindowInMilliseconds ();

	/**
	 * If isolation thread execute times more than this value, circuit breaker will mark this execution as timeout event.
	 * @return
	 */
	DynamicProperty<Integer> executionIsolationThreadTimeoutInMilliseconds();

	/**
	 * The minimum milliseconds interval to refresh health snapshots .
	 * @return
	 */
	Property<Integer> metricsHealthSnapshotIntervalInMilliseconds ();

	/**
	 * The maximum milliseconds that will be tracked in {@link IntegerPercentileBuffer}.
	 * @return
	 */
	Property<Integer> metricsRollingPercentileWindowInMilliseconds();

	/**
	 * The number of buckets in {@link IntegerPercentileBuffer}.
	 * @return
	 */
	Property<Integer> metricsRollingPercentileWindowBuckets();

	/**
	 * The maximum numbers of values to be allowed store in {@link PercentileBucket}.
	 * @return
	 */
	Property<Integer> metricsRollingPercentileBucketSize();

	/**
	 * The maximum milliseconds that will be tracked in {@link CounterBuffer}.
	 */
	Property<Integer> metricsRollingStatisticalWindowInMilliseconds();

	/**
	 * The number of buckets in {@link CounterBuffer}.
	 * @return
	 */
	Property<Integer> metricsRollingStatisticalWindowBuckets();
}

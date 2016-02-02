package com.leetcode.circuitbreaker;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import com.leetcode.circuitbreaker.metrics.Metrics;
import com.leetcode.circuitbreaker.properties.Properties;
import com.leetcode.circularbuffer.ActualTime;

/**
 * An implementation of the circuit breaker that is default circuit breaker.
 * @author fang_j
 *
 */
public class DefaultCircuitBreaker implements CircuitBreaker {
	/**
	 * The properties of current command.
	 */
	private final Properties properties;

	/**
	 * The metrics of current command.
	 */
	private final Metrics metrics;

	/**
	 * The state of circuit breaker.
	 */
	private final AtomicBoolean circuitOpen = new AtomicBoolean(false);

	/**
	 * The last time when the circuit breaker was opened or tested.
	 */
	private final AtomicLong circuitOpenedOrLastTestedTime = new AtomicLong();

	/**
	 * @param properties The properties of current command.
	 * @param metrics The metrics of current command.
	 */
	public DefaultCircuitBreaker(final Properties properties, final Metrics metrics)
	{
		if (properties == null) {
			throw new IllegalArgumentException("properties");
		}

		if (metrics == null) {
			throw new IllegalArgumentException("metrics");
		}

		this.properties = properties;
		this.metrics = metrics;
	}

	@Override
	public boolean allowRequest() {
		if (this.properties.circuitBreakerForceOpen().get()) {
			return false;
		}

		if (this.properties.circuitBreakerForceClosed().get()) {
			this.isOpen();
			return true;
		}

		return !this.isOpen() || this.allowSingleTest();
	}

	@Override
	public boolean isOpen() {
		if (this.circuitOpen.get()) {
			return true;
		}

		final com.leetcode.circuitbreaker.metrics.HealthCounts health = this.metrics.getHealthCounts();

		if (health.totalRequests() < this.properties.circuitBreakerRequestCountThreshold().get()) {
			return false;
		}

		if (health.errorPercentage() < this.properties.circuitBreakerErrorThresholdPercentage().get()) {
			return false;
		} else {
			if (this.circuitOpen.compareAndSet(false, true)) {
				this.circuitOpenedOrLastTestedTime.set(com.leetcode.circularbuffer.ActualTime.currentTimeInMillis());
			}

			return true;
		}
	}

	@Override
	public void markSuccess() {
		if (this.circuitOpen.get()) {
			this.circuitOpen.set(false);
			this.metrics.resetCounter();
		}
	}

	private boolean allowSingleTest()
	{
		final long circuitOpenedOrLastTestedTimeTmp = this.circuitOpenedOrLastTestedTime.get();

		if (this.circuitOpen.get() && (ActualTime.currentTimeInMillis() > (circuitOpenedOrLastTestedTimeTmp + this.properties.circuitBreakerSleepWindowInMilliseconds().get()))) {
			if (this.circuitOpenedOrLastTestedTime.compareAndSet(circuitOpenedOrLastTestedTimeTmp, ActualTime.currentTimeInMillis())) {
				return true;
			}
		}

		return false;
	}
}


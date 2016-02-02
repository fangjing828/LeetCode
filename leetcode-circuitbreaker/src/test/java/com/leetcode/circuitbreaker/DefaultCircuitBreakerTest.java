package com.leetcode.circuitbreaker;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.leetcode.circuitbreaker.metrics.HealthCounts;
import com.leetcode.circuitbreaker.metrics.Metrics;
import com.leetcode.circuitbreaker.properties.Properties;
import com.leetcode.circuitbreaker.properties.PropertiesFactory;
import com.leetcode.circuitbreaker.properties.PropertiesSetter;
import com.leetcode.key.KeyFactory;
import com.leetcode.random.Letter;
import com.leetcode.random.Random;

public class DefaultCircuitBreakerTest {
	private String opName;
	private String serviceName;
	private String fullServiceName;
	private String commandKey;
	private String metricPrefix;
	private Properties properties;
	private PropertiesSetter setter;
	private Metrics metrics;
	private DefaultCircuitBreaker circuitBreaker;

	@Before
	public void init() throws Exception {
		this.opName = Letter.random(10);
		this.serviceName = Letter.random(10);
		this.fullServiceName = Letter.random(20) + "." + this.serviceName;
		this.metricPrefix = Letter.random(10);
		this.commandKey = this.fullServiceName + "." + this.opName;
		this.setter = this.initSetter();
		this.properties = PropertiesFactory.getCommandProperties(KeyFactory.getCommandKey(this.commandKey), this.setter);

		this.metrics = Metrics.getInstance(this.commandKey, this.opName, this.serviceName, this.fullServiceName, this.metricPrefix, this.properties);
		this.circuitBreaker = new DefaultCircuitBreaker(this.properties, this.metrics);
	}

	@Test
	public void allowRequestTest() throws InterruptedException {
		this.reset();
		if (this.properties.circuitBreakerForceOpen().get()) {
			Assert.assertEquals(false, this.circuitBreaker.allowRequest());
		} else {
			if (this.properties.circuitBreakerForceOpen().get()) {
				Assert.assertEquals(true, this.circuitBreaker.allowRequest());
			}
		}
	}

	@Test
	public void allowRequestWithLessThanThresholdTest() throws InterruptedException {
		this.reset();
		this.properties.circuitBreakerForceOpen().set(false);
		this.properties.circuitBreakerForceClosed().set(false);
		final HealthCounts health = this.metrics.getHealthCounts();
		Assert.assertEquals(true, health.totalRequests() < this.properties.circuitBreakerRequestCountThreshold().get());
		Assert.assertEquals(true, this.circuitBreaker.allowRequest());
		Assert.assertEquals(true, this.circuitBreaker.allowRequest());
	}

	@Test
	public void allowRequestWithMoreThanThresholdTest() throws InterruptedException {
		this.reset();
		this.properties.circuitBreakerForceOpen().set(false);
		this.properties.circuitBreakerForceClosed().set(false);


		for (int i = 0, count = this.properties.circuitBreakerRequestCountThreshold().get() * 2; i < count; i++) {
			this.metrics.markSuccess(0);
		}

		Thread.sleep(this.properties.metricsHealthSnapshotIntervalInMilliseconds().get().longValue());
		final HealthCounts health = this.metrics.getHealthCounts();
		Assert.assertEquals(true, health.totalRequests() > this.properties.circuitBreakerRequestCountThreshold().get());
		Assert.assertEquals(true, this.circuitBreaker.allowRequest());
	}

	@Test
	public void allowRequestMoreThanErrorThresholdTest() throws InterruptedException {
		this.reset();
		this.properties.circuitBreakerForceOpen().set(false);
		this.properties.circuitBreakerForceClosed().set(false);
		int count = this.properties.circuitBreakerRequestCountThreshold().get() * 2;

		for (int i = 0; i < count; i++) {
			this.metrics.markSuccess(0);
		}

		count = (count * 100) / (100 - this.properties.circuitBreakerErrorThresholdPercentage().get());
		for (int i = 0; i < count; i++) {
			this.metrics.markTimeout(0);
		}

		Thread.sleep(this.properties.metricsHealthSnapshotIntervalInMilliseconds().get().longValue());
		final HealthCounts health = this.metrics.getHealthCounts();
		Assert.assertEquals(true, health.totalRequests() > this.properties.circuitBreakerRequestCountThreshold().get());
		Assert.assertEquals(false, this.circuitBreaker.allowRequest());
	}

	@Test
	public void allowRequestMoreThanErrorThresholdAndCircuitOpenTest() throws InterruptedException {
		this.reset();
		this.properties.circuitBreakerForceOpen().set(false);
		this.properties.circuitBreakerForceClosed().set(false);
		int count = this.properties.circuitBreakerRequestCountThreshold().get() * 2;

		for (int i = 0; i < count; i++) {
			this.metrics.markSuccess(0);
		}

		count = (count * 100) / (100 - this.properties.circuitBreakerErrorThresholdPercentage().get());
		for (int i = 0; i < count; i++) {
			this.metrics.markTimeout(0);
		}

		Thread.sleep(this.properties.metricsHealthSnapshotIntervalInMilliseconds().get().longValue());
		final HealthCounts health = this.metrics.getHealthCounts();
		Assert.assertEquals(true, health.totalRequests() > this.properties.circuitBreakerRequestCountThreshold().get());
		Assert.assertEquals(false, this.circuitBreaker.allowRequest());
		Thread.sleep(this.properties.circuitBreakerSleepWindowInMilliseconds().get().longValue() + 1);
		Assert.assertEquals(true, this.circuitBreaker.allowRequest());
		Assert.assertEquals(false, this.circuitBreaker.allowRequest());
	}

	@Test
	public void makeSuccessTest() throws InterruptedException {
		this.reset();
		this.properties.circuitBreakerForceOpen().set(false);
		this.properties.circuitBreakerForceClosed().set(false);
		int count = this.properties.circuitBreakerRequestCountThreshold().get() * 2;

		for (int i = 0; i < count; i++) {
			this.metrics.markSuccess(0);
		}

		count = (count * 100) / (100 - this.properties.circuitBreakerErrorThresholdPercentage().get());
		for (int i = 0; i < count; i++) {
			this.metrics.markTimeout(0);
		}

		Thread.sleep(this.properties.metricsHealthSnapshotIntervalInMilliseconds().get().longValue());
		final HealthCounts health = this.metrics.getHealthCounts();
		Assert.assertEquals(true, health.totalRequests() > this.properties.circuitBreakerRequestCountThreshold().get());
		Assert.assertEquals(false, this.circuitBreaker.allowRequest());
		this.circuitBreaker.markSuccess();
		Thread.sleep(this.properties.metricsHealthSnapshotIntervalInMilliseconds().get().longValue());
		Assert.assertEquals(true, this.circuitBreaker.allowRequest());
	}

	private PropertiesSetter initSetter() {
		final PropertiesSetter setter = new PropertiesSetter();
		final int max = 200;
		final int min = 100;
		setter.setCircuitBreakerEnabled(Random.rndBoolean());
		setter.setCircuitBreakerErrorThresholdPercentage(Random.rndInteger(99));
		setter.setCircuitBreakerForceClosed(Random.rndBoolean());
		setter.setCircuitBreakerForceOpen(Random.rndBoolean());
		setter.setCircuitBreakerRequestCountThreshold(Random.rndInteger(min, max));
		setter.setCircuitBreakerSleepWindowInMilliseconds(Random.rndInteger(min, max));

		setter.setMetricsHealthSnapshotIntervalInMilliseconds(Random.rndInteger(min, max));

		setter.setMetricsRollingPercentileBucketSize(Random.rndInteger(min, max));
		setter.setMetricsRollingPercentileWindowBuckets(Random.rndInteger(min, max));
		setter.setMetricsRollingPercentileWindowInMilliseconds(setter.getMetricsRollingPercentileWindowBuckets() * Random.rndInteger(min, max));
		setter.setMetricsRollingStatisticalWindowBuckets(Random.rndInteger(min, max));
		setter.setMetricsRollingStatisticalWindowInMilliseconds(setter.getMetricsRollingStatisticalWindowBuckets() * Random.rndInteger(min, max));

		return setter;
	}

	private void reset() throws InterruptedException {
		this.metrics.resetCounter();
		this.metrics.resetMetricsCounter();
		this.circuitBreaker.markSuccess();
		Thread.sleep(this.properties.metricsHealthSnapshotIntervalInMilliseconds().get().longValue());
	}
}

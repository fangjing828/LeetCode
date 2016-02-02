package com.leetcode.circuitbreaker.properties;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CustomPropertiesTest {
	private Properties defaultProperties;

	@Before
	public void init() {
		this.defaultProperties = new DefaultProperties();
	}

	@Test
	public void testInitialization() {
		final PropertiesSetter setter = new PropertiesSetter();
		final Properties customProperties = new CustomProperties(setter);

		assertEquals(this.defaultProperties.circuitBreakerEnabled().get(), customProperties.circuitBreakerEnabled().get());
		assertEquals(this.defaultProperties.circuitBreakerErrorThresholdPercentage().get(), customProperties.circuitBreakerErrorThresholdPercentage().get());
		assertEquals(this.defaultProperties.circuitBreakerForceClosed().get(), customProperties.circuitBreakerForceClosed().get());
		assertEquals(this.defaultProperties.circuitBreakerForceOpen().get(), customProperties.circuitBreakerForceOpen().get());
		assertEquals(this.defaultProperties.circuitBreakerRequestCountThreshold().get(), customProperties.circuitBreakerRequestCountThreshold().get());
		assertEquals(this.defaultProperties.circuitBreakerSleepWindowInMilliseconds().get(), customProperties.circuitBreakerSleepWindowInMilliseconds().get());

		assertEquals(this.defaultProperties.executionIsolationThreadTimeoutInMilliseconds().get(), customProperties.executionIsolationThreadTimeoutInMilliseconds().get());

		assertEquals(this.defaultProperties.metricsHealthSnapshotIntervalInMilliseconds().get(), customProperties.metricsHealthSnapshotIntervalInMilliseconds().get());

		assertEquals(this.defaultProperties.metricsRollingPercentileBucketSize().get(), customProperties.metricsRollingPercentileBucketSize().get());
		assertEquals(this.defaultProperties.metricsRollingPercentileWindowBuckets().get(), customProperties.metricsRollingPercentileWindowBuckets().get());
		assertEquals(this.defaultProperties.metricsRollingPercentileWindowInMilliseconds().get(), customProperties.metricsRollingPercentileWindowInMilliseconds().get());
		assertEquals(this.defaultProperties.metricsRollingStatisticalWindowBuckets().get(), customProperties.metricsRollingStatisticalWindowBuckets().get());
		assertEquals(this.defaultProperties.metricsRollingStatisticalWindowInMilliseconds().get(), customProperties.metricsRollingStatisticalWindowInMilliseconds().get());
	}

	@Test
	public void testSetterReference() {
		final PropertiesSetter setter = new PropertiesSetter();
		setter.setCircuitBreakerForceClosed(!this.defaultProperties.circuitBreakerForceClosed().get());
		final Properties customProperties1 = new CustomProperties(setter);
		setter.setCircuitBreakerForceClosed(this.defaultProperties.circuitBreakerForceClosed().get());
		final Properties customProperties2 = new CustomProperties(setter);


		assertEquals(!this.defaultProperties.circuitBreakerForceClosed().get(), customProperties1.circuitBreakerForceClosed().get());
		assertEquals(this.defaultProperties.circuitBreakerForceClosed().get(), customProperties2.circuitBreakerForceClosed().get());
	}
}

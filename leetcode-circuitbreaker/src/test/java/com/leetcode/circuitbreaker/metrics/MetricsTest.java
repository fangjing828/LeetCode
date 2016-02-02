package com.leetcode.circuitbreaker.metrics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.leetcode.circuitbreaker.metrics.HealthCounts;
import com.leetcode.circuitbreaker.metrics.Metrics;
import com.leetcode.circuitbreaker.properties.Properties;
import com.leetcode.circuitbreaker.properties.PropertiesFactory;
import com.leetcode.circuitbreaker.properties.PropertiesSetter;
import com.leetcode.circularbuffer.Audit;
import com.leetcode.key.KeyFactory;
import com.leetcode.random.Letter;
import com.leetcode.random.Random;

public class MetricsTest {
	private String opName;
	private String serviceName;
	private String fullServiceName;
	private String commandKey;
	private String metricPrefix;
	private Properties properties;
	private PropertiesSetter setter;
	private String key;
	private String[] keys;

	@Before
	public void init() throws Exception {
		this.opName = Letter.random(10);
		this.serviceName = Letter.random(10);
		this.fullServiceName = Letter.random(20) + "." + this.serviceName;
		this.metricPrefix = Letter.random(10);
		this.commandKey = this.fullServiceName + "." + this.opName;
		this.setter = this.initSetter();
		this.properties = PropertiesFactory.getCommandProperties(KeyFactory.getCommandKey(this.commandKey), this.setter);

		this.keys = new String[Random.rndInteger(5, 10)];
		for (int i = 0; i < this.keys.length; i++) {
			this.keys[i] = this.commandKey + "." + Letter.random(10);
		}
		this.key = this.commandKey + "." + Letter.random(10);
	}

	@Test
	public void getInstanceTest() {
		final Metrics[] metricsArray = new Metrics[this.keys.length];
		for (int i = 0; i < this.keys.length; i++) {
			metricsArray[i] = Metrics.getInstance(this.keys[i], this.opName, this.serviceName, this.fullServiceName, this.metricPrefix, this.properties);
		}

		for (int i = 0; i < this.keys.length; i++) {
			final Metrics metrics = metricsArray[i] = Metrics.getInstance(this.keys[i], this.opName, this.serviceName, this.fullServiceName, this.metricPrefix, this.properties);
			Assert.assertEquals(metricsArray[i], metrics);
		}
	}

	@Test
	public void resetCounterTest() throws InterruptedException {
		final Metrics metrics = this.getMetrics(this.key);
		metrics.resetCounter();
		Assert.assertEquals(0, metrics.getHealthCounts().totalRequests());
		Thread.sleep(this.setter.getMetricsHealthSnapshotIntervalInMilliseconds());
		Assert.assertEquals(0, metrics.getHealthCounts().totalRequests());
	}

	@Test
	public void resetMetricsCounterTest() {
		final Metrics metrics = this.getMetrics(this.key);
		metrics.resetMetricsCounter();
		Assert.assertEquals(0, metrics.successCount());
		Assert.assertEquals(0, metrics.serviceErrorCount());
		Assert.assertEquals(0, metrics.frameworkErrorCount());
		Assert.assertEquals(0, metrics.validationErrorCount());
		Assert.assertEquals(0, metrics.timeoutCount());
		Assert.assertEquals(0, metrics.shortCircuitCount());
	}

	@Test
	public void markSuccessTest() throws InterruptedException {
		final Metrics metrics = this.getMetrics(this.key);
		final int count = Random.rndInteger(50, 100);
		for (int i = 0; i < count; i++) {
			metrics.markSuccess(Random.rndLong(100));
		}

		Assert.assertEquals(metrics.getHealthCounts().successCount(), 0);
		Thread.sleep(this.setter.getMetricsHealthSnapshotIntervalInMilliseconds());
		Assert.assertEquals(metrics.getHealthCounts().successCount(), count);
	}

	@Test
	public void markTimeoutTest() throws InterruptedException {
		final Metrics metrics = this.getMetrics(this.key);
		final int count = Random.rndInteger(50, 100);
		for (int i = 0; i < count; i++) {
			metrics.markTimeout(Random.rndLong(100));
		}

		Assert.assertEquals(metrics.getHealthCounts().timeoutCount(), 0);
		Thread.sleep(this.setter.getMetricsHealthSnapshotIntervalInMilliseconds());
		Assert.assertEquals(metrics.getHealthCounts().timeoutCount(), count);
	}

	@Test
	public void markShortCircuitedTest() throws InterruptedException {
		final Metrics metrics = this.getMetrics(this.key);
		final int count = Random.rndInteger(50, 100);
		for (int i = 0; i < count; i++) {
			metrics.markShortCircuited();
		}

		Assert.assertEquals(metrics.getHealthCounts().shortCircuitedCount(), 0);
		Thread.sleep(this.setter.getMetricsHealthSnapshotIntervalInMilliseconds());
		Assert.assertEquals(metrics.getHealthCounts().shortCircuitedCount(), count);
	}

	@Test
	public void markFrameworkExceptionThrownTest() throws InterruptedException {
		final Metrics metrics = this.getMetrics(this.key);
		final int count = Random.rndInteger(50, 100);
		for (int i = 0; i < count; i++) {
			metrics.markFrameworkExceptionThrown();
		}

		Assert.assertEquals(metrics.getHealthCounts().frameworkExceptionCount(), 0);
		Thread.sleep(this.setter.getMetricsHealthSnapshotIntervalInMilliseconds());
		Assert.assertEquals(metrics.getHealthCounts().frameworkExceptionCount(), count);
	}

	@Test
	public void markServiceExceptionThrownTest() throws InterruptedException {
		final Metrics metrics = this.getMetrics(this.key);
		final int count = Random.rndInteger(50, 100);
		for (int i = 0; i < count; i++) {
			metrics.markServiceExceptionThrown();
		}

		Assert.assertEquals(metrics.getHealthCounts().serviceExceptionCount(), 0);
		Thread.sleep(this.setter.getMetricsHealthSnapshotIntervalInMilliseconds());
		Assert.assertEquals(metrics.getHealthCounts().serviceExceptionCount(), count);
	}

	@Test
	public void markValidationExceptionThrownTest() throws InterruptedException {
		final Metrics metrics = this.getMetrics(this.key);
		final int count = Random.rndInteger(50, 100);
		for (int i = 0; i < count; i++) {
			metrics.markValidationExceptionThrown();
		}

		Assert.assertEquals(metrics.getHealthCounts().validationExceptionCount(), 0);
		Thread.sleep(this.setter.getMetricsHealthSnapshotIntervalInMilliseconds());
		Assert.assertEquals(metrics.getHealthCounts().validationExceptionCount(), count);
	}

	@Test
	public void successCountTest() {
		final Metrics metrics = this.getMetrics(this.key);
		final int count = Random.rndInteger(50, 100);
		for (int i = 0; i < count; i++) {
			metrics.markSuccess(Random.rndInteger(1, 100));
		}

		Assert.assertEquals(metrics.successCount(), count);
		Assert.assertEquals(metrics.successCount(), 0);
	}

	@Test
	public void serviceErrorCountTest() {
		final Metrics metrics = this.getMetrics(this.key);
		final int count = Random.rndInteger(50, 100);
		for (int i = 0; i < count; i++) {
			metrics.markServiceExceptionThrown();
		}

		Assert.assertEquals(metrics.serviceErrorCount(), count);
		Assert.assertEquals(metrics.serviceErrorCount(), 0);
	}

	@Test
	public void frameworkErrorCountTest() {
		final Metrics metrics = this.getMetrics(this.key);
		final int count = Random.rndInteger(50, 100);
		for (int i = 0; i < count; i++) {
			metrics.markFrameworkExceptionThrown();
		}

		Assert.assertEquals(metrics.frameworkErrorCount(), count);
		Assert.assertEquals(metrics.frameworkErrorCount(), 0);
	}

	@Test
	public void validationErrorCountTest() {
		final Metrics metrics = this.getMetrics(this.key);
		final int count = Random.rndInteger(50, 100);
		for (int i = 0; i < count; i++) {
			metrics.markValidationExceptionThrown();
		}

		Assert.assertEquals(metrics.validationErrorCount(), count);
		Assert.assertEquals(metrics.validationErrorCount(), 0);
	}

	@Test
	public void shortCircuitCountTest() {
		final Metrics metrics = this.getMetrics(this.key);
		final int count = Random.rndInteger(50, 100);
		for (int i = 0; i < count; i++) {
			metrics.markShortCircuited();
		}

		Assert.assertEquals(metrics.shortCircuitCount(), count);
		Assert.assertEquals(metrics.shortCircuitCount(), 0);
	}

	@Test
	public void timeoutCountTest() {
		final Metrics metrics = this.getMetrics(this.key);
		final int count = Random.rndInteger(50, 100);
		for (int i = 0; i < count; i++) {
			metrics.markTimeout(Random.rndInteger(1, 100));
		}

		Assert.assertEquals(metrics.timeoutCount(), count);
		Assert.assertEquals(metrics.timeoutCount(), 0);
	}

	@Test
	public void getServiceExecutionTimePercentile() {
		final Metrics metrics = this.getMetrics(this.key);
		final List<Long> executionTimes = this.addServiceExecutionTime(metrics);
		Collections.sort(executionTimes);
		Assert.assertEquals(executionTimes.get(0).longValue(), metrics.getServiceExecutionTimePercentile(0));
		Assert.assertEquals(executionTimes.get(executionTimes.size() - 1).longValue(), metrics.getServiceExecutionTimePercentile(100));
		final double percent = Random.rndInteger(1, 100);
		final int index = (int)((percent * (executionTimes.size() - 1)) / 100);
		Assert.assertEquals(executionTimes.get(index).longValue(), metrics.getServiceExecutionTimePercentile(percent));
	}

	@Test
	public void getServiceExecutionTimeMeanTest() {
		final Metrics metrics = this.getMetrics(this.key);
		final List<Long> executionTimes = this.addServiceExecutionTime(metrics);
		Long sumTimes = 0l;
		for (final Long time : executionTimes) {
			sumTimes += time;
		}
		Assert.assertEquals(Math.round(sumTimes.doubleValue() / executionTimes.size()), metrics.getServiceExecutionTimeMean());
	}

	@Test
	public void getServiceExecutionTimeMetricsDataTest() {
		final Metrics metrics = this.getMetrics(this.key);
		final List<Long> executionTimes = this.addServiceExecutionTime(metrics);
		Collections.sort(executionTimes);
		final Audit audit = metrics.GetServiceExecutionTimeMetricsData();

		long sumTimes = 0l;
		for (final Long time : executionTimes) {
			sumTimes += time;
		}

		Assert.assertEquals(executionTimes.size(), audit.count());
		Assert.assertEquals(sumTimes, audit.sum());
		Assert.assertEquals(executionTimes.get(0).longValue(), audit.min());
		Assert.assertEquals(executionTimes.get(executionTimes.size() - 1).longValue(), audit.max());
	}

	@Test
	public void getServiceExecutionCountInTimeRangeTest() {
		final Metrics metrics = this.getMetrics(this.key);
		final List<Long> executionTimes = this.addServiceExecutionTime(metrics);
		Collections.sort(executionTimes);
		final long low = executionTimes.get(0);
		final long high = executionTimes.get(executionTimes.size() - 1);

		int count = 0;
		for (final long time : executionTimes) {
			if ((time < high) && (time >= low)) {
				count ++;
			}
		}

		Assert.assertEquals(executionTimes.size(), metrics.getServiceExecutionCountInTimeRange(low, null));
		Assert.assertNotEquals(executionTimes.size(), metrics.getServiceExecutionCountInTimeRange(low, high));
		Assert.assertEquals(count, metrics.getServiceExecutionCountInTimeRange(low, high));
	}

	@Test
	public void getTotalTimePercentilePercentile() {
		final Metrics metrics = this.getMetrics(this.key);
		final List<Long> executionTimes = this.addTotalExecutionTime(metrics);
		Collections.sort(executionTimes);
		Assert.assertEquals(executionTimes.get(0).longValue(), metrics.getTotalTimePercentile(0));
		Assert.assertEquals(executionTimes.get(executionTimes.size() - 1).longValue(), metrics.getTotalTimePercentile(100));
		final double percent = Random.rndInteger(1, 100);
		final int index = (int)((percent * (executionTimes.size() - 1)) / 100);
		Assert.assertEquals(executionTimes.get(index).longValue(), metrics.getTotalTimePercentile(percent));
	}

	@Test
	public void getTotalTimeMeanTest() {
		final Metrics metrics = this.getMetrics(this.key);
		final List<Long> executionTimes = this.addTotalExecutionTime(metrics);
		Long sumTimes = 0l;
		for (final Long time : executionTimes) {
			sumTimes += time;
		}
		Assert.assertEquals(Math.round(sumTimes.doubleValue() / executionTimes.size()), metrics.getTotalTimeMean());
	}

	@Test
	public void getTotalTimeMetricsDataTest() {
		final Metrics metrics = this.getMetrics(this.key);
		final List<Long> executionTimes = this.addTotalExecutionTime(metrics);
		Collections.sort(executionTimes);
		final Audit audit = metrics.getTotalTimeMetricsData();

		long sumTimes = 0l;
		for (final Long time : executionTimes) {
			sumTimes += time;
		}

		Assert.assertEquals(executionTimes.size(), audit.count());
		Assert.assertEquals(sumTimes, audit.sum());
		Assert.assertEquals(executionTimes.get(0).longValue(), audit.min());
		Assert.assertEquals(executionTimes.get(executionTimes.size() - 1).longValue(), audit.max());
	}

	@Test
	public void getServiceExecutionCountInTotalTimeRangeTest() {
		final Metrics metrics = this.getMetrics(this.key);
		final List<Long> executionTimes = this.addTotalExecutionTime(metrics);
		Collections.sort(executionTimes);
		final long low = executionTimes.get(0);
		final long high = executionTimes.get(executionTimes.size() - 1);

		int count = 0;
		for (final long time : executionTimes) {
			if ((time < high) && (time >= low)) {
				count ++;
			}
		}

		Assert.assertEquals(executionTimes.size(), metrics.getServiceExecutionCountInTotalTimeRange(low, null));
		Assert.assertNotEquals(executionTimes.size(), metrics.getServiceExecutionCountInTotalTimeRange(low, high));
		Assert.assertEquals(count, metrics.getServiceExecutionCountInTotalTimeRange(low, high));
	}

	private List<Long> addTotalExecutionTime(final Metrics metrics) {
		final List<Long> executionTimes = new ArrayList<Long>();

		for (int i = 0, count = Random.rndInteger(10, 20); i < count; i++) {
			final long executionTime = Random.rndInteger(1, 1000);
			metrics.addTotalExecutionTime(executionTime);
			executionTimes.add(executionTime);
		}
		return executionTimes;
	}

	private List<Long> addServiceExecutionTime(final Metrics metrics) {
		final List<Long> executionTimes = new ArrayList<Long>();

		for (int i = 0, count = Random.rndInteger(10, 20); i < count; i++) {
			final long executionTime = Random.rndInteger(1, 1000);
			metrics.addServiceExecutionTime(executionTime);
			executionTimes.add(executionTime);
		}
		return executionTimes;
	}

	private Metrics getMetrics(final String key) {
		final Metrics metrics = Metrics.getInstance(key, this.opName, this.serviceName, this.fullServiceName, this.metricPrefix, this.properties);
		Assert.assertNotNull(metrics);
		this.validateMetricsInitData(metrics);
		return metrics;
	}

	private void validateMetricsInitData(final Metrics metrics) {
		final HealthCounts healthCounts = metrics.getHealthCounts();
		Assert.assertEquals(0, healthCounts.totalRequests());
		Assert.assertEquals(0, healthCounts.totalErrorCount());
		Assert.assertEquals(0, healthCounts.totalExceptionCount());
		Assert.assertEquals(0, healthCounts.totalFailureCount());
		Assert.assertEquals(0, healthCounts.totalSuccessCount());

		Assert.assertEquals(0, healthCounts.errorPercentage());
		Assert.assertEquals(0, healthCounts.successCount());
		Assert.assertEquals(0, healthCounts.timeoutCount());
		Assert.assertEquals(0, healthCounts.shortCircuitedCount());

		Assert.assertEquals(0, healthCounts.frameworkExceptionCount());
		Assert.assertEquals(0, healthCounts.serviceExceptionCount());
		Assert.assertEquals(0, healthCounts.validationExceptionCount());

		Assert.assertEquals(this.opName, metrics.operationName());
		Assert.assertEquals(this.serviceName, metrics.serviceName());
		Assert.assertEquals(this.fullServiceName, metrics.fullServiceName());

		Assert.assertEquals(this.properties, metrics.properties());

		Assert.assertEquals(this.metricPrefix + ".event.distribution", metrics.metricNameEventDistribution());
		Assert.assertEquals(this.metricPrefix + ".execution.concurrency",metrics.metricNameConcurrentExecutionCount());
		Assert.assertEquals(this.metricPrefix + ".request.count", metrics.metricNameRequestCount());
		Assert.assertEquals(this.metricPrefix + ".request.latency", metrics.metricNameLatency());
		Assert.assertEquals(this.metricPrefix + ".request.latency.distribution", metrics.metricNameLatencyDistribution());
		Assert.assertEquals(this.metricPrefix + ".request.latency.percentile", metrics.metricNameLatencyPercentile());

		Assert.assertEquals(0, metrics.currentConcurrentExecutionCount());
		Assert.assertEquals(0, metrics.maxConcurrentExecutionCount());

		Assert.assertEquals(0, metrics.successCount());
		Assert.assertEquals(0, metrics.serviceErrorCount());
		Assert.assertEquals(0, metrics.frameworkErrorCount());
		Assert.assertEquals(0, metrics.validationErrorCount());
		Assert.assertEquals(0, metrics.shortCircuitCount());
		Assert.assertEquals(0, metrics.timeoutCount());
	}

	private PropertiesSetter initSetter() {
		final PropertiesSetter setter = new PropertiesSetter();
		final int max = 200;
		final int min = 100;
		setter.setCircuitBreakerEnabled(Random.rndBoolean());
		setter.setCircuitBreakerErrorThresholdPercentage(Random.rndInteger(min, max));
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
}

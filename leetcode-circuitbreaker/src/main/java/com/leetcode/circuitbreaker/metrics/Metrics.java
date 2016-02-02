package com.leetcode.circuitbreaker.metrics;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import com.leetcode.circuitbreaker.ExecutionEvent;
import com.leetcode.circuitbreaker.properties.Properties;
import com.leetcode.circularbuffer.ActualTime;
import com.leetcode.circularbuffer.Audit;
import com.leetcode.circularbuffer.CounterBuffer;
import com.leetcode.circularbuffer.IntegerPercentileBuffer;


public class Metrics {
	private static final AtomicReference<ConcurrentHashMap<String, Metrics>> ref = new  AtomicReference<ConcurrentHashMap<String, Metrics>>();

	private final String operationName;
	private final String serviceName;
	private final String fullServiceName;
	private final Properties properties;

	private final AtomicInteger concurrentExecutionCount = new AtomicInteger();
	private final AtomicInteger maxConcurrentExecutionCount = new AtomicInteger();

	private final IntegerPercentileBuffer executionOperationLatencyBuffer;
	private final IntegerPercentileBuffer requestLatencyBuffer;
	private final AtomicReference<CounterBuffer<ExecutionEvent>> counter = new AtomicReference<CounterBuffer<ExecutionEvent>>();

	private final AtomicReference<AtomicLong> successCounter = new AtomicReference<AtomicLong>();
	private final AtomicReference<AtomicLong> serviceErrorCounter = new AtomicReference<AtomicLong>();
	private final AtomicReference<AtomicLong> frameworkErrorCounter = new AtomicReference<AtomicLong>();
	private final AtomicReference<AtomicLong> validationErrorCounter = new AtomicReference<AtomicLong>();
	private final AtomicReference<AtomicLong> timeoutCounter = new AtomicReference<AtomicLong>();
	private final AtomicReference<AtomicLong> shortCircuitCounter = new AtomicReference<AtomicLong>();

	private final int metricsRollingStatisticalWindowInMilliseconds;
	private final int metricsRollingStatisticalWindowBuckets;

	private String metricNameRequestCount;
	private String metricNameEventDistribution;
	private String metricNameLatency;
	private String metricNameLatencyDistribution;
	private String metricNameLatencyPercentile;
	private String metricNameConcurrentExecutionCount;

	static {
		Metrics.reset();
	}

	public Metrics(final String opName, final String serviceName, final String fullServiceName, final String metricPrefix, final Properties properties)
	{
		this.operationName = opName;
		this.serviceName = serviceName;
		this.fullServiceName = fullServiceName;
		this.properties = properties;

		final int metricsRollingPercentileWindowInMilliseconds = properties.metricsRollingPercentileWindowInMilliseconds().get();
		final int metricsRollingPercentileWindowBuckets = properties.metricsRollingPercentileWindowBuckets().get();
		final int metricsRollingPercentileBucketSize = properties.metricsRollingPercentileBucketSize().get();

		this.metricsRollingStatisticalWindowInMilliseconds = properties.metricsRollingStatisticalWindowInMilliseconds().get();
		this.metricsRollingStatisticalWindowBuckets = properties.metricsRollingStatisticalWindowBuckets().get();

		this.executionOperationLatencyBuffer = new IntegerPercentileBuffer(metricsRollingPercentileWindowInMilliseconds, metricsRollingPercentileWindowBuckets, metricsRollingPercentileBucketSize, ActualTime.instance());
		this.requestLatencyBuffer = new IntegerPercentileBuffer(metricsRollingPercentileWindowInMilliseconds, metricsRollingPercentileWindowBuckets, metricsRollingPercentileBucketSize, ActualTime.instance());

		this.counter.getAndSet(new CounterBuffer<ExecutionEvent>(this.metricsRollingStatisticalWindowInMilliseconds, this.metricsRollingStatisticalWindowBuckets, ActualTime.instance()));

		this.successCounter.getAndSet(new AtomicLong(0));
		this.serviceErrorCounter.getAndSet(new AtomicLong(0));
		this.frameworkErrorCounter.getAndSet(new AtomicLong(0));
		this.validationErrorCounter.getAndSet(new AtomicLong(0));
		this.timeoutCounter.getAndSet(new AtomicLong(0));
		this.shortCircuitCounter.getAndSet(new AtomicLong(0));

		this.metricNameEventDistribution(metricPrefix + ".event.distribution")
		.metricNameConcurrentExecutionCount(metricPrefix + ".execution.concurrency")
		.metricNameRequestCount(metricPrefix + ".request.count")
		.metricNameLatency(metricPrefix + ".request.latency")
		.metricNameLatencyDistribution(metricPrefix + ".request.latency.distribution")
		.metricNameLatencyPercentile(metricPrefix + ".request.latency.percentile");
	}

	public static Metrics getInstance (final String commandKey, final String opName, final String serviceName, final String fullServiceName, final String metricPrefix, final Properties properties) {
		Metrics res = ref.get().get(commandKey);
		if (res == null) {
			res = new Metrics(opName, serviceName, fullServiceName, metricPrefix, properties);
			final Metrics existedMetrics = ref.get().putIfAbsent(commandKey, res);
			if (existedMetrics != null) {
				res = existedMetrics;
			}
		}
		return res;
	}

	public static void reset() {
		ref.getAndSet(new ConcurrentHashMap<String, Metrics>());
	}

	public void resetCounter() {
		this.counter.getAndSet(new CounterBuffer<ExecutionEvent>(this.metricsRollingStatisticalWindowInMilliseconds, this.metricsRollingStatisticalWindowBuckets, ActualTime.instance()));
	}

	public void resetMetricsCounter() {
		this.successCount();
		this.serviceErrorCount();
		this.frameworkErrorCount();
		this.validationErrorCount();
		this.timeoutCount();
		this.shortCircuitCount();
	}

	public String operationName() {
		return this.operationName;
	}

	public String serviceName() {
		return this.serviceName;
	}

	public String fullServiceName() {
		return this.fullServiceName;
	}

	public Properties properties() {
		return this.properties;
	}

	public int currentConcurrentExecutionCount() {
		return this.concurrentExecutionCount.get();
	}

	public int maxConcurrentExecutionCount() {
		return this.maxConcurrentExecutionCount.get();
	}

	public String metricNameRequestCount ()
	{
		return this.metricNameRequestCount;
	}

	Metrics metricNameRequestCount(final String metricNameRequestCount) {
		this.metricNameRequestCount = metricNameRequestCount;
		return this;
	}

	public String metricNameEventDistribution () {
		return this.metricNameEventDistribution;
	}

	Metrics metricNameEventDistribution(final String metricNameEventDistribution) {
		this.metricNameEventDistribution = metricNameEventDistribution;
		return this;
	}

	public String metricNameLatency() {
		return this.metricNameLatency;
	}

	Metrics metricNameLatency(final String metricNameLatency) {
		this.metricNameLatency = metricNameLatency;
		return this;
	}

	public String metricNameLatencyDistribution () {
		return this.metricNameLatencyDistribution;
	}

	Metrics metricNameLatencyDistribution(final String metricNameLatencyDistribution) {
		this.metricNameLatencyDistribution = metricNameLatencyDistribution;
		return this;
	}

	public String metricNameLatencyPercentile () {
		return this.metricNameLatencyPercentile;
	}

	Metrics metricNameLatencyPercentile(final String metricNameLatencyPercentile) {
		this.metricNameLatencyPercentile = metricNameLatencyPercentile;
		return this;
	}

	public String metricNameConcurrentExecutionCount () {
		return this.metricNameConcurrentExecutionCount;
	}

	Metrics metricNameConcurrentExecutionCount(final String metricNameConcurrentExecutionCount) {
		this.metricNameConcurrentExecutionCount = metricNameConcurrentExecutionCount;
		return this;
	}

	public void markSuccess(final long duration) {
		this.counter.get().increment(ExecutionEvent.Success);
		this.successCounter.get().incrementAndGet();
	}

	public void markTimeout(final long duration) {
		this.counter.get().increment(ExecutionEvent.Timeout);
		this.timeoutCounter.get().incrementAndGet();
	}

	public void markShortCircuited()
	{
		this.counter.get().increment(ExecutionEvent.ShortCircuited);
		this.shortCircuitCounter.get().incrementAndGet();
	}

	public void markFrameworkExceptionThrown()
	{
		this.counter.get().increment(ExecutionEvent.FrameworkExceptionThrown);
		this.frameworkErrorCounter.get().incrementAndGet();
	}


	public void markServiceExceptionThrown()
	{
		this.counter.get().increment(ExecutionEvent.ServiceExceptionThrown);
		this.serviceErrorCounter.get().incrementAndGet();
	}

	public void markValidationExceptionThrown()
	{
		this.counter.get().increment(ExecutionEvent.ValidationExceptionThrown);
		this.validationErrorCounter.get().incrementAndGet();
	}

	public long successCount()
	{
		return this.successCounter.getAndSet(new AtomicLong()).get();
	}

	public long serviceErrorCount()
	{
		return this.serviceErrorCounter.getAndSet(new AtomicLong()).get();
	}

	public long frameworkErrorCount()
	{
		return this.frameworkErrorCounter.getAndSet(new AtomicLong()).get();
	}

	public long validationErrorCount()
	{
		return this.validationErrorCounter.getAndSet(new AtomicLong()).get();
	}

	public long shortCircuitCount()
	{
		return this.shortCircuitCounter.getAndSet(new AtomicLong()).get();
	}

	public long timeoutCount()
	{
		return this.timeoutCounter.getAndSet(new AtomicLong()).get();
	}

	public void incrementConcurrentExecutionCount()
	{
		final int operationCount = this.concurrentExecutionCount.incrementAndGet();
		if (operationCount > this.maxConcurrentExecutionCount.get()) {
			this.maxConcurrentExecutionCount.getAndSet(operationCount);
		}
	}

	public void decrementConcurrentExecutionCount()
	{
		this.concurrentExecutionCount.decrementAndGet();
	}

	public void addServiceExecutionTime(final long duration)
	{
		this.executionOperationLatencyBuffer.add(duration);
	}

	public void addTotalExecutionTime(final long duration)
	{
		this.requestLatencyBuffer.add(duration);
	}

	public long getServiceExecutionTimePercentile(final double percentile) {
		return this.executionOperationLatencyBuffer.getPercentile(percentile);
	}

	public long getServiceExecutionTimeMean() {
		return this.executionOperationLatencyBuffer.getAuditDataAvg();
	}

	public Audit GetServiceExecutionTimeMetricsData() {
		return this.executionOperationLatencyBuffer.getAuditData();
	}

	public int getServiceExecutionCountInTimeRange(final long low, final Long high)
	{
		if (high == null) {
			return this.executionOperationLatencyBuffer.getItemCountInRange(low);
		} else {
			return this.executionOperationLatencyBuffer.getItemCountInRange(low, high.longValue());
		}
	}

	public long getTotalTimePercentile(final double percentile) {
		return this.requestLatencyBuffer.getPercentile(percentile);
	}

	public long getTotalTimeMean() {
		return this.requestLatencyBuffer.getAuditDataAvg();
	}

	public Audit getTotalTimeMetricsData() {
		return this.requestLatencyBuffer.getAuditData();
	}

	public int getServiceExecutionCountInTotalTimeRange(final long low, final Long high) {
		if (high == null) {
			return this.requestLatencyBuffer.getItemCountInRange(low);
		} else {
			return this.requestLatencyBuffer.getItemCountInRange(low, high.longValue());
		}
	}

	private volatile HealthCounts healthCountsSnapshot = new HealthCounts();
	private final AtomicLong lastHealthCountsSnapshot = new AtomicLong(ActualTime.currentTimeInMillis());

	public HealthCounts getHealthCounts()
	{
		/**
		 * we put an interval between snapshots so high-volume commands don't
		 * spend too much unnecessary time calculating metrics in very small time periods.
		 */
		final long lastTime =this.lastHealthCountsSnapshot.get();
		final long currentTime = ActualTime.currentTimeInMillis();
		if (((currentTime - lastTime) >= this.properties.metricsHealthSnapshotIntervalInMilliseconds().get()) || (this.healthCountsSnapshot == null))
		{
			if (this.lastHealthCountsSnapshot.compareAndSet(lastTime, currentTime)) {
				/**
				 * our thread won setting the snapshot time so we will proceed with generating a new snapshot
				 * losing threads will continue using the old snapshot.
				 */
				final CounterBuffer<ExecutionEvent> tmpCounter = this.counter.get();
				final long success = tmpCounter.count(ExecutionEvent.Success);
				final long timeout = tmpCounter.count(ExecutionEvent.Timeout);
				final long shortCircuited = tmpCounter.count(ExecutionEvent.ShortCircuited);
				final long frameworkException = tmpCounter.count(ExecutionEvent.FrameworkExceptionThrown);
				final long serviceException = tmpCounter.count(ExecutionEvent.ServiceExceptionThrown);
				final long validationException = tmpCounter.count(ExecutionEvent.ValidationExceptionThrown);
				this.healthCountsSnapshot = new HealthCounts(success, timeout, shortCircuited,
						frameworkException, serviceException, validationException);
			}
		}
		return this.healthCountsSnapshot;
	}
}
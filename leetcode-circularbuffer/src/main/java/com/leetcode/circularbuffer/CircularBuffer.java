package com.leetcode.circularbuffer;

import java.lang.reflect.Array;

/**
 * The {@code CircularBuffer} is divide into a number of buckets by increasing time stamp.
 * 
 * @author fang_j
 * @date 18/01/2016
 * @param <T>
 */
abstract class CircularBuffer <T extends Bucket>{
	private static final int _legacyBucketCount = 1;

	protected final T[] buckets;
	protected final int bucketCount;
	protected int bufferEnd;
	protected final int timeWindowInMillis;
	protected final int bucketTimeWindowInMillis;
	protected final Time time;

	@SuppressWarnings("unchecked")
	public CircularBuffer(final int timeWindowInMills, final int bucketCount, final Time time) {
		if (timeWindowInMills < 1) {
			throw new IllegalArgumentException("time window of circular buffer can not less than 1.");
		}

		if (bucketCount < 1) {
			throw new IllegalArgumentException("bucket's count of circular buffer can not less than 1.");
		}

		if ((timeWindowInMills % bucketCount) != 0) {
			throw new IllegalArgumentException("Time window must be n * bucket time window.");
		}

		if (time == null) {
			throw new IllegalArgumentException("time can not be null.");
		}

		this.timeWindowInMillis = timeWindowInMills;
		this.bucketCount = bucketCount;
		this.bucketTimeWindowInMillis = this.timeWindowInMillis / this.bucketCount;
		this.time = time;

		this.buckets = (T[]) Array.newInstance(this.createBucket(0).getClass(), this.bucketCount + _legacyBucketCount);
		for (int i = 0; i < this.buckets.length; i++) {
			this.buckets[i] = this.createBucket(0);
		}
	}

	protected abstract T createBucket(long currentTimeInMills);

	/**
	 * Get current bucket from circular buffer.
	 * @return Current bucket.
	 */
	synchronized public T currentBucket() {
		final long currentBucketStartTime = this.time.getCurrentTimeInMillis();
		if (this.isInBucketStatsWindow(this.buckets[this.bufferEnd], currentBucketStartTime)) {
			final int newBufferEnd = (this.bufferEnd + 1) % this.buckets.length;
			this.buckets[newBufferEnd] = this.createBucket(currentBucketStartTime);
			this.bufferEnd = newBufferEnd;
		}

		return this.buckets[this.bufferEnd];
	}

	/**
	 * From current time to see if it is in specified {@link Bucket} statistics window.
	 * @param bucket
	 * @param currentTime
	 * @return {@code true} if currentTime is in specified bucket statistics window, otherwise {@code false}.
	 */
	protected boolean isInBucketStatsWindow(final T bucket, final long currentTime) {
		return (bucket.timeInMillis() + this.bucketTimeWindowInMillis) <= currentTime;
	}

	/**
	 * From current time to see if specified {@link Bucket} is in {@link CircularBuffer} statistics window.
	 * @param bucket
	 * @param currentTime
	 * @return {@code true} if specified bucket is in circular buffer statistics window, otherwise {@code false}.
	 */
	protected boolean isInBufferStatsWindow(final T bucket, final long currentTime) {
		return (bucket.timeInMillis() + this.timeWindowInMillis) > currentTime;
	}
}
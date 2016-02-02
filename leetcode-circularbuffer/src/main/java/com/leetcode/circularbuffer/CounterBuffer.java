package com.leetcode.circularbuffer;


public class CounterBuffer<T> extends CircularBuffer<CounterBucket<T>>{
	public CounterBuffer(final int timeWindowInMills, final int bucketCount, final Time time) {
		super(timeWindowInMills, bucketCount, time);
	}

	@Override
	protected CounterBucket<T> createBucket(final long currentTimeInMills) {
		return new CounterBucket<T>(currentTimeInMills);
	}

	public int count(final T identity)
	{
		final long currentBufferStartTime = this.time.getCurrentTimeInMillis();
		int count = 0;
		for (final CounterBucket<T> bucket : this.buckets) {
			if (this.isInBufferStatsWindow(bucket, currentBufferStartTime)) {
				count += bucket.count(identity);
			}
		}
		return count;
	}

	public void increment(final T identity)
	{
		this.currentBucket().increment(identity);
	}
}

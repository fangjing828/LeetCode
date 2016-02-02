package com.leetcode.circularbuffer;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author fang_j
 *
 * @param <T>
 */
class PercentileBuffer<T> extends CircularBuffer<PercentileBucket<T>> {
	private final int capacity;

	public PercentileBuffer(final int timeWindowInMilliseconds, final int bucketCount,final int capacity,
			final Time time) {
		super(timeWindowInMilliseconds, bucketCount, time);
		if (capacity < 1) {
			throw new IllegalArgumentException("Bucket capacity cannot be less than 1.");
		}
		this.capacity = capacity;

		for (int i = 0; i < this.buckets.length; i++) {
			this.buckets[i] = this.createBucket(0);
		}
	}

	@Override
	protected PercentileBucket<T> createBucket(final long timeInMilliseconds) {
		return new PercentileBucket<T>(timeInMilliseconds, this.capacity);
	}

	public void add(final T data)
	{
		this.currentBucket().add(data);
	}

	public void visitData(final GenericAction<T> action)
	{
		final long currentBucketStartTime = this.time.getCurrentTimeInMillis();
		for (final PercentileBucket<T> bucket : this.buckets) {
			if ((bucket.timeInMillis() <= currentBucketStartTime) && ((bucket.timeInMillis() + this.timeWindowInMillis) > currentBucketStartTime))
			{
				final int count = bucket.count();
				for (int j = 0; j < count; j++)
				{
					action.consume(bucket.get(j));;
				}
			}
		}
	}


	public List<T> snapShot()
	{
		final List<T> snapShot = new ArrayList<T>();
		this.visitData(new GenericAction<T>() {
			@Override
			public void consume(final T value) {
				snapShot.add(value);
			}
		});
		return snapShot;
	}
}


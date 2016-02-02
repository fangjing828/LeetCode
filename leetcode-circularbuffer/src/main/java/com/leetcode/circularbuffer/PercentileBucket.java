package com.leetcode.circularbuffer;

import java.util.concurrent.atomic.AtomicInteger;

public class PercentileBucket<T> extends Bucket {
	private final T[] data;
	private final AtomicInteger count;

	@SuppressWarnings("unchecked")
	protected PercentileBucket(final long timeInMillis, final int capactity) {
		super(timeInMillis);
		this.data = (T[]) new Object[capactity];
		this.count = new AtomicInteger();
	}

	public void add(final T value) {
		final int index = (this.count.incrementAndGet() - 1) % this.data.length;
		this.data[index] = value;
	}


	/**
	 * The number of {@link PercentileBucket} data which less than bucket's length.
	 * @return
	 */
	public int count()
	{
		return Math.min(this.count.get(), this.data.length);
	}

	/**
	 * Get {@link PercentileBucket} data from designated spot.
	 * @param index designated spot.
	 * @return bucket's data.
	 */
	public T get(final int index)
	{
		return this.data[index];
	}

	int realCount() {
		return this.count.get();
	}
}

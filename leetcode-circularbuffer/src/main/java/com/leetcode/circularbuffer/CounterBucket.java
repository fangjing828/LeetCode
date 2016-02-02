package com.leetcode.circularbuffer;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

class CounterBucket<T> extends Bucket {
	private ConcurrentHashMap<T, AtomicInteger> counters;

	protected CounterBucket(final long timeInMillis) {
		super(timeInMillis);
		this.counters = new ConcurrentHashMap<T, AtomicInteger>();
	}

	protected ConcurrentHashMap<T, AtomicInteger> getCounters() {
		return this.counters;
	}

	protected void setCounters(final ConcurrentHashMap<T, AtomicInteger> counters) {
		this.counters = counters;
	}

	public int count(final T identity) {
		int count = 0;

		final AtomicInteger counter = this.counters.get(identity);
		if (counter != null) {
			count = counter.get();
		}

		return count;
	}

	public void increment(final T identity) {
		AtomicInteger counter = this.counters.get(identity);

		if (counter == null) {
			counter = new AtomicInteger();
			final AtomicInteger existedCounter = this.counters.putIfAbsent(identity, counter);
			if (existedCounter != null) {
				counter = existedCounter;
			}
		}

		counter.incrementAndGet();
	}
}

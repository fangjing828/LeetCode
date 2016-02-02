package com.leetcode.circularbuffer;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class IntegerPercentileBuffer extends PercentileBuffer<Long> {
	public IntegerPercentileBuffer(final int timeWindowInMilliseconds,
			final int bucketCount, final int capacity, final Time time) {
		super(timeWindowInMilliseconds, bucketCount, capacity, time);
	}

	/**
	 * Gets {@link Audit} data from circular buffer.
	 * @return The audit data.
	 */
	public Audit getAuditData() {
		final Audit audit = new Audit();
		this.visitData(new GenericAction<Long>(){
			@Override
			public void consume(final Long value) {
				audit.addData(value);
			}
		});

		if (audit.count() == 0) {
			audit.max(0).min(0);
		}

		return audit;
	}

	/**
	 * Get counts of data in circular buffer which greater than or equals to low.
	 * @param low
	 * @return
	 */
	public int getItemCountInRange(final long low) {
		final AtomicLong count = new AtomicLong(0);
		this.visitData(new GenericAction<Long>(){
			@Override
			public void consume(final Long value) {
				if (value.longValue() >= low) {
					count.incrementAndGet();
				}
			}
		});

		return count.intValue();
	}

	/**
	 * Get counts of data in circular buffer which greater than or equals to low and less than high.
	 * @param low
	 * @return
	 */
	public int getItemCountInRange(final long low, final long high) {
		final AtomicInteger count = new AtomicInteger(0);
		this.visitData(new GenericAction<Long>(){
			@Override
			public void consume(final Long value) {
				if ((value.longValue() >= low) && (value.longValue() < high)) {
					count.incrementAndGet();
				}
			}
		});

		return count.intValue();
	}

	public long getPercentile(final double percent) {
		final List<Long> snapshot = this.snapShot();
		Collections.sort(snapshot);

		if (snapshot.size() <= 0) {
			return 0;
		} else if (percent <= 0.0)
		{
			return snapshot.get(0);
		}
		else if (percent >= 100.0)
		{
			return snapshot.get(snapshot.size() - 1);
		}

		final int rank = (int)((percent * (snapshot.size() - 1)) / 100);
		return snapshot.get(rank);
	}

	public long getAuditDataAvg()
	{
		final AtomicInteger count = new AtomicInteger(0);
		final AtomicLong sum = new AtomicLong(0);
		this.visitData(new GenericAction<Long>(){
			@Override
			public void consume(final Long value) {
				count.incrementAndGet();
				sum.addAndGet(value);
			}
		});
		long avg = 0;
		if (count.intValue() > 0) {
			avg = Math.round(sum.doubleValue() / count.doubleValue());
		}
		return avg;
	}
}


package com.leetcode.stats.web;

import java.util.concurrent.atomic.AtomicLong;

public class Record {
	private final AtomicLong total = new AtomicLong(0);
	private final AtomicLong min = new AtomicLong(0);
	private final AtomicLong max = new AtomicLong(0);

	public void add(final long value) {
		total.addAndGet(value);
		setMax(value);
		setMin(value);
	}

	private void setMax(final long newValue) {
		while (true) {
			final long lMax = max.get();
			if ((lMax == 0) || (lMax < newValue)) {
				if (max.compareAndSet(lMax, newValue)) {
					break;
				}
			} else {
				break;
			}
		}
	}

	private void setMin(final long newValue) {
		while (true) {
			final long lMin = min.get();
			if ((lMin == 0) || (lMin > newValue)) {
				if (min.compareAndSet(lMin, newValue)) {
					break;
				}
			} else {
				break;
			}
		}
	}

	public long total() {
		return total.get();
	}

	public long min() {
		return min.get();
	}

	public long max() {
		return max.get();
	}

	public long avg(long count) {
		if (count > 0) {
			return total() / count;
		}

		return 0l;
	}
}

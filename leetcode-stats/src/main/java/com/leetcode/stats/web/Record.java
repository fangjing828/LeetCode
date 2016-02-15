package com.leetcode.stats.web;

import java.util.concurrent.atomic.AtomicLong;

public class Record {
	private final AtomicLong total = new AtomicLong(0);
	private final AtomicLong min = new AtomicLong(0);
	private final AtomicLong max = new AtomicLong(0);
	
	public void add(final long value) {
		this.total.addAndGet(value);
		this.setMax(value);
		this.setMin(value);
	}
	
	private void setMax(final long newValue) {
		while (true) {
			final long lMax = this.max.get();
			if (lMax == 0 || lMax < newValue) {
				if (this.max.compareAndSet(lMax, newValue)) {
					break;
				}
			} else {
				break;
			}
		}
	}
	
	private void setMin(final long newValue) {
		while (true) {
			final long lMin = this.min.get();
			if (lMin == 0 || lMin > newValue) {
				if (this.min.compareAndSet(lMin, newValue)) {
					break;
				}
			} else {
				break;
			}
		}
	}
	
	public long total() {
		return this.total.get();
	}
	
	public long min() {
		return this.min.get();
	}
	
	public long max() {
		return this.max.get();
	}
	
	
}

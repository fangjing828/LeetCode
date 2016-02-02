package com.leetcode.circularbuffer;


/**
 * Store static data from {@link PercentileBuffer} as follows:
 * 1) the count and sum of data in {@link PercentileBuffer},
 * 2) the maximal and minimal data data in {@link PercentileBuffer}.
 * @author fang_j
 * @date 18/01/2016
 */
public class Audit {
	private int count;
	private long sum;
	private long min = Long.MAX_VALUE;
	private long max = Long.MIN_VALUE;

	/**
	 * 
	 * @return The count of in {@link PercentileBuffer}.
	 */
	public int count() {
		return this.count;
	}

	/**
	 * 
	 * @return The sum of in {@link PercentileBuffer}.
	 */
	public long sum() {
		return this.sum;
	}

	/**
	 * 
	 * @return The minimal data in {@link PercentileBuffer}.
	 */
	public long min() {
		return this.min;
	}

	/**
	 * Set minimal value of audit.
	 * @param min The new minimal value of audit.
	 * @return current audit.
	 */
	public Audit min(final long min) {
		this.min = min;
		return this;
	}

	/**
	 * 
	 * @return The maximal data in {@link PercentileBuffer}.
	 */
	public long max() {
		return this.max;
	}

	/**
	 * Set maximal value of audit.
	 * @param max The new maximal value of audit.
	 * @return current audit.
	 */
	public Audit max(final long max) {
		this.max = max;
		return this;
	}

	/**
	 * Add {@link PercentileBuffer} data to current audit.
	 * @param data The {@link PercentileBuffer} data.
	 * @return current audit.
	 */
	public Audit addData(final long data) {
		this.count ++;
		this.sum += data;
		if (data < this.min) {
			this.min = data;
		}
		if (data > this.max) {
			this.max = data;
		}
		return this;
	}
}

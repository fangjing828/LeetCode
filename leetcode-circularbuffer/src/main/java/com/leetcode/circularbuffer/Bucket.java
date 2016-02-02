package com.leetcode.circularbuffer;
/**
 * The {@code Bucket} is used for storing statistical data.
 * @author fang_j
 * @date 18/01/2016
 */
abstract class Bucket {
	private final long timeInMillis;

	/**
	 * Instance a bucket by specific time stamp.
	 * @param timeInMilliseconds
	 */
	protected Bucket(final long timeInMillis)
	{
		this.timeInMillis = timeInMillis;
	}

	public long timeInMillis() {
		return this.timeInMillis;
	};
}

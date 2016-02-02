package com.leetcode.circularbuffer;


public class MockTime implements Time{
	private final static MockTime instance = new MockTime();
	private long currentTimeInMills;

	private MockTime() {

	}


	public static long currentTimeInMillis() {
		return instance.getCurrentTimeInMillis();
	}

	public static void move(final long extra) {
		instance.moveCurrentTime(extra);
	}

	public static MockTime instance() {
		return instance;
	}

	@Override
	public long getCurrentTimeInMillis() {
		return this.currentTimeInMills;
	}

	public void moveCurrentTime(final long extra) {
		if (extra > 0) {
			this.currentTimeInMills += extra;
		}
	}

}
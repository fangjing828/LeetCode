package com.leetcode.circularbuffer;

import org.apache.commons.lang3.time.StopWatch;
/**
 * The timer of {@link CircularBuffer}.
 * @author fang_j
 *
 */
public class ActualTime implements Time{
	private static final ActualTime _instance = new ActualTime();
	private final StopWatch _stopWatch;

	private ActualTime() {
		this._stopWatch = new StopWatch();
		this._stopWatch.start();
	}

	public static ActualTime instance() {
		return _instance;
	}

	public static long currentTimeInMillis() {
		return _instance.getCurrentTimeInMillis();
	}

	@Override
	public long getCurrentTimeInMillis() {
		return this._stopWatch.getTime();
	}


}

package com.leetcode.random;

public class Random {
	public static long rndLong(final int max) {
		return Math.round(Math.random() * max);
	}

	public static boolean rndBoolean() {
		return Math.random() > 0.5;
	}

	public static int rndInteger(final int max) {
		return (int) rndLong(max);
	}

	public static int rndInteger (final int min, final int max) {
		return min + rndInteger(max - min);
	}
}

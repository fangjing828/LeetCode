package com.leetcode.random;

public class Letter {
	public static char random() {
		final long letterBeginPos = 'a';
		final long tmp = letterBeginPos + Random.rndLong(25);
		return (char) tmp;
	}

	public static String random(final int len) {
		final char[] chars = new char[len];
		for (int i = 0; i < len; i++) {
			chars[i] = random();
		}
		return new String(chars);
	}
}

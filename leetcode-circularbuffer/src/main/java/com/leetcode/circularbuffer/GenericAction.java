package com.leetcode.circularbuffer;

public abstract class GenericAction<T> {
	public abstract void consume(T value);
}

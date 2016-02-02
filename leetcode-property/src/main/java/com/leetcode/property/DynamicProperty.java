package com.leetcode.property;

/**
 * Provides an interface which extends {@link Property} can change hystrix property at runtime.
 * 
 * @author fang_j
 *
 * @param <T> The type of property value.
 */
public interface DynamicProperty<T> extends Property<T> {
	void set(T value);
}

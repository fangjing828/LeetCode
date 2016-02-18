package com.leetcode.property;

import java.util.ArrayList;
import java.util.List;

public class PropertyFactory {
	public static <T> Property<T> asProperty(final T value) {
		return new PropertyDefault<T>(value);
	}

	public static <T> Property<T> asProperty(final T value, final T defaultValue) {
		if (value != null) {
			return new PropertyDefault<T>(value);
		} else {
			return new PropertyDefault<T>(defaultValue);
		}
	}

	public static<T> Property<T> asProperty(final Property<T> property, final T value) {
		return new PropertyWrapperProperty<T>(property, value);
	}

	@SafeVarargs
	public static<T> Property<T> asProperty(final Property<T>... params) {
		final List<Property<T>> properties = new ArrayList<Property<T>>();
		for (final Property<T> property : params) {
			properties.add(property);
		}
		return new ChainedProperty<T>(properties);
	}

	public static<T> Property<T> asProperty(final List<Property<T>> properties) {
		return new ChainedProperty<T>(properties);
	}

	public static <T> DynamicProperty<T> asDynamicProperty(final T value)
	{
		return new DynamicPropertyDefault<T>(value);
	}

	public static <T> DynamicProperty<T> asDynamicProperty(final T value, final T defaultValue)
	{
		if (value != null) {
			return new DynamicPropertyDefault<T>(value);
		} else {
			return new DynamicPropertyDefault<T>(defaultValue);
		}
	}

	public static <T> Property<T> nullProperty()
	{
		return new NullProperty<T>();
	}

	private static class PropertyDefault<T> implements Property<T>{
		private final T value;

		public PropertyDefault (final T value) {
			this.value = value;
		}

		@Override
		public T get() {
			return this.value;
		}
	}

	private static class PropertyWrapperProperty<T> implements Property<T> {
		private T value;
		public PropertyWrapperProperty(final Property<T> property, final T value) {
			if (property.get() != null) {
				this.value = property.get();
			} else {
				this.value = value;
			}
		}

		@Override
		public T get() {
			return this.value;
		}
	}

	private static class ChainedProperty<T> implements Property<T> {
		private T value;

		public ChainedProperty(final List<Property<T>> items)
		{
			for (final Property<T> item : items) {
				final T v = item.get();
				if (v != null) {
					this.value = v;
					break;
				}
			}
		}

		@Override
		public T get() {
			return this.value;
		}

	}

	private static class DynamicPropertyDefault<T> implements DynamicProperty<T>
	{
		private T value;

		public DynamicPropertyDefault(final T value)
		{
			this.value = value;
		}

		@Override
		public T get() {
			return this.value;
		}

		@Override
		public void set(final T value) {
			this.value = value;
		}
	}

	private static class NullProperty<T> implements Property<T>
	{
		@Override
		public T get() {
			return null;
		}
	}
}


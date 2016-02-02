package com.leetcode.key;


/**
 * A base implementation for immutable keys to represent some objects.
 * Keys are equal if their types are the same and their names are equal with ordinal string comparison.
 * 
 * @author fang_j
 *
 */
public abstract class Key {
	private final String name;

	public Key(final String name) throws Exception {
		if ((name == null) || name.trim().isEmpty()) {
			throw new IllegalArgumentException("name");
		}
		this.name = name;
	}

	/**
	 * 
	 * @return Name of Hystrix key.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Determines whether the specified {@link Object} is equal to the current {@link Key}.
	 * 
	 * @param The object to compare with the current object.
	 * 
	 * @return true if the specified object is equal to the current object; otherwise, false.
	 */
	@Override
	public boolean equals(final Object o) {
		if (o instanceof Key) {
			return this.equals((Key) o);
		} else {
			return false;
		}
	}

	public boolean equals(final Key o) {
		if ((o == null) || (this.getClass() != o.getClass()) ) {
			return false;
		}

		return this.getName().equals(o.getName());
	}

	@Override
	public int hashCode() {
		return this.getName().hashCode();
	}

	@Override
	public String toString() {
		return this.getName().toString();
	}
}
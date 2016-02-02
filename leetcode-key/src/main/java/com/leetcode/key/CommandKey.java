package com.leetcode.key;

class CommandKey extends Key {
	public CommandKey(final String name) throws Exception {
		super(name);
	}

	public CommandKey(final Class<?> type) throws Exception {
		super(getDefaultNameForCommandType(type));
	}

	private static String getDefaultNameForCommandType(final Class<?> type) {
		if (type == null) {
			throw new IllegalArgumentException("commandType");
		}
		return type.getName();
	}
}

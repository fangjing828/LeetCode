package com.leetcode.key;

public class KeyFactory {
	public static Key getGroupKey(final String groupKeyName) throws Exception {
		return new GroupKey(groupKeyName);
	}

	public static Key getCommandKey(final String commandName) throws Exception {
		return new CommandKey(commandName);
	}

	public static Key getCommandKey(final Class<?> type) throws Exception {
		return new CommandKey(type);
	}
}

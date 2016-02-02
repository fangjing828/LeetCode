package com.leetcode.key;

import org.junit.Assert;
import org.junit.Test;

public class KeyFactoryTest {
	@Test(expected = IllegalArgumentException.class)
	public void withNullKeyTest() throws Exception{
		final String key = null;
		KeyFactory.getCommandKey(key);
	}

	@Test
	public void withStringKeyTest() throws Exception {
		final String key = "key";

		{
			final Key command = KeyFactory.getCommandKey(key);
			Assert.assertEquals(key, command.getName());
		}

		{
			final Key command = KeyFactory.getGroupKey(key);
			Assert.assertEquals(key, command.getName());
		}
	}

	@Test
	public void withTypeKeyTest() throws Exception {
		final Class<?> type = KeyFactoryTest.class;
		{
			final Key command = KeyFactory.getCommandKey(type);
			Assert.assertEquals(type.getName(), command.getName());
		}
	}

	@Test
	public void equalsTest() throws Exception {
		final String[] keys = new String[]{"key", "Key", "KEY"};
		for (final String key : keys) {
			final Key command = KeyFactory.getCommandKey(key);
			final Key groupcommand = KeyFactory.getGroupKey(key);
			Assert.assertNotEquals(true, command.equals(null));

			Assert.assertEquals(true, command.equals(command));
			Assert.assertEquals(true, groupcommand.equals(groupcommand));

			Assert.assertEquals(true, groupcommand.getName().equals(command.getName()));
			Assert.assertNotEquals(true, command.equals(groupcommand));
			Assert.assertNotEquals(true, groupcommand.equals(command));

			for (final String newKey : keys) {
				final Key newCommand = KeyFactory.getCommandKey(newKey);
				final Key newGroupCommand = KeyFactory.getGroupKey(newKey);

				Assert.assertEquals(newKey.equals(key), newCommand.equals(command));
				Assert.assertEquals(newKey.equals(key), newGroupCommand.equals(groupcommand));
			}
		}
	}
}

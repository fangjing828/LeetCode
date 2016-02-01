package com.leetcode.anagram;

import org.junit.Assert;
import org.junit.Test;

public class ValidAnagramTest {
	private final ValidAnagram validator = new ValidAnagram();

	@Test
	public void testIsAnagram() {
		{
			Assert.assertEquals(true, this.validator.isAnagram(null, null));
		}

		{
			final String s = "anagram";
			Assert.assertEquals(false, this.validator.isAnagram(s, null));
		}

		{
			final String s = "anagram";
			final String t = "agramna";
			Assert.assertEquals(true, this.validator.isAnagram(s, t));
		}

		{
			final String s = "cat";
			final String t = "car";
			Assert.assertEquals(false, this.validator.isAnagram(s, t));
		}
	}
}

package com.leetcode.stats.web;

import org.junit.Assert;
import org.junit.Test;

public class StatsKeyTest {
	@Test
	public void testEquals() {
		{
			final StatsKey key1 = new StatsKey("key1");
			final StatsKey key2 = new StatsKey("key2");
			Assert.assertEquals(false, key1.equals(key2));
		}
		
		{
			final StatsKey key1 = new StatsKey("key");
			final StatsKey key2 = new StatsKey("key");
			Assert.assertEquals(true, key1.equals(key2));
			
			key1.addTag("tag", "value");
			key2.addTag("tag", "value");
			Assert.assertEquals(true, key1.equals(key2));
			
			key1.addTag("tag1", "value1");
			key2.addTag("key2", "value2");
			Assert.assertEquals(false, key1.equals(key2));
		}
	}
}

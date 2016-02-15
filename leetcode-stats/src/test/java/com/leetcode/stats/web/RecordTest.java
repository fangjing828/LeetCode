package com.leetcode.stats.web;

import org.junit.Assert;
import org.junit.Test;

import com.leetcode.random.Random;

public class RecordTest {
	@Test
	public void testAdd() {
		int count = Random.rndInteger(10, 20);
		int total = 0;
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		Record record = new Record();
		
		for (int i = 0; i < count; i++) {
			int value = Random.rndInteger(0, 100);
			record.add(value);
			total += value;
			min = min <= value ? min : value;
			max = max >= value ? max : value;
		}
		
		Assert.assertEquals(total, record.total());
		Assert.assertEquals(min, record.min());
		Assert.assertEquals(max, record.max());
	}
}

package com.leetcode.circularbuffer;

import org.junit.Assert;
import org.junit.Test;

import com.leetcode.random.Random;

public class AuditTest {
	@Test
	public void addDataTest() {
		final Audit audit = new Audit();
		long min = Long.MAX_VALUE;
		long max = Long.MIN_VALUE;
		long sum = 0;
		for (int i = 0, count = Random.rndInteger(50, 100); i < count; i++) {
			final long data = Random.rndLong(1000);
			Assert.assertEquals(true, data >= 0);
			Assert.assertEquals(i, audit.count());
			min = min > data ? data : min;
			max = max < data ? data : max;
			sum += data;
			audit.addData(data);
			Assert.assertEquals(i + 1, audit.count());
			Assert.assertEquals(min, audit.min());
			Assert.assertEquals(max, audit.max());
			Assert.assertEquals(sum, audit.sum());
		}
	}
}

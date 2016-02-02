package com.leetcode.circularbuffer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.leetcode.random.Letter;
import com.leetcode.random.Random;


public class PercentileBucketTest {
	private final long timeInMilliseconds = Random.rndInteger(1000, 2000);
	private final int capacity = Random.rndInteger(10, 20);
	private PercentileBucket<String> bucket;

	@Before
	public void init() {
		this.bucket = new PercentileBucket<String>(this.timeInMilliseconds, this.capacity);
	}

	@Test
	public void countAndAddAndGetTest() {
		Assert.assertEquals(0, this.bucket.count());
		for (int i =0, len = Random.rndInteger(5, 10) * this.capacity; i < len; i++) {
			final String value = Letter.random(10);
			this.bucket.add(value);
			Assert.assertEquals(this.bucket.count(), Math.min(i + 1, this.capacity));
			Assert.assertEquals(value, this.bucket.get(i % this.capacity));
		}
	}
}

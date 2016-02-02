package com.leetcode.circularbuffer;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.leetcode.random.Letter;
import com.leetcode.random.Random;

public class CounterBucketTest {
	private final Set<String> keys = new HashSet<String>();
	private final CounterBucket<String> counter = new CounterBucket<String>(0);

	@Before
	public void init() {
		for (int i = 0,len = Random.rndInteger(5, 10); i < len; i++) {
			this.keys.add(Letter.random(20));
		}
	}

	@Test
	public void countTest() {
		final int limit = Random.rndInteger(5, 10);
		for (final String key : this.keys) {
			Assert.assertEquals(0, this.counter.count(key));
			for (int i = 0; i < limit; i++) {
				this.counter.increment(key);
				Assert.assertEquals(i + 1, this.counter.count(key));
			}
		}

		for (final String key : this.keys) {
			Assert.assertEquals(limit, this.counter.count(key));
		}
	}
}

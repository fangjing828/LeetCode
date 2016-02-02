package com.leetcode.circularbuffer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.leetcode.random.Letter;
import com.leetcode.random.Random;


public class CounterBufferTest {
	private final Set<String> keys = new HashSet<String>();
	private final int bucketCount = Random.rndInteger(10, 20);
	private final int bucketTimeWindowInMilliseconds = Random.rndInteger(2000);
	private final int timeWindowInMilliseconds = this.bucketCount * this.bucketTimeWindowInMilliseconds;
	private MockTime time;
	private CounterBuffer<String> counterBuffer;

	@Before
	public void init() {
		this.time = MockTime.instance();
		this.counterBuffer = new CounterBuffer<String>(this.timeWindowInMilliseconds, this.bucketCount, this.time);
		for (int i = 0,len = Random.rndInteger(5, 10); i < len; i++) {
			this.keys.add(Letter.random(20));
		}
	}

	@Test
	public void withStaticTimeTest() {
		final int limit = Random.rndInteger(5, 100);
		for (final String key : this.keys) {
			Assert.assertEquals(0, this.counterBuffer.count(key));
			for (int i = 0; i < limit; i++) {
				this.counterBuffer.increment(key);
				Assert.assertEquals(i + 1, this.counterBuffer.count(key));
			}
		}

		for (final String key : this.keys) {
			Assert.assertEquals(limit, this.counterBuffer.count(key));
		}

		Assert.assertEquals(this.counterBuffer.currentBucket().timeInMillis(), MockTime.currentTimeInMillis());
	}

	@Test
	public void withIncreasingOneBucketTimeWindowTest() {
		this.withIncreasingTimesTest(this.bucketTimeWindowInMilliseconds);
	}

	@Test
	public void withIncreasingOneAndHalfTimesBucketTimeWindowTest() {
		this.withIncreasingTimesTest((int) (this.bucketTimeWindowInMilliseconds * 1.5));
	}

	@Test
	public void withIncreasingHalfTimesBucketTimeWindowTest() {
		this.withIncreasingTimesTest((int) (this.bucketTimeWindowInMilliseconds * 0.5));
	}

	@Test
	public void withIncreasingRandomTimeTest() {
		this.withIncreasingTimesTest(Random.rndInteger(this.bucketTimeWindowInMilliseconds * 3));
	}

	private void withIncreasingTimesTest(final int step) {
		final int circle =  this.bucketCount * Random.rndInteger(100, 200);
		final HashMap<Long, HashMap<String, Integer>> history = new HashMap<Long, HashMap<String, Integer>>();
		for (int i = 0; i < circle; i++) {
			this.time.moveCurrentTime(step);

			final HashMap<String, Integer> record = new HashMap<String, Integer>();

			for (final String key : this.keys) {
				final int offset = this.countFromHistroy(history, key, this.time.getCurrentTimeInMillis());
				Assert.assertEquals(true, history.size() <= this.bucketCount);

				if (Random.rndBoolean()) {
					final int count = Random.rndInteger(1, 10);
					record.put(key, count);
					for (int j = 0; j < count; j++) {
						this.counterBuffer.increment(key);
						Assert.assertEquals(offset + j + 1, this.counterBuffer.count(key));
					}
					Assert.assertEquals(offset + count, this.counterBuffer.count(key));
				}
			}

			this.processHistory(history, this.counterBuffer.currentBucket().timeInMillis(), record);
		}
	}

	private void processHistory(final HashMap<Long, HashMap<String, Integer>> history, final long currentBucketTime, final HashMap<String, Integer> record) {
		if (history.containsKey(currentBucketTime)) {
			final HashMap<String, Integer> tmp = history.get(currentBucketTime);
			for (final String key : tmp.keySet()) {
				if (record.get(key) != null) {
					record.put(key, record.get(key) + tmp.get(key));
				} else {
					record.put(key, tmp.get(key));
				}
			}
		}
		history.put(currentBucketTime, record);
	}

	private int countFromHistroy(final  HashMap<Long, HashMap<String, Integer>> history, final String key, final long currentTime) {
		int offset = 0;
		final Set<Long> bucketTimes = new HashSet<Long>();
		for (final Long bucketTime : history.keySet()) {
			if ((currentTime - bucketTime) >= this.timeWindowInMilliseconds) {
				bucketTimes.add(bucketTime);
			} else {
				final Integer value = history.get(bucketTime).get(key);
				if (value != null) {
					offset += value;
				}
			}
		}

		for (final Long bucketTime : bucketTimes) {
			history.remove(bucketTime);
		}

		return offset;
	}
}
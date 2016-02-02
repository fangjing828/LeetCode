package com.leetcode.circularbuffer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.leetcode.random.Letter;
import com.leetcode.random.Random;

public class PercentileBufferTest {
	private final int bucketCount = Random.rndInteger(10, 20);
	private final int bucketTimeWindowInMilliseconds = Random.rndInteger(2000);
	private final int timeWindowInMilliseconds = this.bucketCount * this.bucketTimeWindowInMilliseconds;
	private final int capacity = Random.rndInteger(10,50);
	private MockTime time;
	private PercentileBuffer<String> percentileBuffer;

	@Before
	public void init() {
		this.time = MockTime.instance();
		this.percentileBuffer = new PercentileBuffer<String>(this.timeWindowInMilliseconds, this.bucketCount, this.capacity, this.time);
	}

	@Test
	public void withStaticTimeTest() {
		final String[] record = new String[this.capacity];
		for (int i = 0, len = Random.rndInteger(2,5) * this.capacity; i < len; i++) {
			final String value = Letter.random(10);
			this.percentileBuffer.add(value);
			record[i % this.capacity] = value;

			this.validate(record, this.percentileBuffer.snapShot());
		}
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
		final int circle =  this.bucketCount * Random.rndInteger(5, 10);

		final HashMap<Long, String[]> history = new LinkedHashMap<Long, String[]>();
		for (int i = 0; i < circle; i++) {
			this.time.moveCurrentTime(step);

			final int count = Random.rndInteger(this.capacity * Random.rndInteger(1, 3));
			final long bucketTime = this.percentileBuffer.currentBucket().timeInMillis();

			String[] record = history.get(bucketTime);
			if (record == null) {
				record = new String[this.capacity];
				history.put(bucketTime, record);
			}

			int index = this.percentileBuffer.currentBucket().realCount();

			for (int j= 0; j < count; j++, index++) {
				final String value = Letter.random(20);
				record[index % this.capacity] = value;
				this.percentileBuffer.add(value);
			}

			final List<String> historySnapshot =  this.getSnapShotFromHistroy(history, this.time.getCurrentTimeInMillis());
			final List<String> snapshot = this.percentileBuffer.snapShot();
			Assert.assertEquals(historySnapshot.size(), snapshot.size());

			final Set<String> compareValues = new HashSet<String>();
			for (final String value : historySnapshot) {
				compareValues.add(value);
			}

			for (final String value : snapshot) {
				if (!compareValues.contains(value)) {
					compareValues.contains(value);
				}
				Assert.assertTrue(compareValues.contains(value));
			}
		}
	}

	private void validate(final String[] record, final List<String> snapShot) {
		for (int i = 0; i < this.capacity; i++) {
			if (record[i] == null) {
				Assert.assertEquals(i, snapShot.size());
				break;
			} else {
				Assert.assertEquals(record[i], snapShot.get(i));
			}

		}
		final Set<String> set = new HashSet<String>();
		for (final String item : record) {
			set.add(item);
		}
		for (final String item : snapShot) {
			Assert.assertTrue(set.contains(item));
		}
	}

	private List<String> getSnapShotFromHistroy(final HashMap<Long, String[]> history, final long currentTime)
	{
		final List<String> snapShot = new ArrayList<String>();
		final Set<Long> bucketTimes = new HashSet<Long>();
		for (final Long bucketTime : history.keySet()) {
			if ((currentTime - bucketTime) >= this.timeWindowInMilliseconds) {
				bucketTimes.add(bucketTime);
			} else {
				final String[] bucket = history.get(bucketTime);
				if (bucket != null) {
					for(final String  item : bucket) {
						if (item != null) {
							snapShot.add(item);
						}
					}
				}
			}
		}

		for (final Long bucketTime : bucketTimes) {
			history.remove(bucketTime);
		}

		return snapShot;
	}
}

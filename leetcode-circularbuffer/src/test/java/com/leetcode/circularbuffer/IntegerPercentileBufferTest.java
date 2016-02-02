package com.leetcode.circularbuffer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.leetcode.random.Random;


public class IntegerPercentileBufferTest {
	private final int bucketCount = Random.rndInteger(100, 500);
	private final int bucketTimeWindowInMilliseconds = Random.rndInteger(2000);
	private final int timeWindowInMilliseconds = this.bucketCount * this.bucketTimeWindowInMilliseconds;
	private final int capacity = Random.rndInteger(10,50);
	private MockTime time;
	private IntegerPercentileBuffer percentileBuffer;

	@Before
	public void init() {
		this.time = MockTime.instance();
		this.percentileBuffer = new IntegerPercentileBuffer(this.timeWindowInMilliseconds, this.bucketCount, this.capacity, this.time);
	}

	@Test
	public void getAuditDataTest() {
		final List<Long> record = this.mockRecord();
		long max = Long.MIN_VALUE;
		long min = Long.MAX_VALUE;
		long sum = 0;
		long count = 0;
		final Audit audit = this.percentileBuffer.getAuditData();


		for (final Long item : record) {
			count++;
			max = Math.max(max, item);
			min = Math.min(min, item);
			sum += item;
		}

		Assert.assertEquals(count, audit.count());
		Assert.assertEquals(min, audit.min());
		Assert.assertEquals(max, audit.max());
		Assert.assertEquals(sum, audit.sum());
	}

	@Test
	public void getItemCountInRangeTest() {
		final List<Long> record = this.mockRecord();
		final Audit audit = this.percentileBuffer.getAuditData();
		final long low = Random.rndInteger((int) audit.min(), (int)audit.max());
		final long high = Random.rndInteger((int) low, (int)audit.max());

		int greatThanOrEqualLowCount = 0;
		int greatThanOrEqualLowAndLessThanHighCount = 0;

		for (final Long item : record) {
			if (item >= low) {
				greatThanOrEqualLowCount++;
				if (item < high) {
					greatThanOrEqualLowAndLessThanHighCount++;
				}
			}
		}
		Assert.assertEquals(greatThanOrEqualLowCount, this.percentileBuffer.getItemCountInRange(low));
		Assert.assertEquals(greatThanOrEqualLowAndLessThanHighCount, this.percentileBuffer.getItemCountInRange(low, high));
	}

	@Test
	public void getPercentileTest() {
		final List<Long> record = this.mockRecord();

		final Audit audit = this.percentileBuffer.getAuditData();
		Assert.assertEquals(audit.min(), this.percentileBuffer.getPercentile(0));
		Assert.assertEquals(audit.min(), this.percentileBuffer.getPercentile(-1));
		Assert.assertEquals(audit.max(), this.percentileBuffer.getPercentile(100));
		Assert.assertEquals(audit.max(), this.percentileBuffer.getPercentile(101));

		final long percent = Random.rndLong(100);

		final int rank = (int) ((percent * (record.size() - 1)) / 100);

		Collections.sort(record);

		Assert.assertEquals(record.get(rank).longValue(), this.percentileBuffer.getPercentile(percent));
	}

	private List<Long> mockRecord() {
		final Long[] record = new Long[this.capacity];
		for (int i = 0, len = Random.rndInteger(this.capacity * 2); i < len; i++) {
			final long value = Random.rndInteger(1, 2000);
			this.percentileBuffer.add(value);
			record[i % this.capacity] = value;
		}

		final List<Long> processRecord = new ArrayList<Long>();
		for (final Long item : record) {
			if (item == null) {
				break;
			}
			processRecord.add(item);
		}
		return processRecord;
	}
}

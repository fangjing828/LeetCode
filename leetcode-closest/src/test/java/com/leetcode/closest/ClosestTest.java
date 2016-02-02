package com.leetcode.closest;

import org.junit.Assert;
import org.junit.Test;

public class ClosestTest {
	private final Closest closest = new Closest();
	@Test
	public void testThreeSumClosest() {
		{
			final int[] nums = new int[]{-1, 2, 1, -4};
			final int target = 1;
			Assert.assertEquals(2, this.closest.threeSumClosest(nums, target));
		}
	}
}

package com.leetcode.stats.web;

import org.junit.Test;
import org.junit.Assert;

import com.leetcode.random.Random;


public class LongSlotsTest {
	private static String outOfMax = "max";
	private static String lessThan5 = "<5";
	private static String moreThan5AndLessThan10 ="6~10";
	
	@Test
	public void testGetSlot() {
		LongSlots slot = new LongSlots(outOfMax).slot(lessThan5, 5l).slot(moreThan5AndLessThan10, 10l);
		int count = Random.rndInteger(10, 20);
		for (int i = 0; i < count; i++) {
			long value = Random.rndInteger(0, 100);
			if (value <= 5) {
				Assert.assertEquals(lessThan5, slot.getSlot(value));
			} else if(value <= 10) {
				Assert.assertEquals(moreThan5AndLessThan10, slot.getSlot(value));
			} else {
				Assert.assertEquals(outOfMax, slot.getSlot(value));
			}
		}
	}
}

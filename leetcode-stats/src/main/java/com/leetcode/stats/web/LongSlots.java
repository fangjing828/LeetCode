package com.leetcode.stats.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LongSlots {
	private Map<Long, String> nameMap = new HashMap<Long, String>();
	private Long[] scales = new Long[0];
	private String outOfMax = "";
	
	public LongSlots() {}
	
	public LongSlots(String outOfMax) {
		this.outOfMax = outOfMax;
	}
	
	public synchronized LongSlots slot(String name, Long upperLimit) {
		this.nameMap.put(upperLimit, name);
		Long[] tmp = nameMap.keySet().toArray(new Long[0]);
        Arrays.sort(tmp);
        scales = tmp;
		return this;
	}
	
	public String getSlot(Long value) {
		for (Long scale : scales) {
			if (value <= scale) {
				return nameMap.get(scale);
			}
		}
		
		return this.outOfMax;
	}
}

package com.leetcode.stats.web;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ReqStats {
	private final AtomicLong count = new AtomicLong(0);

	private final Record cost = new Record();
	private final Record requestSize = new Record();
	private final Record responseSize = new Record();

	private final Map<String, AtomicInteger> requestSizeSlotCountMap = new HashMap<String, AtomicInteger>();
	private final Map<String, AtomicInteger> responseSizeSlotCountMap = new HashMap<String, AtomicInteger>();
	private final Map<String, AtomicInteger> costSlotCountMap = new HashMap<String, AtomicInteger>();
	private final Map<String, AtomicInteger> statusSlotCountMap = new HashMap<String, AtomicInteger>();

	private final LongSlots requestSizeSlots;
	private final LongSlots responseSizeSlots;
	private final LongSlots costSlots;

	public ReqStats(LongSlots requestSizeSlots, LongSlots responseSizeSlots, LongSlots costSlots) {
		this.requestSizeSlots = requestSizeSlots;
		this.responseSizeSlots = responseSizeSlots;
		this.costSlots = costSlots;
	}

	public void addReqInfo(long requestSize, long responseSize, long cost, String status) {
		count.incrementAndGet();

		this.cost.add(cost);
		this.requestSize.add(requestSize);
		this.responseSize.add(responseSize);

		addCountMap(requestSizeSlots.getSlot(requestSize), requestSizeSlotCountMap);
		addCountMap(responseSizeSlots.getSlot(responseSize), responseSizeSlotCountMap);
		addCountMap(costSlots.getSlot(cost), costSlotCountMap);
		addCountMap(status, statusSlotCountMap);
	}

	public long getCount() {
		return count.get();
	}

	public Record getCost() {
		return cost;
	}

	public Record getRequestSize() {
		return requestSize;
	}

	public Record getResponseSize() {
		return responseSize;
	}

	public Map<String, AtomicInteger> getCostSlotCountMap() {
		return costSlotCountMap;
	}

	public Map<String, AtomicInteger> getRequestSizeSlotCountMap() {
		return requestSizeSlotCountMap;
	}

	public Map<String, AtomicInteger> getResponseSizeSlotCountMap() {
		return responseSizeSlotCountMap;
	}

	public Map<String, AtomicInteger> getStatusSlotCountMap() {
		return statusSlotCountMap;
	}

	private void addCountMap(String key, Map<String, AtomicInteger> map) {
		AtomicInteger count = map.get(key);
		if (count == null) {
			count = new AtomicInteger(0);
			map.put(key, count);
		}
		count.getAndIncrement();
	}
}

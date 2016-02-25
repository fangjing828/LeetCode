package com.leetcode.stats.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogReqStatsCollector extends ReqStatsCollector{
	private static final Logger logger = LoggerFactory.getLogger(ReqStatsCollector.class);
	public LogReqStatsCollector(LongSlots requestSizeSlots, LongSlots reponseSizeSlots, LongSlots costSlots) {
		super(requestSizeSlots, reponseSizeSlots, costSlots);
	}

	@Override
	protected void reportMetrics(
			ConcurrentMap<StatsKey, ReqStats> statsKeyStatsConcurrentMap,
			Date date) {
		for (Map.Entry<StatsKey, ReqStats> entry : statsKeyStatsConcurrentMap.entrySet()) {
			StatsKey k = entry.getKey();
			ReqStats s = entry.getValue();

			String name = k.getName();
			Map<String, String> tags = new HashMap<String, String>(k.getTags());

			if (k.reportCount()) {
				logger.info(name + ".req.count", s.getCount(), tags, date);
			}

			if (k.reportCost()) {
				tags = new HashMap<String, String>(k.getTags());
				Record cost = s.getCost();
				tags.put("type", "avg");
				logger.info(name + ".req.cost", cost.avg(s.getCount()), tags, date);

				tags = new HashMap<String, String>(k.getTags());
				tags.put("type", "min");
				logger.info(name + ".req.cost", cost.min(), tags, date);

				tags = new HashMap<String, String>(k.getTags());
				tags.put("type", "max");
				logger.info(name + ".req.cost", cost.min(), tags, date);

				//Cost Distribution
				for (Map.Entry<String, AtomicInteger> ce : s.getCostSlotCountMap().entrySet()) {
					String range = ce.getKey();
					int rangeCount = ce.getValue().get();

					tags = new HashMap<String, String>(k.getTags());
					tags.put("range", range);
					logger.info(name + ".req.cost.distribution.count", rangeCount, tags, date);

					tags = new HashMap<String, String>(k.getTags());
					tags.put("range", range);
					logger.info(name + ".req.cost.distribution.rate", (rangeCount*100)/s.getCount(), tags, date);
				}

			}

			if (k.reportRequestSize()) {
				tags = new HashMap<String, String>(k.getTags());
				Record requestSize = s.getCost();
				tags.put("type", "avg");
				logger.info(name + ".req.req-size", requestSize.avg(s.getCount()), tags, date);

				tags = new HashMap<String, String>(k.getTags());
				tags.put("type", "min");
				logger.info(name + ".req.req-size", requestSize.min(), tags, date);

				tags = new HashMap<String, String>(k.getTags());
				tags.put("type", "max");
				logger.info(name + ".req.req-size", requestSize.min(), tags, date);

				//request size Distribution
				for (Map.Entry<String, AtomicInteger> ce : s.getRequestSizeSlotCountMap().entrySet()) {
					String range = ce.getKey();
					int rangeCount = ce.getValue().get();

					tags = new HashMap<String, String>(k.getTags());
					tags.put("range", range);
					logger.info(name + ".req.req-size.distribution.count", rangeCount, tags, date);

					tags = new HashMap<String, String>(k.getTags());
					tags.put("range", range);
					logger.info(name + ".req.req-size.distribution.rate", (rangeCount*100)/s.getCount(), tags, date);
				}
			}

			if (k.reportResponseSize()) {
				tags = new HashMap<String, String>(k.getTags());
				Record requestSize = s.getCost();
				tags.put("type", "avg");
				logger.info(name + ".req.res-size", requestSize.avg(s.getCount()), tags, date);

				tags = new HashMap<String, String>(k.getTags());
				tags.put("type", "min");
				logger.info(name + ".req.res-size", requestSize.min(), tags, date);

				tags = new HashMap<String, String>(k.getTags());
				tags.put("type", "max");
				logger.info(name + ".req.res-size", requestSize.min(), tags, date);

				//Response size Distribution
				for (Map.Entry<String, AtomicInteger> ce : s.getRequestSizeSlotCountMap().entrySet()) {
					String range = ce.getKey();
					int rangeCount = ce.getValue().get();

					tags = new HashMap<String, String>(k.getTags());
					tags.put("range", range);
					logger.info(name + ".req.res-size.distribution.count", rangeCount, tags, date);

					tags = new HashMap<String, String>(k.getTags());
					tags.put("range", range);
					logger.info(name + ".req.res-size.distribution.rate", (rangeCount*100)/s.getCount(), tags, date);
				}
			}

			if (k.reportStatus()) {
				//Status Distribution
				for (Map.Entry<String, AtomicInteger> ce : s.getStatusSlotCountMap().entrySet()) {
					String range = ce.getKey();
					int rangeCount = ce.getValue().get();

					tags = new HashMap<String, String>(k.getTags());
					tags.put("range", range);
					logger.info(name + ".req.status.distribution.count", rangeCount, tags, date);

					tags = new HashMap<String, String>(k.getTags());
					tags.put("range", range);
					logger.info(name + ".req.status.distribution.rate", (rangeCount*100)/s.getCount(), tags, date);
				}
			}
		}
	}
}

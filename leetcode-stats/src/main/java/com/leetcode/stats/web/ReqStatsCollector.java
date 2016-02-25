package com.leetcode.stats.web;


import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ReqStatsCollector {
	private static final Logger logger = LoggerFactory.getLogger(ReqStatsCollector.class);

	private final AtomicReference<ConcurrentMap<StatsKey, ReqStats>> ref = new AtomicReference<ConcurrentMap<StatsKey, ReqStats>>(new ConcurrentHashMap<StatsKey, ReqStats>());

	private long reportInterval = 60 * 1000;

	private final LongSlots requestSizeSlots;
	private final LongSlots responseSizeSlots;
	private final LongSlots costSlots;

	public ReqStatsCollector(LongSlots requestSizeSlots, LongSlots responseSizeSlots, LongSlots costSlots) {
		this.requestSizeSlots = requestSizeSlots;
		this.responseSizeSlots = responseSizeSlots;
		this.costSlots = costSlots;

		start();
	}

	public void req(StatsKey key, long responseSize, long cost, String status) {
		this.req(key, 0l, responseSize, cost, status);
	}

	public void req(StatsKey key, long requestSize, long responseSize, long cost, String status) {
		ReqStats stats = ref.get().get(key);
		if (stats == null) {
			stats = new ReqStats(requestSizeSlots, responseSizeSlots, costSlots);

			ReqStats found = ref.get().put(key, stats);

			if (found != null) {
				stats = found;
			}
		}

		stats.addReqInfo(requestSize, responseSize, cost, status);
	}

	public long getReportInterval() {
		return reportInterval;
	}

	public void setReportInterval(long reportInterval) {
		this.reportInterval = reportInterval;
	}
	private ConcurrentMap<StatsKey, ReqStats> drainDry() {
		return ref.getAndSet(new ConcurrentHashMap<StatsKey, ReqStats>());
	}

	private static final AtomicInteger NO = new AtomicInteger(0);
	protected abstract void reportMetrics(ConcurrentMap<StatsKey, ReqStats> statsKeyStatsConcurrentMap, Date date);

	private void start() {
		Thread t = new Thread(){
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(reportInterval);

						logger.info("start to report req metrics.");
						Date date = new Date();
						reportMetrics(drainDry(), date);

					} catch (Exception e) {
						e.printStackTrace();
						logger.warn("Encounter an error while reporting req metrics.");
					}finally {
						logger.info("report req metrics over.");
					}
				}
			}
		};
		t.setName(this.getClass().getSimpleName() + "-" + NO.incrementAndGet());
		t.setDaemon(true);
		t.start();
	}

}

package com.leetcode.stats.web;

import java.util.HashMap;
import java.util.Map;

public class StatsKey {
	private final String name;
	private final Map<String, String> tags = new HashMap<String, String>();
	private boolean needReportCount = true;
	private boolean needReportCost = true;
	private boolean needReportRequestSize = true;
	private boolean needReportResponseSize = true;
	private boolean needReportStatus = true;

	public StatsKey(String name) {
		if ((name == null) || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Name must not be null.");
		}
		this.name = name;
	}

	public StatsKey reportCount(boolean needReportCount) {
		this.needReportCount = needReportCount;
		return this;
	}

	public StatsKey reportCost(boolean needReportCost) {
		this.needReportCost = needReportCost;
		return this;
	}

	public StatsKey reportRequestSize(boolean needReportRequestSize) {
		this.needReportRequestSize = needReportRequestSize;
		return this;
	}

	public StatsKey reportResponseSize(boolean needReportResponseSize) {
		this.needReportResponseSize = needReportResponseSize;
		return this;
	}

	public StatsKey reportStatus(boolean needReportStatus) {
		this.needReportStatus = needReportStatus;
		return this;
	}

	public StatsKey addTag(String name, String value) {
		if ((name == null) || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Name must not be null.");
		}
		if ((value == null) || value.trim().isEmpty()) {
			throw new IllegalArgumentException("Value must not be null.");
		}

		this.tags.put(name, value);
		return this;
	}

	public String getName() {
		return this.name;
	}

	public boolean reportCount() {
		return this.needReportCount;
	}

	public boolean reportCost() {
		return this.needReportCost;
	}

	public boolean reportRequestSize() {
		return this.needReportRequestSize;
	}

	public boolean reportResponseSize() {
		return this.needReportResponseSize;
	}

	public boolean reportStatus() {
		return this.needReportStatus;
	}

	public Map<String, String> getTags() {
		return this.tags;
	}

	@Override
	public int hashCode() {
		int res = 17;
		res = (37 * res) + this.name.hashCode();
		res = (37 * res) + this.tags.toString().hashCode();
		return res;
	}

	@Override
	public boolean equals(Object obj) {
		boolean isEquals = false;
		if ((obj != null) && (obj instanceof StatsKey)) {
			final StatsKey s = (StatsKey) obj;

			isEquals = this.name.equals(s.name) && (this.tags.size() == s.tags.size());
			if (isEquals) {
				for (final String key : this.tags.keySet()) {
					if (!s.tags.containsKey(key)) {
						isEquals = false;
						break;
					}
				}
			}

		}

		return isEquals;
	}
}

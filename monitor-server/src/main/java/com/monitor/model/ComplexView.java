package com.monitor.model;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 复合视图
 * @author hui.Zheng
 * @date 2012-7-21
 */
public class ComplexView {
	private String title;
	private List<String> metricNames = Lists.newArrayList();
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<String> getMetricNames() {
		return metricNames;
	}
	public void setMetricNames(List<String> metricNames) {
		this.metricNames = metricNames;
	}
	
}

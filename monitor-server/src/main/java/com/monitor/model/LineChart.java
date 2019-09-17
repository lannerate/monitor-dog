package com.monitor.model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghui
 * Date: 14-6-26
 * Time: 下午2:25
 * To change this template use File | Settings | File Templates.
 */
public class LineChart {
   private List<String> categories;

   private List<Long> series;

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<Long> getSeries() {
        return series;
    }

    public void setSeries(List<Long> series) {
        this.series = series;
    }
}

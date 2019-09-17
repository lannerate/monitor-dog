package com.monitor.model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghui
 * Date: 14-6-26
 * Time: 上午10:55
 * To change this template use File | Settings | File Templates.
 */
public class Rank {

    private List<RankValue> percent;

    public List<RankValue> getPercent() {
        return percent;
    }

    public void setPercent(List<RankValue> percent) {
        this.percent = percent;
    }

}

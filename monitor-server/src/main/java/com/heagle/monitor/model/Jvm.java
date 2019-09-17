package com.monitor.model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghui
 * Date: 14-6-26
 * Time: 上午10:55
 * To change this template use File | Settings | File Templates.
 */
public class Jvm {

    private List<CellValue> numerical;
    private List<CellValue> percent;

    public List<CellValue> getPercent() {
        return percent;
    }

    public void setPercent(List<CellValue> percent) {
        this.percent = percent;
    }

    public List<CellValue> getNumerical() {
        return numerical;
    }

    public void setNumerical(List<CellValue> numerical) {
        this.numerical = numerical;
    }
}

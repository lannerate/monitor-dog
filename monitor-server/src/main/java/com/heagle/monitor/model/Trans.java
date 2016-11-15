package com.heagle.monitor.model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghui
 * Date: 14-6-26
 * Time: 上午10:55
 * To change this template use File | Settings | File Templates.
 */
public class Trans {

    private List<CellValue> numerical;

    public List<CellValue> getNumerical() {
        return numerical;
    }

    public void setNumerical(List<CellValue> numerical) {
        this.numerical = numerical;
    }
}

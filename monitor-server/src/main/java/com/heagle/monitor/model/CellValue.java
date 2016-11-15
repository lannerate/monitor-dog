package com.heagle.monitor.model;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghui
 * Date: 14-6-26
 * Time: 上午11:04
 * To change this template use File | Settings | File Templates.
 */
public class CellValue {

    private String label;

    private String status;

    public CellValue(){

    }
    public CellValue(String label, String status){
        this.label = label;
        this.status = status;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }


}

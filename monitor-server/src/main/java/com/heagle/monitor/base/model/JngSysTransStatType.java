package com.monitor.base.model;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghui
 * Date: 14-7-14
 * Time: 上午10:11
 * To change this template use File | Settings | File Templates.
 */
public enum  JngSysTransStatType {

    STAT_ALL("所有交易类型","stat_all");

    private String name;
    private String value;

    private JngSysTransStatType(String name,String value){
        this.name = name;
        this.value = value;
    }

    public String getName(){
        return name;
    }

    public String getValue(){
        return value;
    }
}

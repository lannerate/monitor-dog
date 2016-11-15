package com.heagle.monitor.model;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghui
 * Date: 14-7-30
 * Time: 下午5:22
 * To change this template use File | Settings | File Templates.
 */
public enum IpEnum {

    Node28("28","199.78.1.28"),
    Node29("29","199.78.1.29");

    private String key;
    private String val;

   IpEnum(String key,String val){
    this.key = key;
    this.val = val;
   }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}

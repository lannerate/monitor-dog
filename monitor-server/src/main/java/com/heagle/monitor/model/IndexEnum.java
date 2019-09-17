package com.monitor.model;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghui
 * Date: 14-7-30
 * Time: 下午5:22
 * To change this template use File | Settings | File Templates.
 */
public enum IndexEnum {

    memoryUsed("内存使用(M)","memoryUsed (M)"),
    cpuUsed("CPU使用率","cpuUsed"),
    threadCount("线程数","threadCount"),
    diskUsedRatio("磁盘使用率","diskUsedRate (百分比)"),
    heapMemoryUsed("堆使用(M)","heapMemoryUsed (M)"),
    noheapMemoryUsed("非堆使用(M)","noheapMemoryUsed (M)"),
    codeCache("字节码缓存(M)","CodeCache (M)"),
    edenSpace("Eden分区(M)","EdenSpace (M)"),
    survivorSpace("Survivor分区(M)","SurvivorSpace (M)"),
    permGen("方法区(M)","PermGen (M)"),
    totalNetworkRx("网络接收量(M)","totalNetworkRx (M)"),
    totalNetworkTx("网络发送量(M)","totalNetworkTx (M)");


    private String name;
    private String value;

   IndexEnum(String name, String value){
    this.name = name;
    this.value = value;
   }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

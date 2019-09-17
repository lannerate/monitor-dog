package com.monitor.model;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghui
 * Date: 14-6-26
 * Time: 上午10:55
 * To change this template use File | Settings | File Templates.
 */
public class DurationTran {

    private String tranNo;
    private String processCode;
    private Long maxDuration;
    private Long duration;
    private Long topLevel;

    public DurationTran(String tranNo,String processCode,Long duration,Long topLevel,Long maxDuration){
       this.tranNo = tranNo;
       this.processCode = processCode;
       this.duration = duration;
       this.topLevel = topLevel;
       this.maxDuration = maxDuration;
    }

    public String getProcessCode() {
        return processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getTranNo() {
        return tranNo;

    }

    public void setTranNo(String tranNo) {
        this.tranNo = tranNo;
    }

    public Long getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(Long maxDuration) {
        this.maxDuration = maxDuration;
    }

    public Long getTopLevel() {
        return topLevel;
    }

    public void setTopLevel(Long topLevel) {
        this.topLevel = topLevel;
    }
}

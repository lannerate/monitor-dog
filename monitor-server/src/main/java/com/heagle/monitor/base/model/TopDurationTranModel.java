package com.heagle.monitor.base.model;

import java.io.Serializable;

public class TopDurationTranModel implements Serializable {
   private String tranNo;
   private String processCode;
   private Long duration;
   private Long topLevel;

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



    public Long getTopLevel() {
        return topLevel;
    }

    public void setTopLevel(Long topLevel) {
        this.topLevel = topLevel;
    }
}
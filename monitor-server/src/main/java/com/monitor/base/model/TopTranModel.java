package com.monitor.base.model;

import java.io.Serializable;

public class TopTranModel implements Serializable {
   private String processCode;

   private Long topCount;

    public String getProcessCode() {
        return processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public Long getTopCount() {
        return topCount;
    }

    public void setTopCount(Long topCount) {
        this.topCount = topCount;
    }
}
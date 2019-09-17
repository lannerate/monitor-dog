package com.monitor.base.model;

import java.io.Serializable;

public class TopOnlineModel implements Serializable {
   private String ipEare;
   private String ipEarecn;

   private Long topCount;
   private Long topLevel;

    public String getIpEare() {
        return ipEare;
    }

    public void setIpEare(String ipEare) {
        this.ipEare = ipEare;
    }

    public String getIpEarecn() {
        return ipEarecn;
    }

    public void setIpEarecn(String ipEarecn) {
        this.ipEarecn = ipEarecn;
    }

    public Long getTopCount() {
        return topCount;
    }

    public void setTopCount(Long topCount) {
        this.topCount = topCount;
    }

    public Long getTopLevel() {
        return topLevel;
    }

    public void setTopLevel(Long topLevel) {
        this.topLevel = topLevel;
    }
}
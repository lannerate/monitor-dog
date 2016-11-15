
package com.heagle.monitor.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
* @author hui.zhang
 *
 * 交易信息
 */
@Document
public class TransTrendValue {
    @Id
    private String id;

    private String trans_type;
    private long trans_stat_count;
    private int trans_stat_interval;
    private long trans_stat_time;
    private long timeStamp = new Date().getTime();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrans_type() {
        return trans_type;
    }

    public void setTrans_type(String trans_type) {
        this.trans_type = trans_type;
    }

    public long getTrans_stat_count() {
        return trans_stat_count;
    }

    public void setTrans_stat_count(long trans_stat_count) {
        this.trans_stat_count = trans_stat_count;
    }

    public int getTrans_stat_interval() {
        return trans_stat_interval;
    }

    public void setTrans_stat_interval(int trans_stat_interval) {
        this.trans_stat_interval = trans_stat_interval;
    }

    public long getTrans_stat_time() {
        return trans_stat_time;
    }

    public void setTrans_stat_time(long trans_stat_time) {
        this.trans_stat_time = trans_stat_time;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}

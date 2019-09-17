
package com.monitor.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
* @author hui.zhang
 *
 * 交易信息
 */
@Document
public class TransValue{
    @Id
    private String id;

    private String trans_jnls_no;
    private String online_jnls_no;
    private String session_id;
    private String oper_no;
    private String process_code;
    private long trans_begin_time;
    private long trans_end_time;
    private long trans_duration;
    private int trans_flag;
    private long timeStamp = new Date().getTime();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrans_jnls_no() {
        return trans_jnls_no;
    }

    public void setTrans_jnls_no(String trans_jnls_no) {
        this.trans_jnls_no = trans_jnls_no;
    }

    public String getOnline_jnls_no() {
        return online_jnls_no;
    }

    public void setOnline_jnls_no(String online_jnls_no) {
        this.online_jnls_no = online_jnls_no;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getOper_no() {
        return oper_no;
    }

    public void setOper_no(String oper_no) {
        this.oper_no = oper_no;
    }

    public String getProcess_code() {
        return process_code;
    }

    public void setProcess_code(String process_code) {
        this.process_code = process_code;
    }

    public long getTrans_begin_time() {
        return trans_begin_time;
    }

    public void setTrans_begin_time(long trans_begin_time) {
        this.trans_begin_time = trans_begin_time;
    }

    public long getTrans_end_time() {
        return trans_end_time;
    }

    public void setTrans_end_time(long trans_end_time) {
        this.trans_end_time = trans_end_time;
    }

    public long getTrans_duration() {
        return trans_duration;
    }

    public void setTrans_duration(long trans_duration) {
        this.trans_duration = trans_duration;
    }

    public int getTrans_flag() {
        return trans_flag;
    }

    public void setTrans_flag(int trans_flag) {
        this.trans_flag = trans_flag;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}

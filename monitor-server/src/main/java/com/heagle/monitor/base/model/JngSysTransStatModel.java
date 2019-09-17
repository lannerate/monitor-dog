package com.monitor.base.model;

import java.io.Serializable;
import java.util.Date;

public class JngSysTransStatModel implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JNG_SYS_TRANS_LIST_STAT.GUID
     *
     * @mbggenerated
     */
    private String guid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JNG_SYS_TRANS_LIST_STAT.TRANS_TYPE
     *
     * @mbggenerated
     */
    private String transType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JNG_SYS_TRANS_LIST_STAT.TRANS_STAT_COUNT
     *
     * @mbggenerated
     */
    private Long transStatCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JNG_SYS_TRANS_LIST_STAT.TRANS_STAT_INTERVAL
     *
     * @mbggenerated
     */
    private Integer transStatInterval;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JNG_SYS_TRANS_LIST_STAT.TRANS_STAT_TIME
     *
     * @mbggenerated
     */
    private Date transStatTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table JNG_SYS_TRANS_LIST_STAT
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JNG_SYS_TRANS_LIST_STAT.GUID
     *
     * @return the value of JNG_SYS_TRANS_LIST_STAT.GUID
     *
     * @mbggenerated
     */
    public String getGuid() {
        return guid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JNG_SYS_TRANS_LIST_STAT.GUID
     *
     * @param guid the value for JNG_SYS_TRANS_LIST_STAT.GUID
     *
     * @mbggenerated
     */
    public void setGuid(String guid) {
        this.guid = guid == null ? null : guid.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JNG_SYS_TRANS_LIST_STAT.TRANS_TYPE
     *
     * @return the value of JNG_SYS_TRANS_LIST_STAT.TRANS_TYPE
     *
     * @mbggenerated
     */
    public String getTransType() {
        return transType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JNG_SYS_TRANS_LIST_STAT.TRANS_TYPE
     *
     * @param transType the value for JNG_SYS_TRANS_LIST_STAT.TRANS_TYPE
     *
     * @mbggenerated
     */
    public void setTransType(String transType) {
        this.transType = transType == null ? null : transType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JNG_SYS_TRANS_LIST_STAT.TRANS_STAT_COUNT
     *
     * @return the value of JNG_SYS_TRANS_LIST_STAT.TRANS_STAT_COUNT
     *
     * @mbggenerated
     */
    public Long getTransStatCount() {
        return transStatCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JNG_SYS_TRANS_LIST_STAT.TRANS_STAT_COUNT
     *
     * @param transStatCount the value for JNG_SYS_TRANS_LIST_STAT.TRANS_STAT_COUNT
     *
     * @mbggenerated
     */
    public void setTransStatCount(Long transStatCount) {
        this.transStatCount = transStatCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JNG_SYS_TRANS_LIST_STAT.TRANS_STAT_INTERVAL
     *
     * @return the value of JNG_SYS_TRANS_LIST_STAT.TRANS_STAT_INTERVAL
     *
     * @mbggenerated
     */
    public Integer getTransStatInterval() {
        return transStatInterval;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JNG_SYS_TRANS_LIST_STAT.TRANS_STAT_INTERVAL
     *
     * @param transStatInterval the value for JNG_SYS_TRANS_LIST_STAT.TRANS_STAT_INTERVAL
     *
     * @mbggenerated
     */
    public void setTransStatInterval(Integer transStatInterval) {
        this.transStatInterval = transStatInterval;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JNG_SYS_TRANS_LIST_STAT.TRANS_STAT_TIME
     *
     * @return the value of JNG_SYS_TRANS_LIST_STAT.TRANS_STAT_TIME
     *
     * @mbggenerated
     */
    public Date getTransStatTime() {
        return transStatTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JNG_SYS_TRANS_LIST_STAT.TRANS_STAT_TIME
     *
     * @param transStatTime the value for JNG_SYS_TRANS_LIST_STAT.TRANS_STAT_TIME
     *
     * @mbggenerated
     */
    public void setTransStatTime(Date transStatTime) {
        this.transStatTime = transStatTime;
    }
}
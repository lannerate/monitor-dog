package com.monitor.base.model;

import java.io.Serializable;
import java.util.Date;

public class JngSysTransModel implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JNG_SYS_TRANS_LIST.TRANS_JNLS_NO
     *
     * @mbggenerated
     */
    private String transJnlsNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JNG_SYS_TRANS_LIST.ONLINE_JNLS_NO
     *
     * @mbggenerated
     */
    private String onlineJnlsNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JNG_SYS_TRANS_LIST.SESSION_ID
     *
     * @mbggenerated
     */
    private String sessionId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JNG_SYS_TRANS_LIST.OPER_NO
     *
     * @mbggenerated
     */
    private String operNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JNG_SYS_TRANS_LIST.REQUEST_URL
     *
     * @mbggenerated
     */
    private String requestUrl;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JNG_SYS_TRANS_LIST.PROCESS_CODE
     *
     * @mbggenerated
     */
    private String processCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JNG_SYS_TRANS_LIST.REQUEST_CONTENT
     *
     * @mbggenerated
     */
    private String requestContent;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JNG_SYS_TRANS_LIST.TRANS_BEGIN_TIME
     *
     * @mbggenerated
     */
    private Date transBeginTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JNG_SYS_TRANS_LIST.TRANS_END_TIME
     *
     * @mbggenerated
     */
    private Date transEndTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JNG_SYS_TRANS_LIST.OPER_CHECK_STATUS
     *
     * @mbggenerated
     */
    private String operCheckStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JNG_SYS_TRANS_LIST.OPER_CHECK_TIME
     *
     * @mbggenerated
     */
    private Date operCheckTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JNG_SYS_TRANS_LIST.REQUEST_CHECK_STATUS
     *
     * @mbggenerated
     */
    private String requestCheckStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JNG_SYS_TRANS_LIST.REQUEST_CHECK_TIME
     *
     * @mbggenerated
     */
    private Date requestCheckTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JNG_SYS_TRANS_LIST.AUTH_CHECK_STATUS
     *
     * @mbggenerated
     */
    private String authCheckStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JNG_SYS_TRANS_LIST.AUTH_CHECK_TIME
     *
     * @mbggenerated
     */
    private Date authCheckTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JNG_SYS_TRANS_LIST.JSON_CHECK_STATUS
     *
     * @mbggenerated
     */
    private String jsonCheckStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JNG_SYS_TRANS_LIST.JSON_CHECK_TIME
     *
     * @mbggenerated
     */
    private Date jsonCheckTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JNG_SYS_TRANS_LIST.TRANS_STATUS
     *
     * @mbggenerated
     */
    private Long transStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JNG_SYS_TRANS_LIST.ERROR_MESS
     *
     * @mbggenerated
     */
    private String errorMess;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column JNG_SYS_TRANS_LIST.APP_TRANS_JNLS_NO
     *
     * @mbggenerated
     */
    private String appTransJnlsNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table JNG_SYS_TRANS_LIST
     *
     * @mbggenerated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JNG_SYS_TRANS_LIST.TRANS_JNLS_NO
     *
     * @return the value of JNG_SYS_TRANS_LIST.TRANS_JNLS_NO
     *
     * @mbggenerated
     */
    public String getTransJnlsNo() {
        return transJnlsNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JNG_SYS_TRANS_LIST.TRANS_JNLS_NO
     *
     * @param transJnlsNo the value for JNG_SYS_TRANS_LIST.TRANS_JNLS_NO
     *
     * @mbggenerated
     */
    public void setTransJnlsNo(String transJnlsNo) {
        this.transJnlsNo = transJnlsNo == null ? null : transJnlsNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JNG_SYS_TRANS_LIST.ONLINE_JNLS_NO
     *
     * @return the value of JNG_SYS_TRANS_LIST.ONLINE_JNLS_NO
     *
     * @mbggenerated
     */
    public String getOnlineJnlsNo() {
        return onlineJnlsNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JNG_SYS_TRANS_LIST.ONLINE_JNLS_NO
     *
     * @param onlineJnlsNo the value for JNG_SYS_TRANS_LIST.ONLINE_JNLS_NO
     *
     * @mbggenerated
     */
    public void setOnlineJnlsNo(String onlineJnlsNo) {
        this.onlineJnlsNo = onlineJnlsNo == null ? null : onlineJnlsNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JNG_SYS_TRANS_LIST.SESSION_ID
     *
     * @return the value of JNG_SYS_TRANS_LIST.SESSION_ID
     *
     * @mbggenerated
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JNG_SYS_TRANS_LIST.SESSION_ID
     *
     * @param sessionId the value for JNG_SYS_TRANS_LIST.SESSION_ID
     *
     * @mbggenerated
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId == null ? null : sessionId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JNG_SYS_TRANS_LIST.OPER_NO
     *
     * @return the value of JNG_SYS_TRANS_LIST.OPER_NO
     *
     * @mbggenerated
     */
    public String getOperNo() {
        return operNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JNG_SYS_TRANS_LIST.OPER_NO
     *
     * @param operNo the value for JNG_SYS_TRANS_LIST.OPER_NO
     *
     * @mbggenerated
     */
    public void setOperNo(String operNo) {
        this.operNo = operNo == null ? null : operNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JNG_SYS_TRANS_LIST.REQUEST_URL
     *
     * @return the value of JNG_SYS_TRANS_LIST.REQUEST_URL
     *
     * @mbggenerated
     */
    public String getRequestUrl() {
        return requestUrl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JNG_SYS_TRANS_LIST.REQUEST_URL
     *
     * @param requestUrl the value for JNG_SYS_TRANS_LIST.REQUEST_URL
     *
     * @mbggenerated
     */
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl == null ? null : requestUrl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JNG_SYS_TRANS_LIST.PROCESS_CODE
     *
     * @return the value of JNG_SYS_TRANS_LIST.PROCESS_CODE
     *
     * @mbggenerated
     */
    public String getProcessCode() {
        return processCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JNG_SYS_TRANS_LIST.PROCESS_CODE
     *
     * @param processCode the value for JNG_SYS_TRANS_LIST.PROCESS_CODE
     *
     * @mbggenerated
     */
    public void setProcessCode(String processCode) {
        this.processCode = processCode == null ? null : processCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JNG_SYS_TRANS_LIST.REQUEST_CONTENT
     *
     * @return the value of JNG_SYS_TRANS_LIST.REQUEST_CONTENT
     *
     * @mbggenerated
     */
    public String getRequestContent() {
        return requestContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JNG_SYS_TRANS_LIST.REQUEST_CONTENT
     *
     * @param requestContent the value for JNG_SYS_TRANS_LIST.REQUEST_CONTENT
     *
     * @mbggenerated
     */
    public void setRequestContent(String requestContent) {
        this.requestContent = requestContent == null ? null : requestContent.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JNG_SYS_TRANS_LIST.TRANS_BEGIN_TIME
     *
     * @return the value of JNG_SYS_TRANS_LIST.TRANS_BEGIN_TIME
     *
     * @mbggenerated
     */
    public Date getTransBeginTime() {
        return transBeginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JNG_SYS_TRANS_LIST.TRANS_BEGIN_TIME
     *
     * @param transBeginTime the value for JNG_SYS_TRANS_LIST.TRANS_BEGIN_TIME
     *
     * @mbggenerated
     */
    public void setTransBeginTime(Date transBeginTime) {
        this.transBeginTime = transBeginTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JNG_SYS_TRANS_LIST.TRANS_END_TIME
     *
     * @return the value of JNG_SYS_TRANS_LIST.TRANS_END_TIME
     *
     * @mbggenerated
     */
    public Date getTransEndTime() {
        return transEndTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JNG_SYS_TRANS_LIST.TRANS_END_TIME
     *
     * @param transEndTime the value for JNG_SYS_TRANS_LIST.TRANS_END_TIME
     *
     * @mbggenerated
     */
    public void setTransEndTime(Date transEndTime) {
        this.transEndTime = transEndTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JNG_SYS_TRANS_LIST.OPER_CHECK_STATUS
     *
     * @return the value of JNG_SYS_TRANS_LIST.OPER_CHECK_STATUS
     *
     * @mbggenerated
     */
    public String getOperCheckStatus() {
        return operCheckStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JNG_SYS_TRANS_LIST.OPER_CHECK_STATUS
     *
     * @param operCheckStatus the value for JNG_SYS_TRANS_LIST.OPER_CHECK_STATUS
     *
     * @mbggenerated
     */
    public void setOperCheckStatus(String operCheckStatus) {
        this.operCheckStatus = operCheckStatus == null ? null : operCheckStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JNG_SYS_TRANS_LIST.OPER_CHECK_TIME
     *
     * @return the value of JNG_SYS_TRANS_LIST.OPER_CHECK_TIME
     *
     * @mbggenerated
     */
    public Date getOperCheckTime() {
        return operCheckTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JNG_SYS_TRANS_LIST.OPER_CHECK_TIME
     *
     * @param operCheckTime the value for JNG_SYS_TRANS_LIST.OPER_CHECK_TIME
     *
     * @mbggenerated
     */
    public void setOperCheckTime(Date operCheckTime) {
        this.operCheckTime = operCheckTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JNG_SYS_TRANS_LIST.REQUEST_CHECK_STATUS
     *
     * @return the value of JNG_SYS_TRANS_LIST.REQUEST_CHECK_STATUS
     *
     * @mbggenerated
     */
    public String getRequestCheckStatus() {
        return requestCheckStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JNG_SYS_TRANS_LIST.REQUEST_CHECK_STATUS
     *
     * @param requestCheckStatus the value for JNG_SYS_TRANS_LIST.REQUEST_CHECK_STATUS
     *
     * @mbggenerated
     */
    public void setRequestCheckStatus(String requestCheckStatus) {
        this.requestCheckStatus = requestCheckStatus == null ? null : requestCheckStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JNG_SYS_TRANS_LIST.REQUEST_CHECK_TIME
     *
     * @return the value of JNG_SYS_TRANS_LIST.REQUEST_CHECK_TIME
     *
     * @mbggenerated
     */
    public Date getRequestCheckTime() {
        return requestCheckTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JNG_SYS_TRANS_LIST.REQUEST_CHECK_TIME
     *
     * @param requestCheckTime the value for JNG_SYS_TRANS_LIST.REQUEST_CHECK_TIME
     *
     * @mbggenerated
     */
    public void setRequestCheckTime(Date requestCheckTime) {
        this.requestCheckTime = requestCheckTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JNG_SYS_TRANS_LIST.AUTH_CHECK_STATUS
     *
     * @return the value of JNG_SYS_TRANS_LIST.AUTH_CHECK_STATUS
     *
     * @mbggenerated
     */
    public String getAuthCheckStatus() {
        return authCheckStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JNG_SYS_TRANS_LIST.AUTH_CHECK_STATUS
     *
     * @param authCheckStatus the value for JNG_SYS_TRANS_LIST.AUTH_CHECK_STATUS
     *
     * @mbggenerated
     */
    public void setAuthCheckStatus(String authCheckStatus) {
        this.authCheckStatus = authCheckStatus == null ? null : authCheckStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JNG_SYS_TRANS_LIST.AUTH_CHECK_TIME
     *
     * @return the value of JNG_SYS_TRANS_LIST.AUTH_CHECK_TIME
     *
     * @mbggenerated
     */
    public Date getAuthCheckTime() {
        return authCheckTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JNG_SYS_TRANS_LIST.AUTH_CHECK_TIME
     *
     * @param authCheckTime the value for JNG_SYS_TRANS_LIST.AUTH_CHECK_TIME
     *
     * @mbggenerated
     */
    public void setAuthCheckTime(Date authCheckTime) {
        this.authCheckTime = authCheckTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JNG_SYS_TRANS_LIST.JSON_CHECK_STATUS
     *
     * @return the value of JNG_SYS_TRANS_LIST.JSON_CHECK_STATUS
     *
     * @mbggenerated
     */
    public String getJsonCheckStatus() {
        return jsonCheckStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JNG_SYS_TRANS_LIST.JSON_CHECK_STATUS
     *
     * @param jsonCheckStatus the value for JNG_SYS_TRANS_LIST.JSON_CHECK_STATUS
     *
     * @mbggenerated
     */
    public void setJsonCheckStatus(String jsonCheckStatus) {
        this.jsonCheckStatus = jsonCheckStatus == null ? null : jsonCheckStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JNG_SYS_TRANS_LIST.JSON_CHECK_TIME
     *
     * @return the value of JNG_SYS_TRANS_LIST.JSON_CHECK_TIME
     *
     * @mbggenerated
     */
    public Date getJsonCheckTime() {
        return jsonCheckTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JNG_SYS_TRANS_LIST.JSON_CHECK_TIME
     *
     * @param jsonCheckTime the value for JNG_SYS_TRANS_LIST.JSON_CHECK_TIME
     *
     * @mbggenerated
     */
    public void setJsonCheckTime(Date jsonCheckTime) {
        this.jsonCheckTime = jsonCheckTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JNG_SYS_TRANS_LIST.TRANS_STATUS
     *
     * @return the value of JNG_SYS_TRANS_LIST.TRANS_STATUS
     *
     * @mbggenerated
     */
    public Long getTransStatus() {
        return transStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JNG_SYS_TRANS_LIST.TRANS_STATUS
     *
     * @param transStatus the value for JNG_SYS_TRANS_LIST.TRANS_STATUS
     *
     * @mbggenerated
     */
    public void setTransStatus(Long transStatus) {
        this.transStatus = transStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JNG_SYS_TRANS_LIST.ERROR_MESS
     *
     * @return the value of JNG_SYS_TRANS_LIST.ERROR_MESS
     *
     * @mbggenerated
     */
    public String getErrorMess() {
        return errorMess;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JNG_SYS_TRANS_LIST.ERROR_MESS
     *
     * @param errorMess the value for JNG_SYS_TRANS_LIST.ERROR_MESS
     *
     * @mbggenerated
     */
    public void setErrorMess(String errorMess) {
        this.errorMess = errorMess == null ? null : errorMess.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column JNG_SYS_TRANS_LIST.APP_TRANS_JNLS_NO
     *
     * @return the value of JNG_SYS_TRANS_LIST.APP_TRANS_JNLS_NO
     *
     * @mbggenerated
     */
    public String getAppTransJnlsNo() {
        return appTransJnlsNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column JNG_SYS_TRANS_LIST.APP_TRANS_JNLS_NO
     *
     * @param appTransJnlsNo the value for JNG_SYS_TRANS_LIST.APP_TRANS_JNLS_NO
     *
     * @mbggenerated
     */
    public void setAppTransJnlsNo(String appTransJnlsNo) {
        this.appTransJnlsNo = appTransJnlsNo == null ? null : appTransJnlsNo.trim();
    }
}
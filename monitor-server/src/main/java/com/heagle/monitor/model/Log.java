
package com.monitor.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hui.zhang
 */
@Document
public class Log {
    @Id
    private String id;
    private String message, level, className;
    private Date timestamp;
    private LoggerName loggerName;
    private String pid;
    private String ip;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LoggerName getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(LoggerName loggerName) {
        this.loggerName = loggerName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String toString() {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fdate = dateformat.format(timestamp);
        String theclassName = loggerName != null ? loggerName.getFullyQualifiedClassName() : className;
        StringBuffer buf = new StringBuffer();
        buf.append(fdate).append(" ");
        if(ip!=null){
            buf.append(pid).append(" ").append(ip).append(" ");
        }
        buf.append(level).append(" ");
        buf.append("[").append(theclassName).append("] - ");
        buf.append(message);//append("\n");
        return buf.toString();
    }


}

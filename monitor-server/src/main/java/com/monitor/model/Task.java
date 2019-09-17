
package com.monitor.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
* @author hui.zhang
 *
 * mongodb的脚本任务执行器
 */
@SuppressWarnings("unchecked")
public class Task implements Callable, IdentifyObject {
    private static Logger logger = LoggerFactory.getLogger(Task.class);

    public final static int FINE = 0;
    public final static int WARN = 1;
    public final static int ERROR = 2;

    private String script, name;
    private int interval = 5 * 60;
    private int timeout = 20;
    /**
     * 计划执行周期
     */
    private String cron = "5 * * * * *";

    @Override
    public Integer call() {
        try {

            logger.debug("run mongo script = {}", script);
//            CommandResult result = mongoDb.runCmd(script);
//            logger.debug("mongo task response {}", result);
            return FINE;
        } catch (Exception e) {
            logger.error("run mongo cmd error", e);
            return ERROR;
        }
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }


    public int getInterval() {
        return this.interval;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getTimeout() {
        return timeout;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    @Override
    public String toString() {
        return "Task{" +
        		", name='" + name + '\'' +
        		", cron='" + cron + '\'' +
                "script='" + script + '\'' +
                ", interval=" + interval +
                ", timeout=" + timeout +
                '}';
    }
}

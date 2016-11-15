
package com.heagle.monitor.service;

import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import com.heagle.monitor.model.Alert;
import com.heagle.monitor.model.MetricDog;
import com.heagle.monitor.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hui.zhang
 */
public class AlertService {
    private static Logger logger = LoggerFactory.getLogger(AlertService.class);

    private final static ScheduledExecutorService executor = Executors.newScheduledThreadPool(100);
    private static ConcurrentMap<String, AtomicInteger> notifyTimes;
    @Resource
    private List<AlertListener> alertListeners = Lists.newArrayList();
    @Resource
    ProjectService projectService;
    @Resource
    private MongoTemplate mongoTemplate;
    private String collectionName = "heagle_monitor_alerts";
    private int limitTimes = 10;
    private int limitMinutes = 60;

    public void setCheckSeconds(int checkSeconds) {
        this.checkSeconds = checkSeconds;
    }

    private int checkSeconds = 90;


    public void init() {

        executor.scheduleWithFixedDelay(
                new Runnable() {
                    @Override
                    public void run() {
                        watch();
                    }
                }, 15, checkSeconds, TimeUnit.SECONDS
        );
        notifyTimes = new MapMaker().expiration(limitMinutes, TimeUnit.MINUTES).makeMap();
    }

    private void watch() {
        for (Project project : projectService.findProjects()) {
            logger.info("start monitor of project {} ,monitors count={}", project.getName(), project.getMetricDogs().size());
            for (final MetricDog dog : project.getMetricDogs()) {
                if (dog.inWorking()) {
                    startDog(project, dog);
                }

            }
        }
    }

    private void startDog(final Project project, final MetricDog dog) {
        //为了避免同时执行，使用了随机数延迟执行
        executor.schedule(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            logger.debug("start monitor {}", dog);
                            List<Alert> alerts = dog.work(project);
                            notifyAlerts(alerts);
                        } catch (Exception e) {
                            logger.error("start monitor fail ", e);
                        }
                    }
                }, ((int) (Math.random() * 1000)) % 30, TimeUnit.SECONDS
        );

    }

    private void notifyAlerts(List<Alert> alerts) {
        for (Alert alert : alerts) {
            if (isNeedNotify(alert)) {
                notify(alert);
            } else {
                logger.info("out of limit times={} ,not notify this alert", limitTimes);
            }
        }

    }

    protected boolean isNeedNotify(Alert alert) {
        if (alert == null)
            return false;
        String key = alert.getProjectName() + "_" + alert.getTitle();

        AtomicInteger init = new AtomicInteger(0);
        AtomicInteger times = notifyTimes.putIfAbsent(key, init);
        if (times == null)
            times = init;
        logger.debug("{} notify times ={}", key, times.get());
        return times.getAndIncrement() < limitTimes;
    }

    private void notify(Alert alert) {
        logger.info("monitor fire {},notify listener {}", alert, alertListeners);
        mongoTemplate.save(alert, collectionName);
        for (AlertListener listener : alertListeners) {
            notify(alert, listener);
        }
    }

    private void notify(Alert alert, AlertListener listener) {
        try {
            listener.notify(alert);
        } catch (Exception e) {
            logger.error("notify listener fail ", e);
        }
    }

    public List<Alert> findAlerts(String projectName) {
        Query query = Query.query(Criteria.where("projectName").is(projectName)).limit(50);
        query.sort().on("createTime", Order.DESCENDING);
        return mongoTemplate.find(query, Alert.class, collectionName);
    }

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public void removeAlerts(String projectName) {
        mongoTemplate.remove(Query.query(Criteria.where("projectName").is(projectName)), collectionName);
    }

    public void setLimitTimes(int limitTimes) {
        this.limitTimes = limitTimes;
    }

    public void setLimitMinutes(int limitMinutes) {
        this.limitMinutes = limitMinutes;
    }

}

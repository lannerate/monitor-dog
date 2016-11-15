
package com.heagle.monitor.service;

import com.google.common.collect.Maps;
import com.heagle.monitor.model.Project;
import com.heagle.monitor.model.Task;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.util.Map;
import java.util.concurrent.*;

/**
 * @author hui.zhang
 */
@SuppressWarnings("unchecked")
public class TaskService {
    private static Logger logger = LoggerFactory.getLogger(TaskService.class);
    private ConcurrentTaskScheduler executor = new ConcurrentTaskScheduler();
    private final static Map<String, ScheduledFuture> futures = Maps.newHashMap();

    public void scheduledTask(final Project project, final Task task) {
        final String projectName = project.getName();
        //先取消老的任务
        removeScheduled(projectName, task);
        String taskKey = getTaskKey(projectName, task);
        ScheduledFuture<?> future = executor.schedule(new Runnable() {
            @Override
            public void run() {
            	long startTime = System.currentTimeMillis();
                try {
                    FutureTask _fuFutureTask = TaskService.this.runScript(task.getScript(), project);
                    _fuFutureTask.get(task.getTimeout(), TimeUnit.SECONDS);
                } catch (TimeoutException e) {
                    logger.error("execute task timeout,use  {} (second), project={} , script={}",
                    		new Object[]{(System.currentTimeMillis()-startTime)/1000, projectName,task});
                } catch (Exception e) {
                	logger.error("execute task ERROR,use  {} (second), project={} , script={}",
                    		new Object[]{(System.currentTimeMillis()-startTime)/1000, projectName,task});
                    logger.error("execute task fail", e);
                }
                logger.info("execute task success,use {} (second) , project={} , taskName={}",
                		new Object[]{(System.currentTimeMillis()-startTime)/1000, projectName,task.getName()});
            }
        }, new CronTrigger(task.getCron()));
        logger.info("add a new task {}", taskKey);
        futures.put(taskKey, future);
    }

    public FutureTask<CommandResult> runScript(final String script, final Project project) {
        FutureTask<CommandResult> _fuFutureTask = new FutureTask(new Callable() {

            @Override
            public CommandResult call() throws Exception {

                logger.debug("run mongo script = {}", script);
                MongoTemplate template = project.fetchMongoTemplate();
                com.mongodb.DB db =    template.getDb();
                CommandResult result = db.doEval(script, new BasicDBObject().append("nolock", true));
//                CommandResult result = project.fetchMongoTemplate().getDb().doEval(script, new BasicDBObject().append("nolock", true));
                logger.debug("mongo task response {}", result);
                return result;
            }
        });
        executor.submit(_fuFutureTask);
        return _fuFutureTask;
    }

    private String getTaskKey(String projectName, Task task) {
        return projectName + "_" + task.getName();
    }

    public void removeScheduled(String projectName, Task task) {
        String taskKey = getTaskKey(projectName, task);
        if (futures.containsKey(taskKey)) {
            logger.info("remove old task {}", taskKey);
            futures.get(taskKey).cancel(true);
        }

    }

    public void startTasks(Project project) {
        try {
            logger.info("start task of project {}", project.getName());
            for (final Task task : project.getTasks()) {
                logger.debug("schedule task {} ,cron ={}", task.getName(), task.getCron());
                scheduledTask(project, task);
            }
        } catch (Exception e) {
            logger.error("start task of  project fail name={} {}", project.getName(), e);
        }
    }

    public void setExecutor(ConcurrentTaskScheduler executor) {
        this.executor = executor;
    }
}

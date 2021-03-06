package com.monitor.util;

import com.google.common.collect.Lists;
import com.monitor.model.Project;
import com.monitor.model.Task;
import com.monitor.service.ProjectService;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * author: hui.zhang
 */
@SuppressWarnings("unchecked")
public class ProjectCreator {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ProjectCreator.class);

    private ProjectService projectService;
    private List<Task> initTasks = Lists.newArrayList();

    public void createSelf() {
        Project project = new Project();
        project.setAlias("monitor监控平台");
        project.setName("monitor_monitor");
        setMongoInfoByLog4j(project);
        logger.debug("try create a monitor project for monitor_monitor {}", project);
        if (projectService.findProject(project.getName()) == null) {
            for (Task task : initTasks)
                project.getTasks().add(renderTask(task, project));
            projectService.saveProject(project);
        } else {
            logger.debug("projectName={} has exist ,skip create ", project.getName());
        }
    }
    /**
     * 通过log4jProperties的配置来设置mongodb信息
     * @param project
     */
    private void setMongoInfoByLog4j(Project project) {
//        String logCollection = "monitor_monitor_log";
        String logCollection = ResourceUtil.getMessage("collect_log");
//        String mongoUri = "mongodb://monitordb:monitordb1234@199.78.1.32:27017/monitor_log_db";
        String mongoUri = ResourceUtil.getMessage("mongo_uri");

        project.setLogCollection(logCollection);
        project.setMongoUri(mongoUri);

       /* Enumeration appenders = Logger.getRootLogger().getAllAppenders();
        while (appenders.hasMoreElements()) {
            Object appender = appenders.nextElement();
            if (appender instanceof MongoDbAppender) {
            	MongoDbAppender ref = (MongoDbAppender) appender;
                project.setLogCollection(ref.getCollectionName());
                project.setMongoUri(String.format("mongodb://%s:%s/%s",
                		ref.getHostname(), ref.getPort(), ref.getDatabaseName()));
            }
            if (appender instanceof AsynMongoURILayoutAppender) {
            	AsynMongoURILayoutAppender ref = (AsynMongoURILayoutAppender) appender;
                project.setLogCollection(ref.getCollectionName());
                project.setMongoUri(ref.getMongoURI());
            }
        }*/
    }

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public void setInitTasks(List<Task> initTasks) {
        this.initTasks = initTasks;
    }

    public Task renderTask(Task task, Project project) {
        Task task1 = new Task();
        task1.setCron(task.getCron());
        task1.setName(task.getName());
        task1.setTimeout(task.getTimeout());
        String script = task.getScript();
        script = StringUtils.replace(script, "$project.logCollection", project.getLogCollection());
        script = StringUtils.replace(script, "$project.metricCollection", project.getMetricCollection());
        task1.setScript(script);
        return task1;
    }
}

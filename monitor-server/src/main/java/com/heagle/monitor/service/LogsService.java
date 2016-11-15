

package com.heagle.monitor.service;

import com.heagle.monitor.model.LogQuery;
import com.heagle.monitor.model.Project;
import com.mongodb.DBCursor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;

/**
 * 日志分析服务
 *
 * @author hui.zhang
 */
@Service
public class LogsService {
    private static Logger logger = LoggerFactory.getLogger(LogsService.class);

    @Resource
    ProjectService projectService;
    @Resource
    TaskService taskService;
    private int max = 100;


    public DBCursor findLogs(String projectName, LogQuery logQuery) throws ParseException {
        return findLogs(projectName, logQuery, max);
    }

    public DBCursor findLogs(String projectName, LogQuery logQuery, int max) throws ParseException {
        Project project = projectService.findProject(projectName);
        MongoTemplate template = project.fetchMongoTemplate();

        Query query = new BasicQuery(logQuery.toQuery());
        query.limit(max);

        query.sort().on("timestamp", Order.DESCENDING);
        logger.debug("find logs from {}  by query {} by sort {}", new Object[]{project.getLogCollection(), query.getQueryObject(), query.getSortObject()});
        DBCursor cursor = template.getCollection(project.getLogCollection()).find(query.getQueryObject()).sort(query.getSortObject()).limit(max);
        return cursor;
    }

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public void setMax(int max) {
        this.max = max;
    }

}

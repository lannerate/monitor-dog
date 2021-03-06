
package com.monitor.controller;

import com.monitor.model.Log;
import com.monitor.model.LogQuery;
import com.monitor.model.Project;
import com.monitor.service.LogsService;
import com.monitor.service.ProjectService;
import com.monitor.service.TaskService;
import com.mongodb.DBCursor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.*;

/**
 * @author hui.zhang, Hui
 */
@Controller
public class LogsAction {
    private static Logger logger = LoggerFactory.getLogger(LogsAction.class);

    private Executor executor = Executors.newFixedThreadPool(10);

/*    @Value("${flashdog.mongWaitSeconds}")
    private int mongWaitSeconds;*/
    //    @Value("${flashdog.mongWaitSeconds}")
    private int mongWaitSeconds=20;

    @Resource
    private ProjectService projectService;

    @Resource
    private TaskService taskService;

    @Resource
    private LogsService logsService;

    @RequestMapping(value = "/projects/{projectName}/logs", method = RequestMethod.GET)
    public String test(ModelMap map, @PathVariable String projectName) {
        Project project = projectService.findProject(projectName);
        map.put("project", project);
        return "logs/show";
    }

    @RequestMapping(value = "/projects/{projectName}/logs/download", method = RequestMethod.GET)
    public void download(final HttpServletResponse response, ModelMap map, @PathVariable String projectName, LogQuery logQuery) throws IOException, ParseException {
        Project project = projectService.findProject(projectName);

        final MongoConverter converter = project.fetchMongoTemplate().getConverter();
        final DBCursor cursor = logsService.findLogs(projectName, logQuery, 100000);
        response.setContentType("file/txt;charset=utf-8");
        response.addHeader("content-disposition", String.format("attachment; filename=%s.txt", java.net.URLEncoder.encode("logs", "UTF-8")));
        response.setStatus(HttpServletResponse.SC_OK);

        while (cursor.hasNext()) {
            Log log = converter.read(Log.class, cursor.next());

            response.getWriter().println(log.toString());

        }


    }

    @RequestMapping(value = "/projects/{projectName}/logs/more", method = RequestMethod.GET)
    public void console(final HttpServletResponse response, ModelMap map, @PathVariable String projectName, LogQuery logQuery) throws IOException, ParseException {
        Project project = projectService.findProject(projectName);
        map.put("project", project);
        final MongoConverter converter = project.fetchMongoTemplate().getConverter();
        final DBCursor cursor = logsService.findLogs(projectName, logQuery);
        final StringBuffer buf = new StringBuffer();
        @SuppressWarnings("unchecked")
        FutureTask<String> task = new FutureTask(new Callable<String>() {
            @Override
            public String call() throws Exception {
                long startTime = System.currentTimeMillis();
                //遍历游标，最长不能超过20秒
                logger.debug("游标遍历结果:");
                while (cursor.hasNext()) {
                    Log log = converter.read(Log.class, cursor.next());

                    buf.insert(0, log.toString() + "\n");
                    long current = System.currentTimeMillis();
                    if ((current - startTime) / 1000 >= mongWaitSeconds) break;
                }
                return buf.toString();
            }
        });
        executor.execute(task);
        try {
            task.get(mongWaitSeconds + 5, TimeUnit.SECONDS);
            cursor.close();
        } catch (Exception e) {
            logger.error("查询超时 ", e);
            task.cancel(true);
        }

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(buf.toString());
        response.getWriter().flush();

    }

}

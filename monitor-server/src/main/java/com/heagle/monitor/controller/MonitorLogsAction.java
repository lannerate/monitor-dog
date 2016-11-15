
package com.heagle.monitor.controller;

import com.heagle.monitor.model.*;
import com.heagle.monitor.service.LogsService;
import com.heagle.monitor.service.ProjectService;
import com.heagle.monitor.service.TaskService;
import com.heagle.monitor.service.ViewService;
import com.heagle.monitor.util.DateUtil;
import com.heagle.monitor.util.ResourceUtil;
import com.mongodb.DBCursor;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author hui.zhang, Steven.Zheng
 */
@Controller
public class MonitorLogsAction {
    private static Logger logger = LoggerFactory.getLogger(MonitorLogsAction.class);

    private Executor executor = Executors.newFixedThreadPool(10);

//    @Value("${flashdog.mongWaitSeconds}")
    private int mongWaitSeconds=20;

    @Resource
    private ProjectService projectService;

    @Resource
    private TaskService taskService;

    @Resource
    private LogsService logsService;

    @Resource
    private ViewService viewService;

    @RequestMapping(value ="/monitor/log", method = RequestMethod.GET)
    public String listLog(ModelMap map, HttpServletResponse response) throws IOException{

        return  "/monitor/log";
    }

    @RequestMapping(value = "/monitor/{projectName}/log", method = RequestMethod.GET)
    public String test(ModelMap map, @PathVariable String projectName) {
        Project project = projectService.findProject(projectName);
        map.put("project", project);
        return "log/show";
    }

    @RequestMapping(value = "/monitor/{projectName}/log/download", method = RequestMethod.GET)
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

    @RequestMapping(value = "/monitor/{projectName}/log/more", method = RequestMethod.GET)
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

    /**
     * 获得不同日志级别下的log size
     * @param response
     * @param map
     * @param projectName
     * @param logQuery
     * @return
     * @throws IOException
     * @throws ParseException
     */
    @RequestMapping(value = "/monitor/{projectName}/pie/render", method = RequestMethod.GET)
    public @ResponseBody
    Pie countLogLevel(final HttpServletResponse response, ModelMap map, @PathVariable String projectName, LogQuery logQuery) throws IOException, ParseException {
        Pie  pie = new Pie();
        List<CellValue> cellValues = new ArrayList<CellValue>();

        Project project = projectService.findProject(projectName);
//        map.put("project", project);
//      final MongoConverter converter = project.fetchMongoTemplate().getConverter();
        final DBCursor errorCursor = logsService.findLogs(projectName, logQuery,Integer.MAX_VALUE);
        logQuery.setLevel("");
        final DBCursor allCursor = logsService.findLogs(projectName, logQuery,Integer.MAX_VALUE);
        int errorSize = (errorCursor==null ? 0 : errorCursor.size());
        double allSize  =  allCursor==null ? 1 : allCursor.size();
        String errorRatio = ((errorSize*100)/allSize)+"";
        CellValue cellValue = new CellValue("今日错误",errorSize+"");
        cellValues.add(cellValue);
        pie.setNumerical(cellValues);
        List<CellValue> percents= new ArrayList<CellValue>();
        CellValue percent = new CellValue("今日错误占比",errorRatio.substring(0,errorRatio.indexOf(".")+3)+"%");
        percents.add(percent);
        pie.setPercent(percents);

        return pie;

    }


    /**
     * 加载jvm相关信息
     * @param map
     * @param projectName
      * @return
     */
    @RequestMapping(value = "/monitor/{projectName}/jvm/render", method = RequestMethod.GET)
    public  @ResponseBody
    Jvm renderJVM(ModelMap map, @PathVariable String projectName) {
        Project project = projectService.findProject(projectName);
        List<Chart> charts = project.getCharts();
        Jvm jvm = new Jvm();
        for(Chart chart:charts){
          List data = chart.findData();
          map.put("data", data);
        }

        return jvm;
    }


    @RequestMapping(value = "/monitor/{projectName}/{ip}/jvm/metrics/", method = RequestMethod.GET)
    public @ResponseBody
    Jvm renderMetric(@PathVariable String projectName ,@PathVariable String ip) {
        Project project = projectService.findProject(projectName);
        IndexEnum[] indexEnums = new IndexEnum[]{IndexEnum.memoryUsed,IndexEnum.cpuUsed,IndexEnum.diskUsedRatio
        ,IndexEnum.heapMemoryUsed,IndexEnum.noheapMemoryUsed,IndexEnum.permGen,IndexEnum.edenSpace,IndexEnum.survivorSpace
        ,IndexEnum.totalNetworkRx,IndexEnum.totalNetworkTx};
        Jvm jvm = new Jvm();
        List<CellValue> numericals = new ArrayList<CellValue>();
        List<CellValue> percents = new ArrayList<CellValue>();

        List<String> queryNames = new ArrayList<String>();
        for(IndexEnum indexEnum:indexEnums){
            queryNames.add(indexEnum.getValue());
        }
        int interval = Integer.valueOf(ResourceUtil.getMessage("jvm.interval")).intValue();
        List<MetricValue> _metricValueList = project.findMoreMetricData(queryNames,ResourceUtil.getMessage(ip),DateUtil.getBeforeSecondNow(interval));
        Map<String,MetricValue> map= new HashMap<String,MetricValue>();
        for(MetricValue metricValue :_metricValueList){
            if(!map.keySet().contains(metricValue.getName()))
                map.put(metricValue.getName(),metricValue);
        }

       for(Map.Entry<String,MetricValue> entity: map.entrySet()) {
           MetricValue metricValue=entity.getValue();
           if(metricValue!=null){
               String metricName =metricValue.getName();
               for(IndexEnum index:indexEnums){
                    if(metricName.equals(index.getValue())){
                        metricName = index.getName();
                    }
               }
               double value =  metricValue.getValue();
               if(metricValue.getName().equals(IndexEnum.diskUsedRatio.getValue())){
                   double t_value = metricValue.getValue();
                   value = (100.0 - t_value);
               }

               String valStr = String.valueOf(value);
               if(StringUtils.isNotEmpty(valStr) && valStr.indexOf(".")>0)
                   valStr = valStr.substring(0,valStr.indexOf(".")+2);

               if(metricValue.getName().equals(IndexEnum.cpuUsed.getValue()) ||
                       metricValue.getName().equals(IndexEnum.diskUsedRatio.getValue())){
                   CellValue numerical = new CellValue(metricName,valStr+"%");
                   percents.add(numerical);
               }else{
                   CellValue numerical = new CellValue(metricName,valStr);
                   numericals.add(numerical);
               }

           }
       }

       java.util.Collections.sort(numericals,new Comparator<CellValue>() {
           @Override
           public int compare(CellValue o1, CellValue o2) {
               return o2.getLabel().compareTo(o1.getLabel());
           }
       });

        jvm.setNumerical(numericals);
        jvm.setPercent(percents);
        return jvm;
    }

    /**
     * load thread Line Charts
     *
     * @return
     */
    @RequestMapping(value = "/monitor/{projectName}/{ip}/jvm/threads", method = RequestMethod.GET)
    public @ResponseBody
    LineChart renderLineCharts(@PathVariable String projectName ,@PathVariable String ip){

        LineChart chart = new LineChart();
        List<String> categories =  new ArrayList<String>();
        List<Long> series = new ArrayList<Long>();
        Project project = projectService.findProject(projectName);
        IndexEnum[] indexEnums = new IndexEnum[]{IndexEnum.threadCount};

        for(IndexEnum index :indexEnums){
            List<MetricValue> metricValues = project.findMetricData(index.getValue(),ResourceUtil.getMessage(ip),DateUtil.getTodayDay());
            for(MetricValue metricValue :metricValues){
                categories.add(DateUtil.formatDate2String(new Date(metricValue.getTimeStamp()), null));
                series.add((long)metricValue.getValue());
            }
        }
        chart.setCategories(categories);
        chart.setSeries(series);

        return chart;
    }


}


package com.heagle.monitor.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.heagle.monitor.util.ResourceUtil;
import com.mongodb.*;
import org.apache.commons.beanutils.BeanPropertyValueEqualsPredicate;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Order;
import org.springframework.data.mongodb.core.query.Query;

import java.util.*;

/**
 * 项目,包括4个方面
 * 1.图表
 * 2.告警
 * 3.任务
 * 4.度量因子
 *
 * @author hui.zhang
 */
@SuppressWarnings("unchecked")
@Document
public class Project {
    private static Logger logger = LoggerFactory.getLogger(Project.class);

    private final static Map<String, Mongo> MONGO_MAP = Maps.newHashMap();
    @Id
    private String name;
    private String alias;
    private String desc;

    /**
     * mongodb://[username:password@]host1[:port1][,host2[:port2],...[,hostN[:portN]]][/[database][?options]]
     */
    private String mongoUri;
    private List<Chart> charts = Lists.newArrayList();
    private List<Task> tasks = Lists.newArrayList();

    private List<MetricDog> metricDogs = Lists.newArrayList();
    private List<String> admins = Lists.newArrayList();
    private String metricCollection;
    private String logCollection;
    private TimeRange timeRange = TimeRange.lastDay();
    private String mailList;
    private Properties properties = new Properties();
    /**
     * 用于存储视图
     */
    private Map<String, String> views = new HashMap();
    private Status status=Status.FINE;

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getAlias() {
        if (alias == null)
            alias = name;
        return alias;
    }


    public void setAlias(String alias) {
        this.alias = alias;
    }


    public String getDesc() {
        return desc;
    }


    public void setDesc(String desc) {
        this.desc = desc;
    }


    public List<Task> getTasks() {
        return tasks;
    }


    public void setTasks(List tasks) {
        this.tasks = tasks;
    }


    public Task findTask(String taskName) {
        for (Task task : tasks) {
            if (taskName.equals(task.getName()))
                return task;
        }
        return null;
    }


    public void saveTask(Task task) {
        Task oldTask = findTask(task.getName());
        if (oldTask == null) {
            tasks.add(task);
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                if (StringUtils.equals(tasks.get(i).getName(), task.getName())) {
                    tasks.set(i, task);
                }
            }
        }


    }


    public Task removeTask(String taskName) {
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (StringUtils.equals(task.getName(), taskName)) {
                tasks.remove(i);
                return task;
            }
        }
        return null;
    }


    public Chart findChart(String chartName) {
        for (Chart chart : charts) {
            if (StringUtils.equals(chart.getName(), chartName))
                return chart;
        }
        return null;
    }


    public void saveChart(Chart chart) {
        this.charts.remove(chart);
        this.charts.add(chart);
    }

    public List getCharts() {
        return charts;
    }

    public void setCharts(List charts) {
        this.charts = charts;
    }


    public List<MetricDog> getMetricDogs() {
        return metricDogs;
    }

    public void setMetricDogs(List<MetricDog> metricDogs) {
        this.metricDogs = metricDogs;
    }

    public Map<String, String> getViews() {
        return views;
    }

    public void setViews(Map<String, String> views) {
        this.views = views;
    }

    public List<String> getAdmins() {
        return admins;
    }

    public void setAdmins(List<String> admins) {
        this.admins = admins;
    }

    public String getMetricCollection() {
        if (metricCollection == null)
            metricCollection = logCollection + "_metrics";
        return metricCollection;
    }

    public void setMetricCollection(String metricCollection) {
        this.metricCollection = metricCollection;
    }

    public TimeRange getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(TimeRange timeRange) {
        this.timeRange = timeRange;
    }

    public String getMongoUri() {
        return mongoUri;
    }

    public void setMongoUri(String mongoUri) {
        this.mongoUri = mongoUri;
    }

    public MongoTemplate fetchMongoTemplate() {

        try {
            Mongo mongo;
            if (MONGO_MAP.containsKey(mongoUri)) {
                mongo = MONGO_MAP.get(mongoUri);

            } else {
                mongo = new Mongo(new MongoURI(mongoUri));
                MONGO_MAP.put(mongoUri, mongo);

            }

            MongoURI uri = new MongoURI(mongoUri);
            return new MongoTemplate(new SimpleMongoDbFactory(mongo, uri.getDatabase(),
                    new UserCredentials(uri.getUsername(), parseChars(uri.getPassword()))));

        } catch (Exception e) {
            logger.error("mongo db error ,uri={}", mongoUri, e);
            return null;
        }

    }

    private static String parseChars(char[] chars) {
        return chars == null ? null : String.valueOf(chars);
    }

    public List<String> findMetricNames() {

        try {
            List list = fetchMongoTemplate().getCollection(metricCollection).distinct("name");
            logger.debug("project [{}] has  metrics  ={} ,mongo={}", new Object[]{name, list, mongoUri});
            return list;
        } catch (Exception e) {
            logger.error("load metrics fail projectName=" + name, e);
        }
        return Lists.newArrayList();
    }

    private Query fetchTimeQuery() {
        return new Query(Criteria.where(Constants.TIME_STAMP_FIELD_NAME).gt(timeRange.getStart().getTime()));

    }
    private Query fetchTimeQuery(Date date) {
        return new Query(Criteria.where(Constants.TIME_STAMP_FIELD_NAME).gte(date.getTime()));

    }


    public List<MetricValue> findMetricData(String metricName) {

        Query query = fetchTimeQuery();
        query.addCriteria(Criteria.where("name").is(metricName));
        query.sort().on(Constants.TIME_STAMP_FIELD_NAME, Order.ASCENDING);
        logger.debug("find metric value by {} ,mongo={}", query.getQueryObject(), mongoUri);
        List<MetricValue> metricValues =fetchMongoTemplate().find(query, MetricValue.class, metricCollection);
        return metricValues;
    }

    public List<MetricValue> findMetricData(String metricName,String ip) {

        Query query = fetchTimeQuery();
        query.addCriteria(Criteria.where("name").is(metricName));
        query.addCriteria(Criteria.where("ip").is(ip));
        query.sort().on(Constants.TIME_STAMP_FIELD_NAME, Order.ASCENDING);
        logger.debug("find metric value by {} ,mongo={}", query.getQueryObject(), mongoUri);
        List<MetricValue> metricValues =fetchMongoTemplate().find(query, MetricValue.class, metricCollection);
        return metricValues;
    }

    public List<MetricValue> findMetricData(String metricName,String ip,Date date) {
        Query query = fetchTimeQuery(date);
        query.addCriteria(Criteria.where("name").is(metricName));
        query.addCriteria(Criteria.where("ip").is(ip));
        query.sort().on(Constants.TIME_STAMP_FIELD_NAME, Order.ASCENDING);
        logger.debug("find metric value by {} ,mongo={}", query.getQueryObject(), mongoUri);
        List<MetricValue> metricValues =fetchMongoTemplate().find(query, MetricValue.class, metricCollection);
        return metricValues;
    }

    public MetricValue findOneMetricData(String metricName,String ip) {
        Query query = fetchTimeQuery();
        query.addCriteria(Criteria.where("name").is(metricName));
        query.addCriteria(Criteria.where("ip").is(ip));
        query.sort().on(Constants.TIME_STAMP_FIELD_NAME, Order.ASCENDING);
        logger.debug("find metric value by {} ,mongo={}", query.getQueryObject(), mongoUri);
        MetricValue metricValue =fetchMongoTemplate().findOne(query, MetricValue.class, metricCollection);
        return metricValue;
    }

    public MetricValue findOneMetricData(String metricName,String ip,Date date) {
        Query query = fetchTimeQuery(date);
        query.addCriteria(Criteria.where("name").is(metricName));
        query.addCriteria(Criteria.where("ip").is(ip));
        query.sort().on(Constants.TIME_STAMP_FIELD_NAME, Order.ASCENDING);
        logger.debug("find metric value by {} ,mongo={}", query.getQueryObject(), mongoUri);
        MetricValue metricValue =fetchMongoTemplate().findOne(query, MetricValue.class, metricCollection);
        return metricValue;
    }

    public List<MetricValue> findMoreMetricData(List<String> metricNames,String ip,Date date) {
        Query query = fetchTimeQuery(date);
        query.addCriteria(Criteria.where("name").in(metricNames));
//      query.addCriteria(Criteria.where("name").is(metricName));
        query.addCriteria(Criteria.where("ip").is(ip));
        query.sort().on(Constants.TIME_STAMP_FIELD_NAME, Order.ASCENDING);
        logger.debug("find metric value by {} ,mongo={}", query.getQueryObject(), mongoUri);
        List<MetricValue> metricValues =fetchMongoTemplate().find(query, MetricValue.class, metricCollection);
        return metricValues;
    }

    /**
     * 获取交易耗时前8名
     * @param date
     * @return
     */
    public List<TransValue> findTransData(Date date,int top) {
        final String metricCollection ="heagle_trans";
        Query query = fetchTimeQuery(date);
//        query.sort().on(Constants.TIME_STAMP_FIELD_NAME, Order.ASCENDING);
        query.addCriteria(Criteria.where("process_code").ne("null"));
        query.sort().on(Constants.TRANS_DURATION, Order.DESCENDING);
        query.limit(10 * top);
        logger.debug("find metric value by {} ,mongo={}", query.getQueryObject(), mongoUri);
        List<TransValue> transValues =fetchMongoTemplate().find(query, TransValue.class, metricCollection);
        List<TransValue> transValueList = new ArrayList<TransValue>(top);

        Set<String> transIds = new HashSet<String>();
        for(TransValue transValue:transValues){
            if(!transIds.contains(transValue.getTrans_jnls_no()) && transIds.size() < top){
                transIds.add(transValue.getTrans_jnls_no());
                transValueList.add(transValue);
            }
        }
        return transValueList;
    }

    /**
     * 获取交易量前8名
     * @return
     */
    public Map<String,Integer> findTopTransCnt() {
        final String metricCollection ="heagle_trans";
        /**
         * db.runCommand({
         aggregate:"heagle_trans",
         pipeline:[
         {$group:{_id:"$process_code",total:{$sum:"$trans_flag"}}},
         {$sort:{total:-1}},
         {$limit:10}
         ]
         });
         */

/*        Comparator comparator = new Comparator<Map.Entry<String,Integer>>() {
            public int compare(Map.Entry<String,Integer> o1, Map.Entry<String,Integer> o2) {
                int val = 0;
                if (o1.getValue().intValue() > o2.getValue().intValue())
                    val = 1;
                if (o1.getValue().intValue() < o2.getValue().intValue())
                    val = -1;

                return val;
            }
        };*/

        TreeMap<String,Integer> results = new TreeMap<String, Integer>();

        DBObject groupFields = new BasicDBObject("_id","$process_code");
        groupFields.put("topCnt",new BasicDBObject("$sum","$trans_flag"));
        DBObject group = new BasicDBObject("$group",groupFields);

        DBObject sort = new BasicDBObject("$sort",new BasicDBObject("topCnt",-1));
        DBObject limit = new BasicDBObject("$limit",9);

        List<DBObject> pipeline = Arrays.asList(group,sort,limit);
        AggregationOutput output = fetchMongoTemplate().getCollection(metricCollection).aggregate(pipeline);
        for (DBObject result : output.results()){
            String trans_type = (String)result.get("_id");
            Double topCnt = (Double)result.get("topCnt");
            if(null!=trans_type && !trans_type.equals("null")){
                results.put(trans_type,Integer.valueOf(topCnt.intValue()));
            }
        }
        return results;
    }

    /**
     * 获取当天交易趋势
     * @param date
     * @return
     */
    public List<TransTrendValue> findTransTrendData(Date date) {
        final String metricCollection ="heagle_trans_trend";
        Query query = fetchTimeQuery(date);
        query.sort().on(Constants.TIME_STAMP_FIELD_NAME, Order.ASCENDING);
        logger.debug("find metric value by {} ,mongo={}", query.getQueryObject(), mongoUri);
        List<TransTrendValue> transTrendValues =fetchMongoTemplate().find(query, TransTrendValue.class, metricCollection);
        return transTrendValues;
    }


    /**
     * 获取分组地区的在线总人数
     * @return
     */
    public Map<String,Integer> findTopOnlineCnt(int minutes) {
        final String metricCollection ="heagle_online";
        /**
         *
         db.runCommand({
         aggregate:"heagle_online",
         pipeline:[
         {$match:{timeStamp:{$gte:new Date(new Date() - 1*60*1000).getTime()}}},
         {$group:{_id:"$ip",topCnt:{$sum:"$online_flag"}}},
         {$sort:{topCnt:-1}}
         ]
         }
         );
   */
        Date now = new Date();
        Map<String,Integer> results = new TreeMap<String, Integer>();

        DBObject matchFiles = new BasicDBObject("timeStamp",new BasicDBObject("$gte",now.getTime() - minutes*60*1000));
        DBObject match = new BasicDBObject("$match",matchFiles);

        DBObject groupFields = new BasicDBObject("_id","$ip");
        groupFields.put("topCnt",new BasicDBObject("$sum","$online_flag"));
        DBObject group = new BasicDBObject("$group",groupFields);

        DBObject sort = new BasicDBObject("$sort",new BasicDBObject("topCnt",-1));
//        DBObject limit = new BasicDBObject("$limit",8);

        List<DBObject> pipeline = Arrays.asList(match,group,sort);
        AggregationOutput output = fetchMongoTemplate().getCollection(metricCollection).aggregate(pipeline);
        for (DBObject result : output.results()){
            String ip = (String)result.get("_id");
            Double topCnt = (Double)result.get("topCnt");
            if(null!=ip && !ip.equals("")){
                String ip_eara = ResourceUtil.getMessage(ip);
                results.put(ip_eara,Integer.valueOf(topCnt.intValue()));
            }
        }
        return results;
    }

    /**
     * 返回最新值
     *
     * @param metricName
     * @return
     */
    public MetricValue findLastMetric(String metricName) {
        Query query = BasicQuery.query(Criteria.where("name").is(metricName));
        query.sort().on(Constants.TIME_STAMP_FIELD_NAME, Order.DESCENDING);
        return fetchMongoTemplate().findOne(query, MetricValue.class, metricCollection);
    }


    public void saveDog(MetricDog metricDog) {
        removeDog(metricDog.getName());
        metricDogs.add(metricDog);
    }

    public MetricDog findDog(String dogName) {
        return (MetricDog) CollectionUtils.find(metricDogs, new BeanPropertyValueEqualsPredicate("name", dogName));

    }


    public void removeDog(String dogName) {
        MetricDog dog = findDog(dogName);
        if (dog != null) {
            metricDogs.remove(dog);
        }

    }

    public String getMailList() {
        return mailList;
    }

    public void setMailList(String mailList) {
        this.mailList = mailList;
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", alias='" + alias + '\'' +
                ", desc='" + desc + '\'' +
                ", mongoUri='" + mongoUri + '\'' +
                ", metricDogs=" + metricDogs +
                ", metricCollection='" + metricCollection + '\'' +
                ", timeRange=" + timeRange +
                ", mailList='" + mailList + '\'' +
                ", taskCount='" + tasks.size() + '\'' +
                '}';
    }

    public boolean hasMember(String userName) {
        return admins != null && admins.contains(userName);
    }

    public String getLogCollection() {
        return logCollection;
    }

    public void setLogCollection(String logCollection) {
        this.logCollection = logCollection;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}

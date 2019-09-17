
package com.monitor.controller;

import com.monitor.model.*;
import com.monitor.service.ProjectService;
import com.monitor.service.ViewService;
import com.monitor.util.DateUtil;
import com.monitor.util.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author hui.zhang
 *         <p/>
 *         项目转发器
 */
@SuppressWarnings("unchecked")
@Controller
public class MonitorTransAction {
    private static Logger logger = LoggerFactory.getLogger(MonitorTransAction.class);

    @Resource
    private ProjectService projectService;
    @Resource
    private ViewService viewService;

    private final String projectName = ResourceUtil.getMessage("project_name");

    @RequestMapping(value ="/monitor/index",method = RequestMethod.GET)
    public String index(ModelMap map, HttpServletResponse response) throws IOException {

        return "/monitor/index";
    }

    @RequestMapping(value = "/monitor/trans", method = RequestMethod.GET)
    public String listTrans(ModelMap map, HttpServletResponse response) throws IOException {

        return "/monitor/trans";
    }

    @RequestMapping(value ="/monitor/jvm", method = RequestMethod.GET)
    public String listJvm(ModelMap map, HttpServletResponse response) throws IOException{

        return  "/monitor/jvm";
    }


    /**
     * 获得交易总量 top8 (数据来源：Oracle )
     * @param map
     * @return
     */
/*    @RequestMapping(value = "/monitor/trans/render", method = RequestMethod.GET)
    public @ResponseBody
    Trans renderTransFromDB(ModelMap map){
        List<TopTranModel> topTransModels =  jngSysTransService.getTopTrans(8);

        List<CellValue> numerical = new ArrayList<CellValue>();
        for(TopTranModel model :topTransModels){
            CellValue cellValue = new CellValue(model.getProcessCode(),model.getTopCount().toString());
            numerical.add(cellValue);
        }
        Trans  trans = new Trans();
        trans.setNumerical(numerical);
        return trans;
    }*/


    /**
     * 获得交易总量 top8 (数据来源：Mongodb )
     * @return
     */
    @RequestMapping(value = "/monitor/trans/render", method = RequestMethod.GET)
    public @ResponseBody
    Trans renderTransFromMong(){
//        List<TopTranModel> topTransModels =  jngSysTransService.getTopTrans(8);
        Project project = projectService.findProject(projectName);
        Map<String,Integer>  results = project.findTopTransCnt();
        List<CellValue> numerical = new ArrayList<CellValue>();
        for(Map.Entry<String,Integer> entry :results.entrySet()){
            CellValue cellValue = new CellValue(entry.getKey(),entry.getValue().toString());
            numerical.add(cellValue);
        }

        Collections.sort(numerical,new Comparator<CellValue>() {
            @Override
            public int compare(CellValue o1, CellValue o2) {
                int val = 0;
                if(Integer.valueOf(o1.getStatus()) > Integer.valueOf(o2.getStatus())){
                    val = -1;
                }if (Integer.valueOf(o1.getStatus()) < Integer.valueOf(o2.getStatus())) {
                    val = 1;
                }if(o1.getStatus() == null || o2.getStatus() == null){
                    val = 0;
                }if(o1.getStatus()!=null && o2.getStatus() !=null && o1.getStatus().equals(o2.getStatus())){
                    val = 0;
                }
                return val;
            }
        });
        Trans  trans = new Trans();
        trans.setNumerical(numerical);
        return trans;
    }

    /**
     * 交易耗时前8名(数据来源：oracle)
     * @return
     */
/*    @RequestMapping(value = "/monitor/trans/top/duration/render", method = RequestMethod.GET)
    public @ResponseBody
    List<DurationTran> renderDurationTransFromDB(){
        List<TopDurationTranModel> topModels =  jngSysTransService.getTopDurationTrans(8);
        List<DurationTran> durationTrans =  new ArrayList<DurationTran>();
        Long maxDuration = 0L;
        for(TopDurationTranModel model : topModels){
            if(model.getTopLevel()==1L){
                maxDuration = model.getDuration();
                break;
            }
        }
        for(TopDurationTranModel model : topModels){
            DurationTran  tran = new  DurationTran(model.getTranNo(),model.getProcessCode(),model.getDuration(),model.getTopLevel(),maxDuration);
            durationTrans.add(tran);
        }
        return durationTrans;
    }*/


    /**
     * 交易耗时前8名(数据来源：mongodb)
     * @return
     */
    @RequestMapping(value = "/monitor/trans/top/duration/render", method = RequestMethod.GET)
    public @ResponseBody
    List<DurationTran> renderDurationTransFromMong(){
//        List<TopDurationTranModel> topModels =  jngSysTransService.getTopDurationTrans(8);
        List<DurationTran> durationTrans =  new ArrayList<DurationTran>();
        Project project = projectService.findProject(projectName);
        List<TransValue> transValues = project.findTransData(DateUtil.getTodayDay(),8);
        long topLevel = 0;
        for(TransValue model :transValues){
            topLevel ++;
            DurationTran  tran = new  DurationTran(model.getTrans_jnls_no(),model.getProcess_code(),model.getTrans_duration(),
                    Long.valueOf(topLevel),transValues.get(0).getTrans_duration());
            durationTrans.add(tran);
        }
        return durationTrans;
    }


    /**
     * 交易趋势（数据来源：oracle）
     * @param map
     * @return
     */
/*    @RequestMapping(value = "/monitor/linecharts/render", method = RequestMethod.GET)
    public @ResponseBody
    LineChart renderLineChartsFromDB(ModelMap map){
        JngSysTransStatModelCriteria criteria = new JngSysTransStatModelCriteria();
        criteria.setOrderByClause(" trans_stat_time asc ");
        criteria.createCriteria().andTransStatTimeIsNotNull()
                .andTransStatTimeGreaterThanOrEqualTo(DateUtil.getTodayDay());
        List<JngSysTransStatModel> jngSysTransStatModelList = jngSysTransService.queryJngSysTransStat(criteria);
        LineChart chart = new LineChart();
        List<String> categories =  Collections.synchronizedList(new LinkedList<String>());
             //[90.0, 50.9, 120.5, 140.5, 180.2, 160.5, 150.2,130.5, 120.3, 160.3, 130.9, 90.6]
        List<Long> series =  Collections.synchronizedList(new LinkedList<Long>());
        for(JngSysTransStatModel  model:jngSysTransStatModelList){
            categories.add(DateUtil.formatDate2String(model.getTransStatTime(), null));
            series.add(model.getTransStatCount());
        }
        chart.setCategories(categories);
        chart.setSeries(series);
        return chart;
    }*/

    /**
     * 交易趋势（数据来源：Mongodb）
     * @return
     */
    @RequestMapping(value = "/monitor/linecharts/render", method = RequestMethod.GET)
    public @ResponseBody
    LineChart renderLineChartsFromMong(){
        LineChart chart = new LineChart();
        List<String> categories =  new ArrayList<String>();
        List<Long> series = new ArrayList<Long>();
        Project project = projectService.findProject(projectName);

        List<TransTrendValue> transTrendValues = project.findTransTrendData(DateUtil.getTodayDay());
            for(TransTrendValue value :transTrendValues){
                categories.add(DateUtil.formatDate2String(new Date(value.getTimeStamp()), null));
                series.add(value.getTrans_stat_count());
         }

        chart.setCategories(categories);
        chart.setSeries(series);


        return chart;
    }



    /**
     * 获取各个地区在线人数  （数据来源：Oracle）
     * @return
     */
   /* @RequestMapping(value = "/monitor/rank/render", method = RequestMethod.GET)
    public @ResponseBody
    Rank renderRankFromDB(){

        Rank  rank = new Rank();
        List<RankValue> percents= new ArrayList<RankValue>();
        List<TopOnlineModel> topOnlineModels = jngSysOnlineService.getTopOnline(DateUtil.getTodayDay());
        long maxValue = 0L;
        for(TopOnlineModel model :topOnlineModels){
             if(model.getTopLevel()==1L){
                 maxValue = model.getTopCount();
             }
        }

        for(TopOnlineModel model :topOnlineModels){
            RankValue rankValue = new RankValue(model.getTopLevel()+"",model.getIpEarecn(),model.getTopCount(),maxValue ,model.getIpEare());
            percents.add(rankValue);
        }
        rank.setPercent(percents);

        return rank;
    }*/

    /**
     * 获取各个地区在线人数  （数据来源：Mongodb）
     * @return
     */
    @RequestMapping(value = "/monitor/rank/render", method = RequestMethod.GET)
    public @ResponseBody
    Rank renderRankFromMong(){
        Rank  rank = new Rank();
        List<RankValue> percents= new ArrayList<RankValue>();

        Project project = projectService.findProject(projectName);
        int interval = Integer.valueOf(ResourceUtil.getMessage("online.interval"));
        Map<String,Integer>  onlineValues = project.findTopOnlineCnt(interval);

        List<CellValue> numerical = new ArrayList<CellValue>();
        for(Map.Entry<String,Integer> entry :onlineValues.entrySet()){
            CellValue cellValue = new CellValue(entry.getKey(),entry.getValue().toString());
            numerical.add(cellValue);
        }

        //sort the result
        Collections.sort(numerical,new Comparator<CellValue>() {
            @Override
            public int compare(CellValue o1, CellValue o2) {
                int val = 0;
                if(Integer.valueOf(o1.getStatus()) > Integer.valueOf(o2.getStatus())){
                    val = -1;
                }if (Integer.valueOf(o1.getStatus()) < Integer.valueOf(o2.getStatus())) {
                    val = 1;
                }if(o1.getStatus() == null || o2.getStatus() == null){
                    val = 0;
                }if(o1.getStatus()!=null && o2.getStatus() !=null && o1.getStatus().equals(o2.getStatus())){
                    val = 0;
                }
                return val;
            }
        });
        long topLevel = 0;
        for(CellValue model :numerical){
            topLevel ++;
            RankValue rankValue = new RankValue(topLevel+"",ResourceUtil.getMessage(model.getLabel()),Long.valueOf(model.getStatus()),Long.valueOf(numerical.get(0).getStatus()),model.getLabel());
            percents.add(rankValue);
        }
        rank.setPercent(percents);
        return rank;
    }


    @RequestMapping(value = "/monitor/pie/render", method = RequestMethod.GET)
    public @ResponseBody
    Pie renderPie(ModelMap map){
        Pie  pie = new Pie();
        List<CellValue> cellValues = new ArrayList<CellValue>();
        CellValue cellValue = new CellValue("在线人数","2000");
        cellValues.add(cellValue);
        cellValue = new CellValue("今日错误","1000");
        cellValues.add(cellValue);
        cellValue = new CellValue("内存","5000M");
        cellValues.add(cellValue);
        cellValue = new CellValue("磁盘空间","50000M");
        cellValues.add(cellValue);
        pie.setNumerical(cellValues);

        List<CellValue> percents= new ArrayList<CellValue>();
        cellValue = new CellValue("内存使用率","80%");
        percents.add(cellValue);
        cellValue = new CellValue("网络宽带","90%");
        percents.add(cellValue);
        pie.setPercent(percents);

        return pie;
    }



}

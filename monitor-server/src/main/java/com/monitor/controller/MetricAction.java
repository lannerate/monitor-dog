
package com.monitor.controller;

import com.google.common.collect.Lists;
import com.monitor.model.MetricValue;
import com.monitor.model.Project;
import com.monitor.model.TimeRange;
import com.monitor.service.ProjectService;
import com.monitor.util.ChartUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author  hui.zhang,Hui
 */
@Controller
public class MetricAction {
    private static Logger logger = LoggerFactory.getLogger(MetricAction.class);

    @Resource
    private ProjectService projectService;

    @RequestMapping(value = "/projects/{projectName}/metrics", method = RequestMethod.GET)
    public @ResponseBody
    ModelMap renderVar(ModelMap map, @PathVariable String projectName, String metricName,String newChartTitle) {
    	Project project = projectService.findProject(projectName);
    	if(StringUtils.isNotEmpty(newChartTitle)){
	    		project.getViews().put(newChartTitle, metricName);
	    		projectService.saveProject(project);
    	}

        String[] metrics = metricName.split(",");

        List<List<MetricValue>>  metricLists=Lists.newArrayList();
        for(String name:metrics){
            metricLists.add(project.findMetricData(name));
        }

        map.put("data", ChartUtil.format(metricLists));
        return  map;
    }
    
    @RequestMapping(value = "/projects/{projectName}/metrics/destroy")
    public @ResponseBody
    ModelMap metricsDelete(ModelMap map, @PathVariable String projectName, String title) {
    	Project project = projectService.findProject(projectName);
    	if(StringUtils.isNotEmpty(title)){
    		if(project.getViews().containsKey(title)){
	    		project.getViews().remove(title);
	    		projectService.saveProject(project);
    		}
    	}
    	return  map;
    }



    @RequestMapping(value = "/projects/{projectName}/metrics/timeRange", method = RequestMethod.POST)
    public String save(@PathVariable String projectName, TimeRange timeRange) {
        Project project = projectService.findProject(projectName);

        project.setTimeRange(timeRange);

        projectService.saveProject(project);
        return "redirect:/projects/" + projectName;
    }

}

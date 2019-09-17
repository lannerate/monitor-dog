
package com.monitor.controller;

import com.monitor.model.Project;
import com.monitor.model.TimeRange;
import com.monitor.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
* @author hui.zhang
 *
 */
@Controller
public class TimeRangeAction {
    private static Logger logger = LoggerFactory.getLogger(TimeRangeAction.class);

    @Resource
    private ProjectService projectService;


    @RequestMapping(value = "/projects/{projectName}/timeRange", method = RequestMethod.POST)
    public String update(ModelMap map, @PathVariable String projectName,
                         TimeRange timeRange) {
        logger.debug("update  time range to {} of project {} ", timeRange, projectName);
        Project project = projectService.findProject(projectName);

        projectService.saveProject(project);
        return "redirect:/projects/" + projectName;
    }


}

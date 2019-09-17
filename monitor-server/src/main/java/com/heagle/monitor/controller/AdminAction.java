
package com.monitor.controller;

import com.google.common.collect.Lists;
import com.monitor.model.Project;
import com.monitor.model.View;
import com.monitor.security.User;
import com.monitor.security.UserManager;
import com.monitor.service.ProjectService;
import com.monitor.service.ViewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: hui.zhang
 */
@Controller
public class AdminAction {
    private static Logger logger = LoggerFactory.getLogger(AdminAction.class);
    @Resource
    private UserManager userManager;
    @Resource
    private ViewService viewService;
     @Resource
     private ProjectService projectService;
    @RequestMapping(value = "/admin/user/list", method = RequestMethod.GET)
    public String show(ModelMap map) {
        List<User> users = userManager.listUsers();

        map.put("users", users);
        return "user/list";
    }

    @RequestMapping(value = "/admin/user/", method = RequestMethod.POST)
    public String update(User user, ModelMap map) {
        userManager.monitorUser(user);
        map.put("user", user);
        return "redirect:/admin/user/list";
    }

    @RequestMapping(value = "/admin/views/", method = RequestMethod.POST)
    public String addView(View view, String projectName) {
        if (projectName != null)
            view.setProjectNames(Lists.newArrayList(projectName.split(",")));
        logger.debug("save view ={}",view);
        viewService.saveView(view);

        return "redirect:/projects";
    }

    @RequestMapping(value = "/admin/views/destroy")
    public String deleteView(String name, ModelMap map) {

        viewService.delete(name);

        return "redirect:/projects";
    }
    @RequestMapping(value = "/admin/views/edit")
    public String editView(String name, ModelMap map) {
        List<Project> projects = projectService.findProjects();
        View view = viewService.find(name);
        map.put("projects",projects);
        map.put("view", view);
        return "view/edit";
    }
    @RequestMapping(value = "/admin/views/new")
    public String newView(  ModelMap map) {
        List<Project> projects = projectService.findProjects();

        map.put("projects",projects);

        return "view/new";
    }
}


package com.heagle.monitor.controller;

import com.google.common.collect.Lists;
import com.heagle.monitor.model.Project;
import com.heagle.monitor.model.View;
import com.heagle.monitor.security.SimpleAuthz;
import com.heagle.monitor.service.AlertService;
import com.heagle.monitor.service.ProjectService;
import com.heagle.monitor.service.ViewService;
import com.heagle.monitor.util.SystemConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author hui.zhang
 *         <p/>
 *         项目转发器
 */
@SuppressWarnings("unchecked")
@Controller
public class ProjectAction {
    private static Logger logger = LoggerFactory.getLogger(ProjectAction.class);

    @Resource
    private ProjectService projectService;

    @Resource
    private SimpleAuthz simpleAuthz;

    @Resource
    private AlertService alertService;

    @Resource
    private ViewService viewService;

    @RequestMapping({"/index", "/"})
    public String index(ModelMap map, HttpServletResponse response) throws IOException {

//        return "redirect:/projects";
        return "redirect:/monitor/index";
    }

    /**
     * 查看项目列表
     *
     * @param map
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public String listProject(ModelMap map, HttpServletResponse response) throws IOException {
        List<Project> projects = projectService.findProjects();
        map.put("projects", projects);
        List<View> views = viewService.findAll();
        map.put("views",views);
        return "project/list";
//        return "redirect:/monitor/index";
    }

    /**
     * 进入创建项目页面
     *
     * @param map
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/projects/new", method = RequestMethod.GET)
    public String create(ModelMap map, HttpServletResponse response) throws IOException {

        return "project/new";
    }

    /**
     * 创建项目
     *
     * @param map
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/projects", method = RequestMethod.POST)
    public String save(ModelMap map, HttpServletResponse response, Project project, BindingResult bindingResult) throws IOException {

        String userName = simpleAuthz.getPrincipal();
        project.setAdmins(Lists.newArrayList(userName));

        project.setMetricCollection(project.getMetricCollection());
        try {
            projectService.create(project);
            return "redirect:/projects/" + project.getName();
        } catch (IllegalArgumentException e) {
            map.put("project", project);
            map.put("flashMsg", e.getMessage());
            return "project/new";
        }

    }

    /**
     * 查看项目
     *
     * @param map
     * @param name
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/projects/{name}", method = RequestMethod.GET)
    public String show(ModelMap map, @PathVariable String name) throws IOException {
        Project project = projectService.findProject(name);
        map.put("project", project);
        map.put("metricNames", project.findMetricNames());
        Map views = project.getViews();
        if (views.isEmpty()) {
            for (String metricName : project.findMetricNames())
                views.put(metricName, metricName);
        }
        map.put("views", project.getViews());
        return "project/show";
    }

    /**
     * 进入设置项目页面
     *
     * @param map
     * @param name
     * @return
     * @throws IOException
     */
    @RequestMapping("/projects/{name}/settings")
    public String edit(ModelMap map, @PathVariable String name) throws IOException {
        Project project = projectService.findProject(name);
        map.put("project", project);
        return "project/settings";
    }

    /**
     * 进入设置项目页面
     *
     * @param map
     * @param name
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/projects/{name}/settings/{module}", method = RequestMethod.GET)
    public String settings(ModelMap map, @PathVariable String name, @PathVariable String module) throws IOException {
        Project project = projectService.findProject(name);
        map.put("project", project);
        map.put("module", module);
        return "project/settings";
    }

    /**
     * 更新指定项目
     *
     * @param map
     * @param name
     * @return
     */
    @RequestMapping(value = "/projects/{name}/info", method = RequestMethod.POST)
    public String update(ModelMap map, @PathVariable String name, Project project) throws IOException {
        Project dbProject = projectService.findProject(name);
        dbProject.setMailList(project.getMailList());
        dbProject.setLogCollection(project.getLogCollection());
        dbProject.setMongoUri(project.getMongoUri());
        dbProject.setAlias(project.getAlias());
        dbProject.setMetricCollection(project.getMetricCollection());
        projectService.saveProject(dbProject);

        return "redirect:/projects/" + name + "/settings/info";
    }

    @RequestMapping(value = "/projects/{name}/members", method = RequestMethod.POST)
    public String update(ModelMap map, @PathVariable String name, String admins) throws IOException {
        Project dbProject = projectService.findProject(name);
        dbProject.setAdmins(Lists.newArrayList(admins.split(",")));

        projectService.saveProject(dbProject);

        return "redirect:/projects/" + name + "/settings/members";
    }

    @RequestMapping(value = "/projects/{name}/ext", method = RequestMethod.POST)
    public String updateExt(ModelMap map, @PathVariable String name, HttpServletRequest request) throws IOException {
        Project dbProject = projectService.findProject(name);
        Map<String, Object> extProperties = WebUtils.getParametersStartingWith(request, SystemConstants.CONFIG_PREFIX);
        logger.debug("update project ext properties {}", extProperties);
        dbProject.getProperties().putAll(extProperties);
        projectService.saveProject(dbProject);

        return "redirect:/projects/" + name + "/settings/ext";
    }

    @RequestMapping(value = "/projects/{projectName}/destroy")
    public String delete(@PathVariable String projectName) throws IOException {
        projectService.remove(projectName);

        return "redirect:/projects";

    }

}

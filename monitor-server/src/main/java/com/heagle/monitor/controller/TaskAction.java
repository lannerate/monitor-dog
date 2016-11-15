
package com.heagle.monitor.controller;

import com.google.gson.Gson;
import com.heagle.monitor.model.Project;
import com.heagle.monitor.model.Task;
import com.heagle.monitor.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.io.IOException;

/**
* @author hui.zhang
 *
 */
@Controller
public class TaskAction {
    private static Logger logger = LoggerFactory.getLogger(TaskAction.class);

    @Resource
    private ProjectService projectService;


    /**
     * 显示任务列表
     *
     * @param map
     * @param projectName
     * @return
     */
    @RequestMapping(value = "/projects/{projectName}/tasks", method = RequestMethod.GET)
    public String show(ModelMap map, @PathVariable String projectName) throws IOException {
        Project project = projectService.findProject(projectName);

        map.put("project", project);
        map.put("tasks", project.getTasks());
        return "task/list";
    }

    /**
     * 创建任务
     *
     * @param map
     * @param projectName
     * @return
     */
    @RequestMapping(value = "/projects/{projectName}/tasks/new", method = RequestMethod.POST)
    public String create(ModelMap map, @PathVariable String projectName, String taskName) {

        Project project = projectService.findProject(projectName);
        Task projectTask = project.findTask(taskName);
        if (projectTask == null) {
            Task task = new Task();
            task.setName(taskName);
            project.saveTask(task);
            projectService.saveProject(project);
        }
        return String.format("redirect:/projects/%s/tasks/%s", projectName, taskName);

    }

    /**
     * 编辑任务
     *
     * @param map
     * @param projectName
     * @param taskName
     * @return
     */
    @RequestMapping(value = "/projects/{projectName}/tasks/{taskName}", method = RequestMethod.GET)
    public String edit(ModelMap map, @PathVariable String projectName, @PathVariable String taskName) throws IOException {
        Project project = projectService.findProject(projectName);
        Task task = project.findTask(taskName);
        map.put("project", project);
        map.put("task", task);
        return "task/edit";
    }

    /**
     * 更新任务
     *
     * @param map
     * @param projectName
     * @param task
     * @return
     */
    @RequestMapping(value = "/projects/{projectName}/tasks", method = RequestMethod.POST)
    public String update(ModelMap map, @PathVariable String projectName, Task task) {
        logger.debug("update task {}", new Gson().toJson(task));
        projectService.saveTask(projectName, task);

        return String.format("redirect:/projects/%s/tasks", projectName);
    }

    @RequestMapping(value = "/projects/{projectName}/tasks/{taskName}/destroy")
    public String delete(ModelMap map, @PathVariable String projectName, @PathVariable String taskName) throws IOException {
        projectService.removeTask(projectName, taskName);

        return String.format("redirect:/projects/%s/tasks", projectName);

    }
}

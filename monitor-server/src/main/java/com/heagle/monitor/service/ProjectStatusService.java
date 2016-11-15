package com.heagle.monitor.service;

import com.heagle.monitor.model.Alert;
import com.heagle.monitor.model.Project;
import com.heagle.monitor.model.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @author hui.zhang
 */
public class ProjectStatusService implements AlertListener {
    private static Logger logger = LoggerFactory.getLogger(ProjectStatusService.class);
    @Resource
    private ProjectService projectService;

    @Override
    public void notify(Alert alert) {
        Project project = projectService.findProject(alert.getProjectName());
        Status status = Status.valueOf(alert.getLevel());
        if (project.getStatus().compareTo(status) > 0) {
            project.setStatus(status);
            logger.debug("change project status to {}", status);
            projectService.saveProject(project);
        }
    }

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }
}

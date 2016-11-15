
package com.heagle.monitor.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
* @author hui.zhang
 *
 * 告警
 */
@Document
public class Alert {
    @Id
    private String id;

    private String content, title;

    private String projectName;

    private Date createTime = new Date();

    private String ip;
    
    private  MetricDog metricDog;
    

    /**
     * @see  Status
     */
    private String level="WARN";

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    

    public MetricDog getMetricDog() {
		return metricDog;
	}

	public void setMetricDog(MetricDog metricDog) {
		this.metricDog = metricDog;
	}

	@Override
    public String toString() {
        return "Alert{" +
                "content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", ip='" + ip + '\'' +
                ", level='" + level + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}

package com.heagle.monitor.service;

import com.google.gson.Gson;
import com.heagle.monitor.model.View;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicUpdate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author hui.zhang
 */
public class ViewService {
    public static final String SYSTEM_CONFIG_NAME = "system_config";
    @Resource
    private MongoTemplate mongoTemplate;
    private String collectionName = "heagle_monitor_projects";
//    private String collectionName = "heagle_monitor_projects";

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void saveView(View view) {
        mongoTemplate.upsert(new Query(Criteria.where("name").is(view.getName())),
                new BasicUpdate(new Gson().toJson(view)), collectionName);
    }

    public List<View> findAll() {
        return mongoTemplate.findAll(View.class, collectionName);
    }

    public View find(String name) {
        return mongoTemplate.findOne(new Query(Criteria.where("name").is(name)), View.class,collectionName);
    }

    public void delete(String name) {
        mongoTemplate.remove(new Query(Criteria.where("name").is(name)),collectionName);
    }
}

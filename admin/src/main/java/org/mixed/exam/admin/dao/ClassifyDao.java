package org.mixed.exam.admin.dao;


import org.mixed.exam.admin.pojo.Classification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClassifyDao {
    @Autowired
    private MongoTemplate mongoTemplate;
    //添加一个分类
    public void saveClassify(Classification classification){
        mongoTemplate.save(classification,"classifications");
    }
    //查找所有的课程信息
    public List<Classification> findAllCourse(){
        Query query = new Query(Criteria.where("superClassId").is(""));
        List<Classification> classifications = mongoTemplate.find(query,Classification.class,"classifications");
        return classifications;
    }
    //查找所有的课程知识点信息
    public List<Classification> findAllKnowledge(){
        Query query = new Query(Criteria.where("superClassId").ne(""));
        List<Classification> classifications = mongoTemplate.find(query,Classification.class,"classifications");
        return classifications;
    }
}

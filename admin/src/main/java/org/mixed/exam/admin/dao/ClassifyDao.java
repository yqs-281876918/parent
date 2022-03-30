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
    private static String DOCUMENT_NAME="classifications";
    @Autowired
    private MongoTemplate mongoTemplate;
    //添加一个分类
    public void saveClassify(Classification classification){
        mongoTemplate.save(classification,DOCUMENT_NAME);
    }
    //查找所有的课程信息
    public List<Classification> findAllCourse(){
        Query query = new Query(Criteria.where("superClassId").is(""));
        return mongoTemplate.find(query,Classification.class,DOCUMENT_NAME);
    }
    //查找所有的课程知识点信息
    public List<Classification> findAllKnowledge(){
        Query query = new Query(Criteria.where("superClassId").ne(""));
        return mongoTemplate.find(query,Classification.class,DOCUMENT_NAME);
    }
    public List<Classification> findKnowledge(String courseID)
    {
        Query query = new Query(Criteria.where("superClassId").is(courseID));
        return mongoTemplate.find(query,Classification.class,DOCUMENT_NAME);
    }
}

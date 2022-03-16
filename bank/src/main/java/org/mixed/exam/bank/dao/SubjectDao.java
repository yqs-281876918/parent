package org.mixed.exam.bank.dao;

import org.mixed.exam.bank.pojo.po.Question;
import org.mixed.exam.bank.util.SubjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SubjectDao
{
    private static String DOCUMENT_NAME="subjects";
    @Autowired
    private MongoTemplate mongoTemplate;
    //存储一个题目
    public void saveSubject(Question question)
    {
        mongoTemplate.save(question,DOCUMENT_NAME);
    }
    //查询某一类型的所有题目
    public List<? extends Question> getSubjectsByType(String type)
    {
        Query query = Query.query(Criteria.where("type").is(type));
        return mongoTemplate.find(query, SubjectUtil.getClassByType(type),DOCUMENT_NAME);
    }
    //根据id查题目
    public <T extends Question> T getSubjectsByID(String id,Class<T> questionClass)
    {
        return mongoTemplate.findById(id,questionClass,DOCUMENT_NAME);
    }
    public Question getSubjectsByID(String id,String type)
    {
        return mongoTemplate.findById(id,SubjectUtil.getClassByType(type),DOCUMENT_NAME);
    }
    public List<Question> getAllSubject()
    {
        return mongoTemplate.findAll(Question.class,DOCUMENT_NAME);
    }
    /*
    public List<SingleChoiceQuestion> getAllSingleChoiceQuestion()
    {
        return mongoTemplate.findAll(SingleChoiceQuestion.class);
    }
     */
    public int pass(String id,String type){
        Query query = Query.query(Criteria.where("id").is(id));
        Update update = Update.update("isExamined",true);
        mongoTemplate.updateFirst(query, update, SubjectUtil.getClassByType(type),DOCUMENT_NAME);
        return 1;
    }

    //根据题型类型和学科类型查询题目数量
    public Integer getCountByType(String type,String courseID)
    {
        Query query = Query.query(Criteria.where("courseID").is(courseID).and("type").is(type));
        return (int)mongoTemplate.count(query,DOCUMENT_NAME);
    }
}

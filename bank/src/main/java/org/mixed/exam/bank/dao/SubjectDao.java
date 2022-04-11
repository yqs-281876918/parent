package org.mixed.exam.bank.dao;

import lombok.Data;
import org.checkerframework.checker.units.qual.C;
import org.mixed.exam.admin.api.pojo.Classification;
import org.mixed.exam.bank.pojo.dto.SubjectJson;
import org.mixed.exam.bank.pojo.po.Question;
import org.mixed.exam.bank.pojo.vo.SubjectItem;
import org.mixed.exam.bank.util.HttpUtil;
import org.mixed.exam.bank.util.SubjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SubjectDao
{
    private static String DOCUMENT_NAME="subjects";
    @Autowired
    private MongoTemplate mongoTemplate;
    public String getSubjectByID(String id)
    {
        SubjectType type = mongoTemplate.findById(id,SubjectType.class,DOCUMENT_NAME);
        if(type==null)
        {
            return "{}";
        }
        Question q = mongoTemplate.findById(id,SubjectUtil.getClassByType(type.getType()),DOCUMENT_NAME);
        SubjectJson sj=SubjectUtil.subject2Json(q);
        return sj.getJson();
    }
    @Data
    private class SubjectType
    {
        private String type;
    }
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

    //根据课程id取课程
    public Classification getCourse(String id) {
        return mongoTemplate.findById(id,Classification.class,"classifications");
    }

    //根据课程名取课程
    public String getCourseByName(String course) {
        Query query = Query.query(Criteria.where("classifyName").is(course));
        return mongoTemplate.find(query,Classification.class,"classifications").get(0).getId();
    }

    //根据题型类型和学科查询所有题目基本信息
    public List<Question> getQuestionByType(String course,String type){
        String courseID=getCourseByName(course);
        Query query = Query.query(Criteria.where("courseID").is(courseID).and("type").is(type));
        return mongoTemplate.find(query,Question.class,"subjects");
    }

    public List<Question> getSubjects(String type, Boolean open, Boolean isExamined, Integer difficulty,
                                     String courseID, String class2ndID, String creator)
    {
        Criteria criteria = new Criteria();
        if(type!=null)
        {
            criteria.and("type").is(type);
        }
        if(open!=null)
        {
            criteria.and("open").is(open);
        }
        if(isExamined!=null)
        {
            criteria.and("isExamined!").is(isExamined);
        }
        if(difficulty!=null)
        {
            criteria.and("difficulty").is(difficulty);
        }
        if(courseID!=null)
        {
            criteria.and("courseID").is(courseID);
        }
        if(class2ndID!=null)
        {
            criteria.and("class2ndID").is(class2ndID);
        }
        if(creator!=null)
        {
            criteria.and("creator").is(creator);
        }
        Query query = Query.query(criteria);
        return mongoTemplate.find(query,Question.class,DOCUMENT_NAME);
    }

    public List<SubjectItem> getUnVerifySubjectItems(){
        Query query = Query.query(Criteria.where("isExamined").is(false));
        List<Question> questions =  mongoTemplate.find(query,Question.class,DOCUMENT_NAME);
        List<SubjectItem> items= new ArrayList<>();
        for(Question q : questions)
        {
            items.add(new SubjectItem(q));
        }
        return items;
    }

    public void deleteSubject(String id){
        Query query=new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query,Question.class,DOCUMENT_NAME);
    }
}

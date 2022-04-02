package org.mixed.exam.teacher.dao;


import org.mixed.exam.bank.api.pojo.po.exam.ExamDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.domain.Sort.by;


@Repository
public class AnalyseDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    //算出最高、最低、平均分、参加考试人数
    //总人数
    public List<ExamDetail> getAll(Integer examId){
        Query query = new Query(Criteria.where("examId").is(examId));
        query.with(Sort.by(Sort.Direction.DESC,"totalScore"));
        return mongoTemplate.find(query,ExamDetail.class,"examDetail");
    }

    //最高分
    public List<ExamDetail> max(Integer examId){
        Query query = new Query(Criteria.where("examId").is(examId));
        query.with(Sort.by(Sort.Direction.DESC,"totalScore"));
        return mongoTemplate.find(query,ExamDetail.class,"examDetail");
    }
    public List<ExamDetail> min(Integer examId){
        Query query = new Query(Criteria.where("examId").is(examId));
        query.with(Sort.by(Sort.Direction.ASC,"totalScore"));
        return mongoTemplate.find(query,ExamDetail.class,"examDetail");
    }
    //找出某场考试的全部学生
    public List<ExamDetail> getStuList(Integer examId){
        Query query = Query.query(Criteria.where("examId").is(examId));
        return mongoTemplate.find(query,ExamDetail.class,"examDetail");
    }
}

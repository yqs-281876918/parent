package org.mixed.exam.student.dao;

import org.mixed.exam.bank.api.pojo.po.exam.ExamDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ExamDetailDao {

    @Autowired
    private MongoTemplate mongoTemplate;
    //存储学生的答题卡
    public void saveExamDetail(ExamDetail examDetail){
        mongoTemplate.save(examDetail,"examDetail");
    }
    //查询该学生是否完成考试
    public List<ExamDetail> selectExamDetail(String username, Integer examId){
        Query query = new Query(Criteria.where("username").is(username).and("examId").is(examId));
        return mongoTemplate.find(query,ExamDetail.class,"examDetail");
    }
}

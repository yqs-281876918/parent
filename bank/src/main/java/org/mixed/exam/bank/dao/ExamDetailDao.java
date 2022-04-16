package org.mixed.exam.bank.dao;


import org.mixed.exam.bank.pojo.po.exam.ExamDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ExamDetailDao
{
    @Autowired
    private MongoTemplate mongoTemplate;
    //存储学生的答题卡
    public void addExamDetail(ExamDetail examDetail){
        mongoTemplate.insert(examDetail,"examDetail");
    }
}

package org.mixed.exam.student.dao;

import org.mixed.exam.bank.api.pojo.po.exam.ExamDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ExamDetailDao {

    @Autowired
    private MongoTemplate mongoTemplate;
    //存储学生的答题卡
    public void saveExamDetail(ExamDetail examDetail){
        mongoTemplate.save(examDetail,"examDetail");
    }
}

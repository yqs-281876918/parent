package org.mixed.exam.student.dao;

import org.mixed.exam.bank.api.pojo.po.exam.ExamDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

public class paperRetrospectDao {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //根据examdetial id查询是否完成批阅
    public int isFinished (String id){
        ExamDetail e=mongoTemplate.findById("id", ExamDetail.class,"examDetail");
        return e.getFinishReview();
    }
    //通过examdetial id查询all
    public ExamDetail getById (String id){
        ExamDetail e=mongoTemplate.findById("id", ExamDetail.class,"examDetail");
        return e;
    }


}

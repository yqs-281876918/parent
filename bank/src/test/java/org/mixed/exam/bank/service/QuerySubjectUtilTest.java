package org.mixed.exam.bank.service;

import org.junit.jupiter.api.Test;
import org.mixed.exam.bank.pojo.po.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest
public class QuerySubjectUtilTest {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private QuerySubjectService querySubjectService;
    @Test
    public void queryByID()
    {
        System.out.println(mongoTemplate.findAll(Question.class,"subjects"));
    }
}
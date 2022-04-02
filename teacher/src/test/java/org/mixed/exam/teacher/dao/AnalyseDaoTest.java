package org.mixed.exam.teacher.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AnalyseDaoTest {

    @Autowired
    private AnalyseDao analyseDao;
    @Test
    void getStuList() {
        System.out.println(analyseDao.getStuList(13));
    }
}
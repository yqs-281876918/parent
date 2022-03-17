package org.mixed.exam.bank.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SubjectDaoTest {

    @Autowired
    private SubjectDao subjectDao;
    @Test
    void saveSubject()
    {
    }
    @Test
    public void getSubjects()
    {
        System.out.println(subjectDao.getSubjects("singleChoiceQuestion",true,null,null,null,null,"yqs"));
    }
}
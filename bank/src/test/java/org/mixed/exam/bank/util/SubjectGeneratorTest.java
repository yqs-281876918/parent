package org.mixed.exam.bank.util;

import org.junit.jupiter.api.Test;
import org.mixed.exam.bank.dao.SubjectDao;
import org.mixed.exam.bank.pojo.po.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class SubjectGeneratorTest
{
    @Autowired
    private SubjectDao subjectDao;
    @Test
    public void main()
    {
        generate();
    }
    private void generate()
    {
//        for(int i=0;i<15;i++)
//        {
//            subjectDao.saveSubject(SubjectGenerator.CHINESE_WRONG_CHARACTER.randomSCQ());
//        }
        for(int i=0;i<15;i++)
        {
            subjectDao.saveSubject(SubjectGenerator.CHINESE_READ_COMPREHENSION.randomRC());
        }
    }
    private void renderBaseInfo(Question q)
    {

    }
}
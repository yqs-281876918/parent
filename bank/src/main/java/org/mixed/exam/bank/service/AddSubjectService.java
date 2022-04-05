package org.mixed.exam.bank.service;

import org.mixed.exam.admin.api.client.ClassifyClient;
import org.mixed.exam.admin.api.pojo.Classification;
import org.mixed.exam.bank.dao.SubjectDao;
import org.mixed.exam.bank.pojo.po.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddSubjectService
{
    @Autowired
    SubjectDao subjectDao;
    @Autowired
    private ClassifyClient classifyClient;
    public void insertQuestion(Question q)
    {
        subjectDao.saveSubject(q);
    }
    //课程相关
    //查找所有课程
    public List<Classification> findAllCourse(){
        return classifyClient.findAllCourse();
    }
    //查找所有课程相关知识点
    public List<Classification> findAllKnowledge(){
        return classifyClient.findAllKnowledge();
    }
}

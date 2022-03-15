package org.mixed.exam.admin.service;


import org.mixed.exam.admin.dao.ClassifyDao;
import org.mixed.exam.admin.pojo.Classification;
import org.mixed.exam.bank.api.client.SubjectClient;
import org.mixed.exam.bank.api.pojo.vo.SubjectItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassifyService {
    @Autowired
    private ClassifyDao classifyDao;
    @Autowired
    private SubjectClient subjectClient;
    //添加课程或者课程知识点
    public void insertClassify(Classification classification){
        classifyDao.saveClassify(classification);
    }
    //查找所有课程信息
    public List<Classification> findAllCourse(){
        return classifyDao.findAllCourse();
    }
    //查找所有课程知识点信息
    public List<Classification> findAllKnowledge(){
        return classifyDao.findAllKnowledge();
    }
    //查找所有题目信息
    public List<SubjectItem> findAllSubject(){
        return subjectClient.items();
    }
}

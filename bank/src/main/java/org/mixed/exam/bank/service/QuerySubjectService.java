package org.mixed.exam.bank.service;

import org.mixed.exam.bank.dao.SubjectDao;
import org.mixed.exam.bank.pojo.po.Question;
import org.mixed.exam.bank.pojo.vo.SubjectItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuerySubjectService
{
    @Autowired
    private SubjectDao subjectDao;
    public <T extends Question> T queryByID(String id, Class<T> classType)
    {
        return subjectDao.getSubjectsByID(id,classType);
    }
    public List<SubjectItem> getSubjectItems()
    {
        List<Question> questions = subjectDao.getAllSubject();
        List<SubjectItem> subjectItems=new ArrayList<>();
        for(Question q : questions)
        {
            subjectItems.add(new SubjectItem(q));
        }
        return subjectItems;
    }
    public List<? extends Question> getSubjectsByType(String type)
    {
        return subjectDao.getSubjectsByType(type);
    }
    public List<Question> getAllSubject()
    {
        return subjectDao.getAllSubject();
    }
}

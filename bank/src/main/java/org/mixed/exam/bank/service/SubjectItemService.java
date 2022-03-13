package org.mixed.exam.bank.service;

import org.mixed.exam.bank.dao.SubjectDao;
import org.mixed.exam.bank.pojo.po.Question;
import org.mixed.exam.bank.pojo.vo.SubjectItem;
import org.mixed.exam.bank.util.SubjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectItemService
{
    @Autowired
    private SubjectDao subjectDao;
    public List<SubjectItem> getItems()
    {
        List<SubjectItem> items=new ArrayList<>();
        List<String> typeList = SubjectUtil.getTypeList();
        for(String type : typeList)
        {
            List<? extends Question> list = subjectDao.getSubjectsByType(type);
            for(Question q : list)
            {
                items.add(new SubjectItem(q));
            }
        }
        return items;
    }
}

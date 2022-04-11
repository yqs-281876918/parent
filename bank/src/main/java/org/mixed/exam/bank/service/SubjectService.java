package org.mixed.exam.bank.service;

import org.mixed.exam.bank.dao.SubjectDao;
import org.mixed.exam.bank.util.SubjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectService
{
    @Autowired
    private SubjectDao subjectDao;
    public String getSubjectByID(@RequestParam("id") String id)
    {
        return subjectDao.getSubjectByID(id);
    }
    public List<String> getTypeList(@RequestParam("language")String language)
    {
        if(language.equals("chn"))
        {
            return SubjectUtil.getTypeListCHN();
        }
        else if(language.equals("en"))
        {
            return SubjectUtil.getTypeList();
        }
        return new ArrayList<>();
    }
}

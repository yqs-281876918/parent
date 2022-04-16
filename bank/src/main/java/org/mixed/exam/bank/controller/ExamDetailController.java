package org.mixed.exam.bank.controller;

import org.mixed.exam.bank.dao.ExamDetailDao;
import org.mixed.exam.bank.pojo.po.exam.ExamDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ExamDetailController
{
    @Autowired
    private ExamDetailDao examDetailDao;
    @ResponseBody
    @PostMapping("/test/exam-detail/save")
    public void addExamDetail(ExamDetail examDetail){
        examDetailDao.addExamDetail(examDetail);
    }
}

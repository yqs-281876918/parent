package org.mixed.exam.bank.controller;

import org.mixed.exam.bank.dao.ExamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@PreAuthorize("hasAnyRole('ROLE_adm','ROLE_tea1','ROLE_tea2')")
public class ExamController
{
    @Autowired
    private ExamMapper examMapper;
    @ResponseBody
    @PostMapping("/exam/submit")
    public void submit(String startTime, Integer testTime, Integer lateTime, Integer submitTime,
                       String paperID,Integer classID,String antiSettings)
    {
        examMapper.addExam(startTime,testTime,lateTime,submitTime,paperID,classID,antiSettings);
    }
}

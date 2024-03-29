package org.mixed.exam.bank.controller;

import org.mixed.exam.bank.dao.ExamMapper;
import org.mixed.exam.bank.pojo.po.Exam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@PreAuthorize("hasAnyRole('ROLE_adm','ROLE_tea1','ROLE_tea2','ROLE_stu')")
public class ExamController
{
    @Autowired
    private ExamMapper examMapper;
    @ResponseBody
    @PostMapping("/exam/submit")
    public void submit(Long startTime, Integer testTime, Integer lateTime, Integer submitTime,
                       String paperID,Integer classID,String antiSettings,
                       String examName,String introduce,String score,Integer totalScore)
    {
        examMapper.addExam(startTime,testTime,lateTime,submitTime,paperID,classID,antiSettings,examName,introduce,score,totalScore);
    }
    @ResponseBody
    @GetMapping("/exam/list")
    public List<Exam> list(String userName)
    {
        return examMapper.list(userName);
    }
    @ResponseBody
    @GetMapping("/exam")
    public Exam getByID(@RequestParam("id")Integer id)
    {
        return examMapper.getByID(id);
    }
}
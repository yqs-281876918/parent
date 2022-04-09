package org.mixed.exam.student.controller;

import org.mixed.exam.bank.api.client.ExamClient;
import org.mixed.exam.bank.api.pojo.po.Exam;
import org.mixed.exam.student.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
@RestController
public class ExamController {
    @Autowired
    private ExamService examService;
    @Autowired
    private ExamClient examClient;
    //根据班级号得到我的考试列表
    @RequestMapping("/test/examList")
    public List<Exam> getExamByCno(long classID){
        return examService.getExamByCno(classID);
    }
    //判断该学生是否能进入测试
    @RequestMapping("/test/goExamDetail")
    public String goExamDetail(Integer id){
        Exam exam = examClient.getByID(id);
        Date date = new Date();
        long dateTime =date.getTime();
        if (dateTime<exam.getStartTime()){
            return "未到时间";
        }
        if (dateTime>=exam.getStartTime() && dateTime<=exam.getStartTime()+exam.getLateTime()*60000){
            System.out.println(111);
            return "可以进入";
        }
        System.out.println(111);
        return "已超时";
    }
}

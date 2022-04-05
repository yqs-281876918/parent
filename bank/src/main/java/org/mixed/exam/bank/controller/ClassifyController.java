package org.mixed.exam.bank.controller;

import org.mixed.exam.admin.api.pojo.Classification;
import org.mixed.exam.bank.service.AddSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ClassifyController
{
    @Autowired
    private AddSubjectService addSubjectService;
    //查找所有课程信息
    @ResponseBody
    @GetMapping("classify/findAllCourse")
    public List<Classification> findAllCourse() {
        return addSubjectService.findAllCourse();
    }

    //查找所有课程知识点信息
    @ResponseBody
    @GetMapping("classify/findAllKnowledge")
    public List<Classification> findAllKnowledge() {
        return addSubjectService.findAllKnowledge();
    }
}

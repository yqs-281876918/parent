package org.mixed.exam.admin.controller;

import org.mixed.exam.admin.pojo.Classification;
import org.mixed.exam.admin.service.ClassifyService;
import org.mixed.exam.bank.api.pojo.vo.SubjectItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("hasAnyRole('ROLE_adm','ROLE_tea1','ROLE_tea2')")
public class ClassifyController {
    @Autowired
    private ClassifyService classifyService;
    //添加课程或者课程知识点
    @RequestMapping("classify/addClassify")
    public String addClassify(  @RequestParam(value ="classifyName") String classifyName,
                                @RequestParam(value = "superClassId",defaultValue = "") String superClassId)
    {
        Classification classification = new Classification();
        classification.setClassifyName(classifyName);
        classification.setSuperClassId(superClassId);
        classifyService.insertClassify(classification);
        return "添加成功";
    }
    //查找所有课程信息
    @RequestMapping("classify/findAllCourse")
    public List<Classification> findAllCourse(){
        return classifyService.findAllCourse();
    }
    //查找所有课程知识点信息
    @RequestMapping("classify/findAllKnowledge")
    public List<Classification> findAllKnowledge(){
        return classifyService.findAllKnowledge();
    }
    //查找所有题目信息
    @RequestMapping("classify/findAllSubject")
    public List<SubjectItem> findAllSubject(){
        return classifyService.findAllSubject();
    }
}

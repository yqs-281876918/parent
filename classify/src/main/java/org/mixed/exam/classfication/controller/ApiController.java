package org.mixed.exam.classfication.controller;

import org.mixed.exam.classfication.pojo.Classification;
import org.mixed.exam.classfication.service.ClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@PreAuthorize("hasAnyRole('ROLE_adm')")
public class ApiController
{
    @Autowired
    private ClassifyService classifyService;

    @ResponseBody
    @GetMapping("api/course/all")
    public List<Classification> findAllCourse(){
        return classifyService.findAllCourse();
    }

    @ResponseBody
    @GetMapping("api/knowledge/all")
    public List<Classification> findAllKnowledge(){
        return classifyService.findAllKnowledge();
    }
}

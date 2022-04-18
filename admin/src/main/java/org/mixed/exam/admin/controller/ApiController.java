package org.mixed.exam.admin.controller;

import org.mixed.exam.admin.dao.ClassifyDao;
import org.mixed.exam.admin.pojo.Classification;
import org.mixed.exam.admin.service.ClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
@PreAuthorize("hasAnyRole('ROLE_adm','ROLE_tea1','ROLE_tea2')")
public class ApiController
{
    @Autowired
    private ClassifyService classifyService;

    @Autowired
    private ClassifyDao classifyDao;
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

    @ResponseBody
    @GetMapping("api/knowledge")
    public List<Classification> findKnowledge(String courseID)
    {
        return classifyDao.findKnowledge(courseID);
    }
}

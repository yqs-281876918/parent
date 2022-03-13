package org.mixed.exam.bank.controller;

import org.mixed.exam.bank.dao.SubjectDao;
import org.mixed.exam.bank.pojo.dto.SubjectJson;
import org.mixed.exam.bank.pojo.po.Question;
import org.mixed.exam.bank.pojo.vo.SubjectItem;
import org.mixed.exam.bank.service.SubjectItemService;
import org.mixed.exam.bank.util.SubjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@PreAuthorize("hasAnyRole('ROLE_adm,ROLE_tea2')")
public class ApiController {
    @Autowired
    private SubjectDao subjectDao;

    @ResponseBody
    @GetMapping("api/subjects/type")
    public List<SubjectJson> subjects(@RequestParam("type") String type) {
        List<? extends Question> subjects = subjectDao.getSubjectsByType(type);
        List<SubjectJson> jsons = new ArrayList<>();
        for (Question q : subjects) {
            String jsonString = SubjectUtil.subject2Json(q);
            jsons.add(new SubjectJson(q.getType(), jsonString));
        }
        return jsons;
    }

    @Autowired
    private SubjectItemService subjectItemService;

    @ResponseBody
    @GetMapping("api/subject/items")
    public List<SubjectItem> items() {
        return subjectItemService.getItems();
    }

    @ResponseBody
    @GetMapping("api/subject/id")
    public SubjectJson getSubjectByID(@RequestParam("id") String id, @RequestParam("type") String type) {
        Question q = subjectDao.getSubjectsByID(id, type);
        String jsonString = SubjectUtil.subject2Json(subjectDao.getSubjectsByID(id, type));
        return new SubjectJson(q.getType(), jsonString);
    }

    @ResponseBody
    @PostMapping("api/subject/pass")
    public Integer passSubject(@RequestParam("id")String id,@RequestParam("type")String type)
    {
        return subjectDao.pass(id,type);
    }
}

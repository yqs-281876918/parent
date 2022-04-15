package org.mixed.exam.bank.controller;

import org.mixed.exam.bank.dao.SubjectDao;
import org.mixed.exam.bank.pojo.dto.SubjectJson;
import org.mixed.exam.bank.pojo.po.Question;
import org.mixed.exam.bank.pojo.vo.SubjectItem;
import org.mixed.exam.bank.service.QuerySubjectService;
import org.mixed.exam.bank.service.SubjectItemService;
import org.mixed.exam.bank.service.SubjectService;
import org.mixed.exam.bank.util.HttpUtil;
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
public class SubjectController {
    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    private QuerySubjectService querySubjectService;

    @Autowired
    private SubjectService subjectService;
    @ResponseBody
    @GetMapping("api/subjects/type")
    public List<SubjectJson> subjects(@RequestParam("type") String type) {
        List<? extends Question> subjects = subjectDao.getSubjectsByType(type);
        List<SubjectJson> jsons = new ArrayList<>();
        for (Question q : subjects) {
            jsons.add(SubjectUtil.subject2Json(q));
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
        return SubjectUtil.subject2Json(q);
    }

    @ResponseBody
    @PostMapping("api/subject/pass")
    public Integer passSubject(@RequestParam("id")String id,@RequestParam("type")String type)
    {
        return subjectDao.pass(id,type);
    }

    @ResponseBody
    @PostMapping("api/subject/fail")
    public void failSubject(@RequestParam("id")String id)
    {
        subjectDao.deleteSubject(id);
    }

    @ResponseBody
    @GetMapping("api/subject/count")
    public Integer getCountByType(@RequestParam("type") String type,@RequestParam("courseID") String courseID)
    {
        return subjectDao.getCountByType(type,courseID);
    }

    @ResponseBody
    @GetMapping("api/subject/typeList")
    public List<String> getTypeList(@RequestParam("language")String language)
    {
        return subjectService.getTypeList(language);
    }
    @ResponseBody
    @GetMapping("api/subjects")
    public List<SubjectJson> getSubjects(@RequestParam(value = "type",defaultValue = HttpUtil.NULL_STRING_VALUE)String type,
                                     @RequestParam(value = "open",defaultValue = HttpUtil.NULL_STRING_VALUE)String open,
                                     @RequestParam(value = "isExamined",defaultValue = HttpUtil.NULL_STRING_VALUE)String isExamined,
                                     @RequestParam(value = "difficulty",defaultValue = HttpUtil.NULL_STRING_VALUE)String difficulty,
                                     @RequestParam(value = "courseID",defaultValue = HttpUtil.NULL_STRING_VALUE)String courseID,
                                     @RequestParam(value = "class2ndID",defaultValue = HttpUtil.NULL_STRING_VALUE)String class2ndID,
                                     @RequestParam(value = "creator",defaultValue = HttpUtil.NULL_STRING_VALUE)String creator)
    {
        String _type = type.equals(HttpUtil.NULL_STRING_VALUE) ?null:type;
        Boolean _open = open.equals(HttpUtil.NULL_STRING_VALUE) ?null:Boolean.valueOf(open);
        Boolean _isExamined = open.equals(HttpUtil.NULL_STRING_VALUE) ?null:Boolean.valueOf(isExamined);
        Integer _difficulty = difficulty.equals(HttpUtil.NULL_STRING_VALUE)?null:Integer.valueOf(difficulty);
        String _courseID = courseID.equals(HttpUtil.NULL_STRING_VALUE) ?null:courseID;
        String _class2ndID = class2ndID.equals(HttpUtil.NULL_STRING_VALUE) ?null:class2ndID;
        String _creator = creator.equals(HttpUtil.NULL_STRING_VALUE) ?null:creator;
        return SubjectUtil.subjects2Json2(
                querySubjectService.getSubjects(_type,_open,_isExamined,_difficulty,_courseID,_class2ndID,_creator));
        //return SubjectUtil.subjects2Json2(subjectDao.getSubject(courseID));
    }
    @ResponseBody
    @GetMapping("subject/json")
    public String getSubjectByID(@RequestParam("id") String id)
    {
        return subjectService.getSubjectByID(id);
    }
    @ResponseBody
    @GetMapping("subject/un-verify")
    public List<SubjectItem> getUnVerifySubjectItems()
    {
        return subjectDao.getUnVerifySubjectItems();
    }
    @ResponseBody
    @PostMapping("subject/delete")
    public String delete(String id){
        subjectDao.deleteSubject(id);
        return "success";
    }
    @ResponseBody
    @PostMapping("subject/mulDelete")
    public String mulDelete(String[] itemIds){
        for (String id:itemIds){
            subjectDao.deleteSubject(id);
        }
        return "success";
    }
}

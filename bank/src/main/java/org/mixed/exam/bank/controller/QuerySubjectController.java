package org.mixed.exam.bank.controller;

import org.mixed.exam.bank.pojo.po.*;
import org.mixed.exam.bank.service.QuerySubjectService;
import org.mixed.exam.bank.util.SubjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@PreAuthorize("hasAnyRole('ROLE_adm,ROLE_tea2')")
public class QuerySubjectController
{
    @Autowired
    private QuerySubjectService querySubjectService;
    @ResponseBody
    @GetMapping("subjectByID")
    public Question subjectByID(@RequestParam("id")String id, @RequestParam("type")String type)
    {
        return querySubjectService.queryByID(id, SubjectUtil.getClassByType(type));
    }
    @ResponseBody
    @GetMapping("subjectsByType")
    public List<? extends Question> subjectByType(@RequestParam("type") String type)
    {
        return querySubjectService.getSubjectsByType(type);
    }
    @ResponseBody
    @GetMapping("subject/singleChoice")
    public SingleChoiceQuestion singleChoice(@RequestParam("id")String id)
    {
        return (SingleChoiceQuestion) querySubjectService.queryByID(id, SubjectUtil.getClassByType("singleChoiceQuestion"));
    }
    @ResponseBody
    @GetMapping("subject/completion")
    public Completion completion(@RequestParam("id")String id)
    {
        return (Completion) querySubjectService.queryByID(id, SubjectUtil.getClassByType("completion"));
    }
    @ResponseBody
    @GetMapping("subject/comprehensiveQuestion")
    public ComprehensiveQuestion comprehensiveQuestion(@RequestParam("id")String id)
    {
        return (ComprehensiveQuestion) querySubjectService.queryByID(id, SubjectUtil.getClassByType("comprehensiveQuestion"));
    }
    @ResponseBody
    @GetMapping("subject/judgment")
    public Judgment judgment(@RequestParam("id")String id)
    {
        return (Judgment) querySubjectService.queryByID(id, SubjectUtil.getClassByType("judgment"));
    }
    @ResponseBody
    @GetMapping("subject/multipleChoiceQuestion")
    public MultipleChoiceQuestion multipleChoiceQuestion(@RequestParam("id")String id)
    {
        return (MultipleChoiceQuestion) querySubjectService.queryByID(id, SubjectUtil.getClassByType("multipleChoiceQuestion"));
    }
    @ResponseBody
    @GetMapping("subject/programProblem")
    public ProgramProblem programProblem(@RequestParam("id")String id)
    {
        return (ProgramProblem) querySubjectService.queryByID(id, SubjectUtil.getClassByType("programProblem"));
    }
    @ResponseBody
    @GetMapping("subject/singleChoiceQuestion")
    public SingleChoiceQuestion singleChoiceQuestion(@RequestParam("id")String id)
    {
        return (SingleChoiceQuestion) querySubjectService.queryByID(id, SubjectUtil.getClassByType("singleChoiceQuestion"));
    }
}

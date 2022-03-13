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
    @GetMapping("subject/calculationProblem")
    public CalculationProblem calculationProblem(@RequestParam("id")String id)
    {
        return (CalculationProblem) querySubjectService.queryByID(id, SubjectUtil.getClassByType("calculationProblem"));
    }
    @ResponseBody
    @GetMapping("subject/clozeTest")
    public ClozeTest clozeTest(@RequestParam("id")String id)
    {
        return (ClozeTest) querySubjectService.queryByID(id, SubjectUtil.getClassByType("clozeTest"));
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
    @GetMapping("subject/dataItems")
    public DataItems dataItems(@RequestParam("id")String id)
    {
        return (DataItems) querySubjectService.queryByID(id, SubjectUtil.getClassByType("dataItems"));
    }
    @ResponseBody
    @GetMapping("subject/entryProblem")
    public EntryProblem entryProblem(@RequestParam("id")String id)
    {
        return (EntryProblem) querySubjectService.queryByID(id, SubjectUtil.getClassByType("entryProblem"));
    }
    @ResponseBody
    @GetMapping("subject/essayQuestion")
    public EssayQuestion essayQuestion(@RequestParam("id")String id)
    {
        return (EssayQuestion) querySubjectService.queryByID(id, SubjectUtil.getClassByType("essayQuestion"));
    }
    @ResponseBody
    @GetMapping("subject/judgment")
    public Judgment judgment(@RequestParam("id")String id)
    {
        return (Judgment) querySubjectService.queryByID(id, SubjectUtil.getClassByType("judgment"));
    }
    @ResponseBody
    @GetMapping("subject/listeningQuestion")
    public ListeningQuestion listeningQuestion(@RequestParam("id")String id)
    {
        return (ListeningQuestion) querySubjectService.queryByID(id, SubjectUtil.getClassByType("listeningQuestion"));
    }
    @ResponseBody
    @GetMapping("subject/matching")
    public Matching matching(@RequestParam("id")String id)
    {
        return (Matching) querySubjectService.queryByID(id, SubjectUtil.getClassByType("matching"));
    }
    @ResponseBody
    @GetMapping("subject/multipleChoiceQuestion")
    public MultipleChoiceQuestion multipleChoiceQuestion(@RequestParam("id")String id)
    {
        return (MultipleChoiceQuestion) querySubjectService.queryByID(id, SubjectUtil.getClassByType("multipleChoiceQuestion"));
    }
    @ResponseBody
    @GetMapping("subject/nounParsing")
    public NounParsing nounParsing(@RequestParam("id")String id)
    {
        return (NounParsing) querySubjectService.queryByID(id, SubjectUtil.getClassByType("nounParsing"));
    }
    @ResponseBody
    @GetMapping("subject/oralTopic")
    public OralTopic oralTopic(@RequestParam("id")String id)
    {
        return (OralTopic) querySubjectService.queryByID(id, SubjectUtil.getClassByType("oralTopic"));
    }
    @ResponseBody
    @GetMapping("subject/programProblem")
    public ProgramProblem programProblem(@RequestParam("id")String id)
    {
        return (ProgramProblem) querySubjectService.queryByID(id, SubjectUtil.getClassByType("programProblem"));
    }
    @ResponseBody
    @GetMapping("subject/rankingQuestion")
    public RankingQuestion rankingQuestion(@RequestParam("id")String id)
    {
        return (RankingQuestion) querySubjectService.queryByID(id, SubjectUtil.getClassByType("rankingQuestion"));
    }
    @ResponseBody
    @GetMapping("subject/readComprehension")
    public ReadComprehension readComprehension(@RequestParam("id")String id)
    {
        return (ReadComprehension) querySubjectService.queryByID(id, SubjectUtil.getClassByType("readComprehension"));
    }
    @ResponseBody
    @GetMapping("subject/singleChoiceQuestion")
    public SingleChoiceQuestion singleChoiceQuestion(@RequestParam("id")String id)
    {
        return (SingleChoiceQuestion) querySubjectService.queryByID(id, SubjectUtil.getClassByType("singleChoiceQuestion"));
    }
    @ResponseBody
    @GetMapping("subject/voteTopic")
    public VoteTopic voteTopic(@RequestParam("id")String id)
    {
        return (VoteTopic) querySubjectService.queryByID(id, SubjectUtil.getClassByType("voteTopic"));
    }
}

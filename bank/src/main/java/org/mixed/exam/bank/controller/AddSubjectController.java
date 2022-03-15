package org.mixed.exam.bank.controller;

import lombok.Data;
import org.mixed.exam.admin.api.pojo.Classification;
import org.mixed.exam.auth.api.AuthUtil;
import org.mixed.exam.bank.pojo.po.*;
import org.mixed.exam.bank.service.AddSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@PreAuthorize("hasAnyRole('ROLE_adm')")
public class AddSubjectController {
    @Data
    private class BaseParam {
        private HttpServletRequest request;
        private Integer difficulty;
        private String date;
        private String courseID;
        private String class2ndID;
        private Integer recommendScore;
        private String creator;//创建人
    }

    @Autowired
    private AddSubjectService addSubjectService;

    private void setBaseInfo(Question q, HttpServletRequest request, BaseParam param) {
        q.setDifficulty(param.getDifficulty());
        q.setCourseID(param.getCourseID());
        q.setClass2ndID(param.getClass2ndID());
        q.setRecommendScore(param.getRecommendScore());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            q.setDate(simpleDateFormat.parse(param.getDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String jwt = cookie.getValue();
                String userName = AuthUtil.parseUsername(jwt);
                q.setCreator(userName);
            }
        }
    }

    //单选题
    @ResponseBody
    @PostMapping("addSubject/singleChoice")
    public String singleChoice(BaseParam baseParam,
                               HttpServletRequest request,
                               @RequestParam("description") String description,
                               @RequestParam("options") List<String> options,
                               @RequestParam("answer") String answer) {
        SingleChoiceQuestion question = new SingleChoiceQuestion();
        setBaseInfo(question, request, baseParam);
        question.setAnswer(answer);
        question.setType("singleChoiceQuestion");
        question.setOptions(options);
        question.setDescription(description);
        addSubjectService.insertSingleChoice(question);
        return "添加成功";
    }

    //多选题
    @ResponseBody
    @PostMapping("addSubject/multipleChoice")
    public String multipleChoice(BaseParam baseParam,
                                 HttpServletRequest request,
                                 @RequestParam("options") List<String> options,
                                 @RequestParam("description") String description,
                                 @RequestParam("answers") List<String> answers) {
        MultipleChoiceQuestion question = new MultipleChoiceQuestion();
        setBaseInfo(question, request, baseParam);
        question.setOptions(options);
        question.setDescription(description);
        question.setAnswers(answers);
        question.setType("multipleChoiceQuestion");
        addSubjectService.insertMultipleChoice(question);
        return "添加成功";
    }

    //填空题
    @ResponseBody
    @PostMapping("addSubject/completion")
    public String completion(BaseParam baseParam,
                             HttpServletRequest request,
                             @RequestParam("description") String description,
                             @RequestParam("answers") List<String> answers) {
        Completion question = new Completion();
        setBaseInfo(question, request, baseParam);
        question.setDescription(description);
        question.setAnswers(answers);
        question.setType("completion");
        addSubjectService.insertCompletion(question);
        return "添加成功";
    }

    //判断题
    @ResponseBody
    @PostMapping("addSubject/judgment")
    public String judgment(BaseParam baseParam,
                           HttpServletRequest request,
                           @RequestParam("options") List<String> options,
                           @RequestParam("description") String description,
                           @RequestParam("answer") String answer) {
        Judgment question = new Judgment();
        setBaseInfo(question, request, baseParam);
        question.setOptions(options);
        question.setDescription(description);
        question.setAnswer(answer);
        question.setType("judgment");
        addSubjectService.insertJudgment(question);
        return "添加成功";
    }

    //名词解析题
    @ResponseBody
    @PostMapping("addSubject/nounParsing")
    public String nounParsing(BaseParam baseParam,
                              HttpServletRequest request,
                              @RequestParam("description") String description,
                              @RequestParam("answer") String answer) {
        NounParsing question = new NounParsing();
        setBaseInfo(question, request, baseParam);
        question.setDescription(description);
        question.setAnswer(answer);
        question.setType("nounParsing");
        addSubjectService.insertNounParsing(question);
        return "添加成功";
    }

    //计算题
    @ResponseBody
    @PostMapping("addSubject/calculationProblem")
    public String calculationProblem(BaseParam baseParam,
                                     HttpServletRequest request,
                                     @RequestParam("description") String description,
                                     @RequestParam("answer") String answer) {
        CalculationProblem question = new CalculationProblem();
        setBaseInfo(question, request, baseParam);
        question.setDescription(description);
        question.setAnswer(answer);
        question.setType("calculationProblem");
        addSubjectService.insertCalculationProblem(question);
        return "添加成功";
    }

    //分录题
    @ResponseBody
    @PostMapping("addSubject/entryProblem")
    public String entryProblem(BaseParam baseParam,
                               HttpServletRequest request,
                               @RequestParam("description") String description,
                               @RequestParam("answer") String answer) {
        EntryProblem question = new EntryProblem();
        setBaseInfo(question, request, baseParam);
        question.setDescription(description);
        question.setAnswer(answer);
        question.setType("entryProblem");
        addSubjectService.insertEntryProblem(question);
        return "添加成功";
    }

    //论述题
    @ResponseBody
    @PostMapping("addSubject/essayQuestion")
    public String essayQuestion(BaseParam baseParam,
                                HttpServletRequest request,
                                @RequestParam("description") String description,
                                @RequestParam("answer") String answer) {
        EssayQuestion question = new EssayQuestion();
        setBaseInfo(question, request, baseParam);
        question.setDescription(description);
        question.setAnswer(answer);
        question.setType("essayQuestion");
        addSubjectService.insertEssayQuestion(question);
        return "添加成功";
    }

    //资料题
    @ResponseBody
    @PostMapping("addSubject/dataItems")
    public String dataItems(BaseParam baseParam,
                            HttpServletRequest request,
                            @RequestParam("description") String description,
                            @RequestParam("answer") String answer) {
        DataItems question = new DataItems();
        setBaseInfo(question, request, baseParam);
        question.setDescription(description);
        question.setAnswer(answer);
        question.setType("dataItems");
        addSubjectService.insertDataItems(question);
        return "添加成功";
    }

    //排序题
    @ResponseBody
    @PostMapping("addSubject/rankingQuestion")
    public String rankingQuestion(BaseParam baseParam,
                                  HttpServletRequest request,
                                  @RequestParam("options") List<String> options,
                                  @RequestParam("description") String description,
                                  @RequestParam("answer") String answer) {
        RankingQuestion question = new RankingQuestion();
        setBaseInfo(question, request, baseParam);
        question.setOptions(options);
        question.setDescription(description);
        question.setAnswer(answer);
        question.setType("rankingQuestion");
        addSubjectService.insertRankingQuestion(question);
        return "添加成功";
    }

    //投票题
    @ResponseBody
    @PostMapping("addSubject/voteTopic")
    public String voteTopic(BaseParam baseParam,
                            HttpServletRequest request,
                            @RequestParam("options") List<String> options,
                            @RequestParam("description") String description) {
        VoteTopic question = new VoteTopic();
        setBaseInfo(question, request, baseParam);
        question.setOptions(options);
        question.setDescription(description);
        question.setType("voteTopic");
        addSubjectService.insertVoteTopic(question);
        return "添加成功";
    }

    //完型填空
    @ResponseBody
    @PostMapping("addSubject/clozeTest")
    public String clozeTest(BaseParam baseParam,
                            HttpServletRequest request,
                            @RequestParam("options") List<String> options,
                            @RequestParam("description") String description,
                            @RequestParam("answers") List<String> answers) {
        ClozeTest question = new ClozeTest();
        setBaseInfo(question, request, baseParam);
        question.setOptions(options);
        question.setDescription(description);
        question.setAnswers(answers);
        question.setType("clozeTest");
        addSubjectService.insertClozeTest(question);
        return "添加成功";
    }

    //阅读理解
    @ResponseBody
    @PostMapping("addSubject/readComprehension")
    public String readComprehension(BaseParam baseParam,
                                    HttpServletRequest request,
                                    @RequestParam("options") List<String> options,
                                    @RequestParam("description") String description,
                                    @RequestParam("answers") List<String> answers) {
        ReadComprehension question = new ReadComprehension();
        setBaseInfo(question, request, baseParam);
        question.setOptions(options);
        question.setDescription(description);
        question.setAnswers(answers);
        question.setType("readComprehension");
        addSubjectService.insertReadComprehension(question);
        return "添加成功";
    }

    //听力题
    @ResponseBody
    @PostMapping("addSubject/listeningQuestion")
    public String listeningQuestion(BaseParam baseParam,
                                    HttpServletRequest request,
                                    @RequestParam("options") List<String> options,
                                    @RequestParam("description") String description,
                                    @RequestParam("answers") List<String> answers) {
        ListeningQuestion question = new ListeningQuestion();
        setBaseInfo(question, request, baseParam);
        question.setOptions(options);
        question.setDescription(description);
        question.setAnswers(answers);
        question.setType("listeningQuestion");
        addSubjectService.insertListeningQuestion(question);
        return "添加成功";
    }

    //综合题
    @ResponseBody
    @PostMapping("addSubject/comprehensiveQuestion")
    public String comprehensiveQuestion(BaseParam baseParam,
                                        HttpServletRequest request,
                                        @RequestParam("description") String description,
                                        @RequestParam("answer") String answer) {
        ComprehensiveQuestion question = new ComprehensiveQuestion();
        setBaseInfo(question, request, baseParam);
        question.setDescription(description);
        question.setAnswer(answer);
        question.setType("comprehensiveQuestion");
        addSubjectService.insertComprehensiveQuestion(question);
        return "添加成功";
    }

    //口语题
    @ResponseBody
    @PostMapping("addSubject/oralTopic")
    public String oralTopic(BaseParam baseParam,
                            HttpServletRequest request,
                            @RequestParam("description") String description) {
        OralTopic question = new OralTopic();
        setBaseInfo(question, request, baseParam);
        question.setDescription(description);
        question.setType("oralTopic");
        addSubjectService.insertOralTopic(question);
        return "添加成功";
    }

    //程序题
    @ResponseBody
    @PostMapping("addSubject/programProblem")
    public String programProblem(BaseParam baseParam,
                                 HttpServletRequest request,
                                 @RequestParam("description") String description,
                                 @RequestParam("prepositionCode") String prepositionCode,
                                 @RequestParam("postCode") String postCode,
                                 @RequestParam("answer") String answer) {
        ProgramProblem question = new ProgramProblem();
        setBaseInfo(question, request, baseParam);
        question.setDescription(description);
        question.setPrepositionCode(prepositionCode);
        question.setPostCode(postCode);
        question.setAnswer(answer);
        question.setType("programProblem");
        addSubjectService.insertProgramProblem(question);
        return "添加成功";
    }

    //    private List<String> optionsLeft = Collections.singletonList("null");
//    private List<String> optionsRight = Collections.singletonList("null");
//    private List<String> answer=Collections.singletonList("null");
    //连线题
    @ResponseBody
    @PostMapping("addSubject/matching")
    public String matching(BaseParam baseParam,
                           HttpServletRequest request,
                           @RequestParam("description") String description,
                           @RequestParam("optionsLeft") List<String> optionsLeft,
                           @RequestParam("optionsRight") List<String> optionsRight,
                           @RequestParam("answer") List<String> answer) {
        Matching question = new Matching();
        setBaseInfo(question, request, baseParam);
        question.setDescription(description);
        question.setOptionsLeft(optionsLeft);
        question.setOptionsRight(optionsRight);
        question.setAnswer(answer);
        question.setType("matching");
        addSubjectService.insertMatching(question);
        return "添加成功";
    }

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
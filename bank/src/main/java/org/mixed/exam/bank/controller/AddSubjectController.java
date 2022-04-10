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
import java.util.Map;

@Controller
@PreAuthorize("hasAnyRole('ROLE_adm')")
public class AddSubjectController {
    @Data
    private static class BaseParam {
        private HttpServletRequest request;
        private Integer difficulty;
        private String courseID;
        private String class2ndID;
        private String creator;//创建人
        private String introduction;
        private String description;
        private List<String> fileUrls;
    }

    @Autowired
    private AddSubjectService addSubjectService;

    private void setBaseInfo(Question q, HttpServletRequest request, BaseParam param) {
        q.setDifficulty(param.getDifficulty());
        q.setCourseID(param.getCourseID());
        q.setClass2ndID(param.getClass2ndID());
        q.setIntroduction(param.getIntroduction());
        q.setDate(new Date());
        q.setDescription(param.description);
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String jwt = cookie.getValue();
                String userName = AuthUtil.parseUsername(jwt);
                q.setCreator(userName);
            }
        }
        q.setFileUrls(param.fileUrls);
    }

    //单选题
    @ResponseBody
    @PostMapping("addSubject/singleChoiceQuestion")
    public String singleChoice(BaseParam baseParam,
                               HttpServletRequest request, @RequestParam("answer") String answer,
                               @RequestParam("optionCount")int optionCount) {
        SingleChoiceQuestion question = new SingleChoiceQuestion();
        setBaseInfo(question, request, baseParam);
        question.setAnswer(answer);
        question.setType("singleChoiceQuestion");
        question.setOptionCount(optionCount);
        addSubjectService.insertQuestion(question);
        return "添加成功";
    }

    //多选题
    @ResponseBody
    @PostMapping("addSubject/multipleChoiceQuestion")
    public String multipleChoice(BaseParam baseParam, HttpServletRequest request,
                                 @RequestParam("answers") List<String> answers,
                                 @RequestParam("optionCount")int optionCount) {
        MultipleChoiceQuestion question = new MultipleChoiceQuestion();
        setBaseInfo(question, request, baseParam);
        question.setAnswers(answers);
        question.setType("multipleChoiceQuestion");
        question.setOptionCount(optionCount);
        addSubjectService.insertQuestion(question);
        return "添加成功";
    }

    //填空题
    @ResponseBody
    @PostMapping("addSubject/completion")
    public String completion(BaseParam baseParam, HttpServletRequest request,
                             @RequestParam("answers") List<String> answers) {
        Completion question = new Completion();
        setBaseInfo(question, request, baseParam);
        question.setAnswers(answers);
        question.setType("completion");
        addSubjectService.insertQuestion(question);
        return "添加成功";
    }

    //判断题
    @ResponseBody
    @PostMapping("addSubject/judgment")
    public String judgment(BaseParam baseParam,
                           HttpServletRequest request,
                           @RequestParam("answer") String answer) {
        Judgment question = new Judgment();
        setBaseInfo(question, request, baseParam);
        question.setAnswer(answer);
        question.setType("judgment");
        addSubjectService.insertQuestion(question);
        return "添加成功";
    }
    //组合选择
    @ResponseBody
    @PostMapping("addSubject/combinationChoice")
    public String combinationChoice(BaseParam baseParam,
                                    HttpServletRequest request,
                                    @RequestParam("answers") List<String> answers,
                                    @RequestParam("optionCount")int optionCount) {
        CombinationChoice question = new CombinationChoice();
        setBaseInfo(question, request, baseParam);
        question.setAnswers(answers);
        question.setType("combinationChoice");
        question.setOptionCount(optionCount);
        addSubjectService.insertQuestion(question);
        return "添加成功";
    }

    //综合题
    @ResponseBody
    @PostMapping("addSubject/comprehensiveQuestion")
    public String comprehensiveQuestion(BaseParam baseParam, HttpServletRequest request,
                                        @RequestParam("answer") String answer) {
        ComprehensiveQuestion question = new ComprehensiveQuestion();
        setBaseInfo(question, request, baseParam);
        question.setAnswer(answer);
        question.setType("comprehensiveQuestion");
        addSubjectService.insertQuestion(question);
        return "添加成功";
    }

    //程序题
    @ResponseBody
    @PostMapping("addSubject/programProblem")
    public String programProblem(BaseParam baseParam,
                                 HttpServletRequest request,
                                 @RequestParam("description") String description,
                                 @RequestParam("inputs") List<String> inputs,
                                 @RequestParam("outputs") List<String> outputs) {
        ProgramProblem question = new ProgramProblem();
        setBaseInfo(question, request, baseParam);
        question.setDescription(description);
        question.setInputs(inputs);
        question.setOutputs(outputs);
        question.setType("programProblem");
        addSubjectService.insertQuestion(question);
        return "添加成功";
    }
}
package org.mixed.exam.student.controller;

import com.alibaba.fastjson.JSON;
import org.mixed.exam.auth.api.AuthUtil;
import org.mixed.exam.bank.api.client.ExamClient;
import org.mixed.exam.bank.api.client.PaperClient;
import org.mixed.exam.bank.api.client.SubjectClient;
import org.mixed.exam.bank.api.pojo.po.Exam;
import org.mixed.exam.bank.api.pojo.po.Paper;
import org.mixed.exam.bank.api.pojo.po.exam.Answer;
import org.mixed.exam.bank.api.pojo.po.exam.ExamDetail;
import org.mixed.exam.student.service.ChooseClassService;
import org.mixed.exam.student.service.ExamDetailService;
import org.mixed.exam.student.service.ExamService;
import org.mixed.exam.student.util.RedisUtil;
import org.mixed.exam.student.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class RouteController
{
    @Autowired
    private ExamClient examClient;
    @Autowired
    private PaperClient paperClient;
    @Autowired
    private SubjectClient subjectClient;
    @Autowired
    private ExamService examService;
    @Autowired
    private ExamDetailService examDetailService;
    @Autowired
    private ChooseClassService classService;

    private HashMap<String,ExamDetail> answerHash = new HashMap<String,ExamDetail>();
    @GetMapping("/exam/list")
    public String exam_list(Model model,@RequestParam("cno") long cno,HttpServletRequest request)
    {
        String userName = "";
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String jwt = cookie.getValue();
                userName = AuthUtil.parseUsername(jwt);
            }
        }
        String realName = classService.getRealName(userName).getRealName();
        if (realName==null){
            realName = userName;
        }
        List<Exam> exams=examService.getExamByCno(cno);
        for (Exam exam:exams){
            Date date = new Date();
            long dateTime =date.getTime();
            if (dateTime<exam.getStartTime()){
                exam.setStatus(1);
                continue;
            }
            if (dateTime>=exam.getStartTime() && dateTime<=exam.getStartTime()+exam.getLateTime()*60000){
                exam.setStatus(2);
            }
            else {
                exam.setStatus(3);
            }
            List<ExamDetail> examDetailList = examDetailService.selectExamDetail(userName,exam.getId());
            if (examDetailList!=null){
                for (ExamDetail examDetail:examDetailList){
                    if (examDetail.getFinishReview()==0){
                        exam.setStatus(4);
                    }
                    else {
                        exam.setStatus(5);
                        exam.setStudentScore(examDetail.getTotalScore());
                    }
                }
            }
        }
        model.addAttribute("realName",realName);
        model.addAttribute("exams",exams);
        return "exam/list.html";
    }
    @GetMapping("/exam/stuExam")
    public String info(Model model, @RequestParam("id") Integer examId, HttpServletRequest request)
    {
        String userName = "";
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String jwt = cookie.getValue();
                userName = AuthUtil.parseUsername(jwt);
                model.addAttribute("userName",userName);
            }
        }
        String examIdKey = Integer.toString(examId);
        Exam exam = null;
        if(RedisUtil.exists(examIdKey)){
            String examStr = RedisUtil.get(examIdKey);
            exam = JSON.parseObject(examStr,Exam.class);
            System.out.println("从缓存中取出考试信息");
        }else {
            exam = examClient.getByID(examId);
            System.out.println("从数据库中取出考试信息");
            String examStr = JSON.toJSONString(exam);
            if (RedisUtil.set(examIdKey,examStr,Long.parseLong("600"))){
                System.out.println("写入缓存成功");
            }else {
                System.out.println("写入缓存失败");
            }
        }
        Paper paper = null;
        if (RedisUtil.exists(exam.getPaperID())){
            String paperStr = RedisUtil.get(exam.getPaperID());
            paper = JSON.parseObject(paperStr,Paper.class);
            System.out.println("从缓存中取出试卷信息");
        }else {
            paper = paperClient.getByID(exam.getPaperID());
            System.out.println("从数据库中取出试卷信息");
            String paperStr = JSON.toJSONString(paper);
            if (RedisUtil.set(exam.getPaperID(),paperStr,Long.parseLong("600"))){
                System.out.println("试卷信息写入缓存成功");
            }else {
                System.out.println("试卷信息写入缓存失败");
            }
        }
        String subjectsKey = exam.getPaperID()+"Subjects";
        List<Object> subjectsMap= null;
        if (RedisUtil.exists(subjectsKey)){
            String subjectsStr = RedisUtil.get(subjectsKey);
            subjectsMap = JSON.parseArray(subjectsStr);
            System.out.println("从缓存中取出试题信息");
        }else {
            List<String> subjectIDs=paper.getSubjectIDs();
            subjectsMap = new ArrayList<>();
            for(String id : subjectIDs)
            {
                //System.out.println(subjectClient.getSubjectByID(id));
                subjectsMap.add(JSON.parseObject(subjectClient.getSubjectByID(id)));
                //System.out.println(StringUtil.jsonToMap(subjectClient.getSubjectByID(id)));
            }
            System.out.println("从数据库中取出试题信息");
            String subjectsStr = JSON.toJSONString(subjectsMap);
            if (RedisUtil.set(subjectsKey,subjectsStr,Long.parseLong("600"))){
                System.out.println("试题信息写入缓存成功");
            }else {
                System.out.println("试题信息写入缓存失败");
            }
        }
        String key = userName + exam.getId().toString();
        int flag = 1;
        if (answerHash.get(key)!=null){
            model.addAttribute("examDetail",answerHash.get(key));
            model.addAttribute("examDetailState",flag);
        }else {
            flag = 0;
            model.addAttribute("examDetailState",flag);
        }
        model.addAttribute("subjects",subjectsMap);
        model.addAttribute("paper",paper);
        model.addAttribute("exam",exam);
        return "exam/stuExamDetail.html";
    }
    @ResponseBody
    @RequestMapping("/test/saveMyAnswer")
    public void saveMyAnswer(@RequestParam("answers") List<String> answers_json,
                             @RequestParam("examId") Integer examId,
                             @RequestParam("antiCount") Integer antiCount,
                             @CookieValue("token")String jwt){
        String userName = AuthUtil.parseUsername(jwt);
        ExamDetail examDetail = new ExamDetail();
        examDetail.setExamId(examId);
        examDetail.setUsername(userName);
        examDetail.setAntiCount(antiCount);
        List<Answer> answers= new ArrayList<>();
        for(String json : answers_json){
            Answer answer = StringUtil.json2Object(json,Answer.class);
            answers.add(answer);
        }
        examDetail.setAnswers(answers);
        String key = userName + examId.toString();
        answerHash.put(key,examDetail);
    }
}

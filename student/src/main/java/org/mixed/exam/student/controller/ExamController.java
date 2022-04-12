package org.mixed.exam.student.controller;

import org.mixed.exam.auth.api.AuthUtil;
import org.mixed.exam.bank.api.client.ExamClient;
import org.mixed.exam.bank.api.pojo.po.exam.Answer;
import org.mixed.exam.bank.api.pojo.po.exam.ExamDetail;
import org.mixed.exam.student.service.ExamDetailService;
import org.mixed.exam.student.service.ExamService;
import org.mixed.exam.student.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
@Controller
public class ExamController {
    @Autowired
    private ExamService examService;
    @Autowired
    private ExamClient examClient;
    @Autowired
    private ExamDetailService examDetailService;
//    //根据班级号得到我的考试列表
//    @RequestMapping("/test/examList")
//    public List<Exam> getExamByCno(long classID){
//        return examService.getExamByCno(classID);
//    }
//    //判断该学生是否能进入测试
//    @RequestMapping("/test/goExamDetail")
//    public String goExamDetail(Integer id){
//        Exam exam = examClient.getByID(id);
//        Date date = new Date();
//        long dateTime =date.getTime();
//        if (dateTime<exam.getStartTime()){
//            return "未到时间";
//        }
//        if (dateTime>=exam.getStartTime() && dateTime<=exam.getStartTime()+exam.getLateTime()*60000){
//            System.out.println(111);
//            return "可以进入";
//        }
//        System.out.println(111);
//        return "已超时";
//    }
    @ResponseBody
    @RequestMapping("/test/submitExam")
    public String submitExam(@RequestParam("answers") List<String> answers_json,
                             @RequestParam("examId") Integer examId,
                             HttpServletRequest request){
        ExamDetail examDetail = new ExamDetail();
        examDetail.setExamId(examId);
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String jwt = cookie.getValue();
                String userName = AuthUtil.parseUsername(jwt);
                examDetail.setUsername(userName);
            }
        }
        List<Answer> answers= new ArrayList<>();
        for(String json : answers_json){
            Answer answer = StringUtil.json2Object(json,Answer.class);
            answers.add(answer);
        }
        for (Answer answer:answers){
            if (answer.getSubjectType().equals("completion")){

            }else {
                String ans = answer.getAnswerList().get(0);
                String[] list = ans.split(",");
                List<String> answerList = null;
                for (int i=0;i<list.length;i++){
                    answerList.add(list[i]);
                }
                answer.setAnswerList(answerList);
            }
        }
        examDetail.setAnswers(answers);
        examDetailService.saveExamDetail(examDetail);
        return "success";
    }
}

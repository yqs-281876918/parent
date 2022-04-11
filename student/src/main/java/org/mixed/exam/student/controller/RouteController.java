package org.mixed.exam.student.controller;

import org.mixed.exam.auth.api.AuthUtil;
import org.mixed.exam.bank.api.client.ExamClient;
import org.mixed.exam.bank.api.client.PaperClient;
import org.mixed.exam.bank.api.client.SubjectClient;
import org.mixed.exam.bank.api.pojo.po.Exam;
import org.mixed.exam.bank.api.pojo.po.Paper;
import org.mixed.exam.student.service.ExamService;
import org.mixed.exam.student.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    @GetMapping("/exam/list")
    public String exam_list(Model model,@RequestParam("cno") long cno)
    {
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
        }
        model.addAttribute("exams",exams);
        return "exam/list.html";
    }
    @GetMapping("/exam/stuExam")
    public String info(Model model, @RequestParam("id") Integer examId, HttpServletRequest request)
    {
        System.out.println(111);
//        ExamDetail examDetail = new ExamDetail();
//        examDetail.setExamId(examId);
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                String jwt = cookie.getValue();
                String userName = AuthUtil.parseUsername(jwt);
                model.addAttribute("userName",userName);
            }
        }
        Exam exam = examClient.getByID(examId);
        Paper paper = paperClient.getByID(exam.getPaperID());
        List<String> subjectIDs=paper.getSubjectIDs();
        List<Map<String,Object>> subjectsMap=new ArrayList<>();
        for(String id : subjectIDs)
        {
            subjectsMap.add(StringUtil.jsonToMap(subjectClient.getSubjectByID(id)));
        }
        model.addAttribute("subjects",subjectsMap);
        model.addAttribute("paper",paper);
        model.addAttribute("exam",exam);
        return "exam/stuExamDetail.html";
    }
}

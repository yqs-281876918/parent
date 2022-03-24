package org.mixed.exam.student.controller;

import org.mixed.exam.auth.api.AuthUtil;
import org.mixed.exam.bank.api.client.ExamClient;
import org.mixed.exam.bank.api.client.PaperClient;
import org.mixed.exam.bank.api.client.SubjectClient;
import org.mixed.exam.bank.api.pojo.po.Exam;
import org.mixed.exam.bank.api.pojo.po.Paper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RouteController
{
    @Autowired
    private ExamClient examClient;
    @Autowired
    private PaperClient paperClient;
    @Autowired
    private SubjectClient subjectClient;
    @GetMapping("/exam/list")
    public String exam_list(Model model, @CookieValue("token")String token)
    {
        String userName= AuthUtil.parseUsername(token);
        List<Exam> exams=examClient.list(userName);
        model.addAttribute("exams",exams);
        return "exam/list.html";
    }
    @GetMapping("/exam/stuExam")
    public String info(Model model,@RequestParam("id") Integer examId)
    {
        Exam exam = examClient.getByID(examId);
        Paper paper = paperClient.getByID(exam.getPaperID());
        List<List<String>> subjectIDs=paper.getSubjectIDs();
        List<String> subjectJsons=new ArrayList<>();
        for(List<String> ls : subjectIDs)
        {
            for(String id : ls)
            {
                subjectJsons.add(subjectClient.getSubjectByID(id));
            }
        }
        model.addAttribute("subjects",subjectJsons);
        model.addAttribute("paper",paper);
        return "exam/stuExam.html";
    }
}

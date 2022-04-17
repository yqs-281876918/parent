package org.mixed.exam.teacher.controller;

import org.mixed.exam.auth.api.po.Users;
import org.mixed.exam.bank.api.client.ExamClient;
import org.mixed.exam.teacher.dao.ClassMapper;
import org.mixed.exam.teacher.pojo.dto.EmailDto;
import org.mixed.exam.teacher.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@PreAuthorize("hasAnyRole('ROLE_adm','ROLE_tea1','ROLE_tea2')")
@Controller
public class PublishController
{
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private ExamClient examClient;
    @Autowired
    private EmailService emailService;
    @ResponseBody
    @PostMapping("/exam/publish")
    public String publish(String startTime, Integer testTime, Integer lateTime, Integer submitTime,
                          String paperID, HttpServletRequest request, String examName, String introduce,
                          @RequestParam("scoreList") List<String> scoreList)
    {
        String score = "";
        Integer totalScore = 0;
        for (String s:scoreList){
            score += s;
            score += ",";
            totalScore += Integer.parseInt(s);
        }
        score = score.substring(0,score.length()-1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        long _startTime = 0L;
        try {
            _startTime = sdf.parse(startTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String[] classIDs = request.getParameterValues("classIDs");
        //发送邮件
        List<EmailDto> emailDtos = new ArrayList<>();
        for(String id : classIDs){
            List<Users> stuList = classMapper.stuList(Integer.parseInt(id));
            for(Users stu : stuList){
                emailDtos.add(new EmailDto(stu.getEmail(),examName+" 考试提醒",
                        "您于"+startTime.toString()+"有一场考试,请注意参加!"));
            }
        }
        emailService.send(emailDtos);
        String[] antiSettings = request.getParameterValues("antiSettings");
        for(String classID:classIDs)
        {
            examClient.submit(_startTime,testTime,lateTime,submitTime,paperID,
                    Integer.parseInt(classID),String.join(",",antiSettings),examName,introduce,score,totalScore);
        }
        return "success";
    }
}

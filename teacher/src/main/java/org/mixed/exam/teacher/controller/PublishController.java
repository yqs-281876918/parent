package org.mixed.exam.teacher.controller;

import org.mixed.exam.bank.api.client.ExamClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@PreAuthorize("hasAnyRole('ROLE_adm','ROLE_tea1','ROLE_tea2')")
@Controller
public class PublishController
{
    @Autowired
    private ExamClient examClient;
    @ResponseBody
    @PostMapping("/exam/publish")
    public String publish(String startTime, Integer testTime, Integer lateTime, Integer submitTime,
                          String paperID,HttpServletRequest request)
    {
        String[] classIDs = request.getParameterValues("classIDs");
        String[] antiSettings = request.getParameterValues("antiSettings");
        for(String classID:classIDs)
        {
            examClient.submit(startTime,testTime,lateTime,submitTime,paperID,
                    Integer.parseInt(classID),String.join(",",antiSettings));
        }
        return "success";
    }
}

package org.mixed.exam.student.controller;

import org.mixed.exam.auth.api.AuthUtil;
import org.mixed.exam.student.service.ExamDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ExamDetialController {
    @Autowired
    private ExamDetailService examDetailService;
    @ResponseBody
    @PostMapping("/ExamDetial/getDetialId")
    public String getDetialId(@RequestParam("id") int examid, @CookieValue("token")String jwt){
        return examDetailService.getDetialId(examid, AuthUtil.parseUsername(jwt));
    }
}

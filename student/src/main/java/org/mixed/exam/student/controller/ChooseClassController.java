package org.mixed.exam.student.controller;


import org.mixed.exam.auth.api.AuthUtil;
import org.mixed.exam.student.pojo.po.ChooseClass;
import org.mixed.exam.student.service.ChooseClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class ChooseClassController {
    @Autowired
    private ChooseClassService classService;
    @RequestMapping("/class/choose")
    public String chooseClassByInvitation(String invitation,
                                          HttpServletRequest request){
        long cno = classService.getCnoByInvitation(invitation);
        if (cno>0){
            ChooseClass chooseClass = new ChooseClass();
            chooseClass.setCno(cno);
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String jwt = cookie.getValue();
                    String userName = AuthUtil.parseUsername(jwt);
                    chooseClass.setSname(userName);
                }
            }
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            chooseClass.setData(dateFormat.format(date));
            if (classService.addChooseClass(chooseClass)>0){
                return "添加成功";
            }else {
                return "添加失败";
            }
        }else {
            return "未找到班级";
        }
    }
}
